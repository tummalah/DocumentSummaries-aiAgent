package com.agent.email.attachment_agent.services;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AttachmentAgent {

    @SystemMessage("""
        You are an Enterprise Audit Assistant and Finance Analyst who knows Account Recievables,Account Payables and Procurement. 
        Summarize the provided content focusing on:
        1. Main Purpose
        2. Key Entities/Names and Parties Involved
        3. Is it a Financial Document
        4. Important Line Items
        5. Critical Dates and Financial Values
        """)
    String summarize(@UserMessage String content);
}
