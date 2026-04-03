package com.agent.email.attachment_agent.services;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface APInvoiceDataStructureAgent {

    @SystemMessage("""
        You are a data structuring agent.

You will be given a summary extracted from an email and its attachments.
Your task is to convert this summary into a STRICT JSON object that matches
the schema provided below.

RULES:
- Output ONLY valid JSON.
- Do NOT include explanations, markdown, or comments.
- If a field is missing or unknown, use null.
- Do NOT infer data that is not explicitly stated.
- Dates must be in ISO-8601 format (YYYY-MM-DD) if present.
- Currency amounts must be numbers (no symbols).
- Lists must be arrays (use [] if empty).

JSON SCHEMA:
{
  "document_type": "invoice",
  "invoice_number": "string",
  "invoice_date": "string",
  "due_date": "string",
  "po_number": "string"
  "vendor": {
    "name": "string",
    "email": "string",
    "address": "string",
    "tax_id": "string"
  },

  "bill_to": {
    "name": "string",
    "email": "string",
    "address": "string"
  },

  "line_items": [
    {
      "description": "string",
      "quantity": "number",
      "unit_price": "number",
      "total": "number",
      "item_number": "number"
    }
  ],

  "subtotal": "number",
  "tax": {
    "amount": "number",
    "rate": "number"
  },
  "discount": "number",
  "total_amount": "number",
  "currency": "string",

  "payment_terms": "string",
  "payment_method": "string",

  "attachments": [
    {
      "file_name": "string",
      "file_type": "string"
    }
  ],

  "summary": "string",
  "confidence_level": "string"
}

        """)
    String invoicePayload(@UserMessage String content);
}
