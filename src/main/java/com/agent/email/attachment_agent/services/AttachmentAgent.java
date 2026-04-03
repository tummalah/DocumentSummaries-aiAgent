package com.agent.email.attachment_agent.services;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AttachmentAgent {

    @SystemMessage("""
        You are an Enterprise Audit Assistant and Finance Analyst who knows Account Recievables,Account Payables and Procurement. 
        Break down the provided content focusing on:
        1. Main Purpose
        2. Key Entities/Names and Parties Involved
        3. Is it a Financial Document
        4. capture Invoice Line Items and capture item number,amounts,quantity,unit price,PO number etc
        5. capture subtotal,tax amount,grand total etc from the document
        6. Critical Dates and Financial Values
        """)
    String summarize(@UserMessage String content);
}
