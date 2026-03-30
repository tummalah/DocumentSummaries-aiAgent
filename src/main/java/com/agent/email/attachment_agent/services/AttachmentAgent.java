package com.agent.email.attachment_agent.services;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AttachmentAgent {

    @SystemMessage("""
        You are an Enterprise Audit Assistant. 
        Summarize the provided text focusing on:
        1. Main Purpose
        2. Key Entities/Names
        3. Critical Dates and Financial Values
        """)
    String summarize(@UserMessage String content);
}
