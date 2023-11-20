package com.iumutdikbasan.receiptservice.service;

import com.iumutdikbasan.receiptservice.dto.InventoryResponse;
import com.iumutdikbasan.receiptservice.dto.ReceiptLineItemsDto;
import com.iumutdikbasan.receiptservice.dto.ReceiptRequest;
import com.iumutdikbasan.receiptservice.model.Receipt;
import com.iumutdikbasan.receiptservice.model.ReceiptLineItems;
import com.iumutdikbasan.receiptservice.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final WebClient.Builder webClientBuilder;

    public String placeReceipt(ReceiptRequest receiptRequest) {
        Receipt receipt = new Receipt();
        receipt.setReceiptNumber(UUID.randomUUID().toString());

        List<ReceiptLineItems> receiptLineItems = receiptRequest.getReceiptLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        receipt.setReceiptLineItemsList(receiptLineItems);

        List<String> skuCodes = receipt.getReceiptLineItemsList().stream()
                .map(ReceiptLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place receipt if product is in
        // stock
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            receiptRepository.save(receipt);
            return "Receipt placed successfully.";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private ReceiptLineItems mapToDto(ReceiptLineItemsDto receiptLineItemsDto) {
        ReceiptLineItems receiptLineItems = new ReceiptLineItems();
        receiptLineItems.setPrice(receiptLineItemsDto.getPrice());
        receiptLineItems.setQuantity(receiptLineItemsDto.getQuantity());
        receiptLineItems.setSkuCode(receiptLineItemsDto.getSkuCode());
        return receiptLineItems;
    }
}