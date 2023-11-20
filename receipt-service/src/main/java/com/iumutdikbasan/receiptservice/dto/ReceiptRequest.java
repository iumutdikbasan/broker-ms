package com.iumutdikbasan.receiptservice.dto;
import com.iumutdikbasan.receiptservice.model.ReceiptLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequest {
    private List<ReceiptLineItemsDto> receiptLineItemsDtoList;
}
