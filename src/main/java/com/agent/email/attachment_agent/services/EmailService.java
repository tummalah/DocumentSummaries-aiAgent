package com.agent.email.attachment_agent.services;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

import java.util.logging.Logger;

import com.agent.email.attachment_agent.utils.*;

@Service
public class EmailService {

     
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private final AttachmentAgent agent;

    public EmailService(AttachmentAgent agent) {
        this.agent = agent;
    }

    public String processEmail(String path) throws Exception{
        // Load and Parse
        
        EmailRecord email = EmailParser.parse(
                Path.of(path),
                new AttachmentType()
        );
        
        LOGGER.info("EmailFrom:"+email.from());
        LOGGER.info("EmailTo:"+email.to());
        LOGGER.info("EmailSubject:"+email.subject());
        
String prompt = """
        You are analyzing a business email which may contain attachments.

        From: %s
        To: %s
        Subject: %s

        Email Body:
        %s

        Attachment Content:
        %s

        Please provide:
        1. Executive summary
        2. Key decisions
        3. Action items
        4. Summary of Attachment Content
        """.formatted(
                email.from(),
                email.to(),
                email.subject(),
                email.body(),
                String.join("\n", email.attachmentsText())
        );


        // Reduce: One final summary of all partial summaries
        return agent.summarize("Create a final executive summary from these notes: " + prompt);
    }
}