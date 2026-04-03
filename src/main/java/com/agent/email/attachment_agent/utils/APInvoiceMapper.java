package com.agent.email.attachment_agent.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.cloud.documentai.v1.Document;


public class APInvoiceMapper {

   

    public static APInvoiceRecord map(Document doc) {

        //System.out.println(doc.getEntitiesList());

        Map<String, Float> confidence = new HashMap<>();

        String invoiceNumber = getField(doc, "invoice_id", confidence);
        String vendor = getField(doc, "supplier_name", confidence);
        String total = getField(doc, "total_amount", confidence);
        String po= getField(doc, "po_number", confidence);

        return new APInvoiceRecord(
                vendor,
                invoiceNumber,
                getField(doc, "invoice_date", confidence),
                getField(doc, "due_date", confidence),
                getField(doc, "currency", confidence),
                total != null ? Double.parseDouble(total) : null,
                extractLineItems(doc),
                po,
                doc.getText(),
                confidence
        );
    }



private static List<APInvoiceLineItem> extractLineItems(Document doc) {

    List<APInvoiceLineItem> lineItems = new ArrayList<>();

    for (Document.Entity entity : doc.getEntitiesList()) {

        // Invoice processor uses "line_item"
        if (!"line_item".equalsIgnoreCase(entity.getType())) {
            continue;
        }

        String description = null;
        Double quantity = null;
        Double unitPrice = null;
        Double totalAmount = null;
        Double taxAmount= null;
        String sku=null;

     
        for (Document.Entity prop : entity.getPropertiesList()) {

            String type = prop.getType();
            String value = prop.getMentionText();

            switch (type) {
                case "description" -> description = value;
                case "quantity" -> quantity = parseDouble(value);
                case "unit_price" -> unitPrice = parseDouble(value);
                case "amount" -> totalAmount = parseDouble(value);
                case "taxAmount" -> taxAmount= parseDouble(value);
                case "sku" -> sku= value;
            }
        }

        lineItems.add(
                new APInvoiceLineItem(
                        description,
                        quantity,
                        unitPrice,
                        totalAmount,
                        taxAmount,
                        sku
                )
        );
    }

    return lineItems;
}


    private static String getField(Document doc, String name, Map<String, Float> conf) {
        for (Document.Entity e : doc.getEntitiesList()) {
            if (name.equalsIgnoreCase(e.getType())) {
                conf.put(name, e.getConfidence());
                return e.getMentionText();
            }
        }
        return null;
    }


    

private static Double parseDouble(String value) {
        if (value == null) return null;
        try {
            // Strip currency symbols, commas, spaces
            String cleaned = value.replaceAll("[^0-9.]", "");
            return cleaned.isEmpty() ? null : Double.parseDouble(cleaned);
        } catch (Exception e) {
            return null;
        }


}
}
