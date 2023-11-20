package com.iumutdikbasan.receiptservice.repository;

import com.iumutdikbasan.receiptservice.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
}
