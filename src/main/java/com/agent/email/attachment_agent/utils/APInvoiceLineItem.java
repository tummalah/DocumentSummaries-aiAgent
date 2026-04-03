package com.agent.email.attachment_agent.utils;

public record APInvoiceLineItem(
        String description,
        Double quantity,
        Double unitPrice,
        Double totalAmount,
        Double taxAmount,
        String sku
        //Float confidence
) {}