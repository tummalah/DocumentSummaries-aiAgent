package com.agent.email.attachment_agent;

import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.boot.CommandLineRunner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.email.attachment_agent.services.*;
import com.agent.email.attachment_agent.utils.APInvoiceRecord;

@SpringBootApplication
public class AttachmentAgentApplication implements CommandLineRunner {

	private final EmailService emailService ;
    private final EmailAttachmentService emailAttachmentService;
    private final VertexInvoiceProcessingService vertexInvoiceProcessingService;

    AttachmentAgentApplication(EmailService emailService, EmailAttachmentService emailAttachmentService,VertexInvoiceProcessingService vertexInvoiceProcessingService ) {
        this.emailService = emailService;
        this.emailAttachmentService= emailAttachmentService;
        this.vertexInvoiceProcessingService= vertexInvoiceProcessingService;
    }

    public static void main(String[] args) {
		SpringApplication.run(AttachmentAgentApplication.class, args);
	}

// 	@Override
//     public void run(String... args) {


     
//             // Point this to a real file in your Downloads folder
//            String fileName = args[0]; 
//            String downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", fileName).toString();

//             System.out.println("🚀 Starting Enterprise Agent POC...");
//            System.out.println("Reading file: " + downloadsPath);

//             try {
//               String summary = emailAttachmentService.processFile(downloadsPath);
//               String payload= emailAttachmentService.getPayload(summary);
                
//                 System.out.println("\n--- FINAL ENTERPRISE SUMMARY ---");
//                System.out.println(summary);
//                 System.out.println("--------------------------------");
//                 System.out.println("\n--- Invoice Payload ---");
//                System.out.println(payload);
//                 System.out.println("--------------------------------");
//             } catch (Exception e) {
//                 System.err.println("❌ Error processing file: " + e.getMessage());
//                 e.printStackTrace();
//             }
        
	
// }

// @Override
//     public void run(String... args) {


     
//             // Point this to a real file in your Downloads folder
//            String fileName = args[0]; 
//            String downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", fileName).toString();

//             System.out.println("🚀 Starting Enterprise Agent POC...");
//            System.out.println("Reading file: " + downloadsPath);

//             try {
//               String summary = emailService.processEmail(downloadsPath);
                
//                 System.out.println("\n--- FINAL ENTERPRISE SUMMARY ---");
//                System.out.println(summary);
//                 System.out.println("--------------------------------");
//             } catch (Exception e) {
//                 System.err.println("❌ Error processing file: " + e.getMessage());
//                 e.printStackTrace();
//             }
        
	
// }

// Use google document ai
@Override
    public void run(String... args) {


     
            // Point this to a real file in your Downloads folder
           String fileName = args[0]; 
           Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", fileName);

            System.out.println("🚀 Starting Enterprise Agent POC...");
           System.out.println("Reading file: " + downloadsPath);

            try {
              APInvoiceRecord invoiceDoc = vertexInvoiceProcessingService.processInvoice(downloadsPath);  

              
             
              String payload= emailAttachmentService.getPayload(invoiceDoc.fullText());
                

                
                System.out.println("\n--- Invoice Payload ---");
               System.out.println(payload);
                System.out.println("--------------------------------");
            } catch (Exception e) {
                System.err.println("❌ Error processing file: " + e.getMessage());
                e.printStackTrace();
            }
        
	
}
}
