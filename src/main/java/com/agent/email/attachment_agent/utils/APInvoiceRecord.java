package com.agent.email.attachment_agent.utils;

import java.util.List;
import java.util.Map;

public record APInvoiceRecord(
        String vendorName,
        String invoiceNumber,
        String invoiceDate,
        String dueDate,
        String currency,
        Double totalAmount,
        List<APInvoiceLineItem> lineItems,
        String ponumber,
        String fullText,
        Map<String, Float> confidenceScores
) {}
