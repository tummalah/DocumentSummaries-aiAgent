package com.agent.email.attachment_agent.services;



import com.agent.email.attachment_agent.utils.APInvoiceMapper;
import com.agent.email.attachment_agent.utils.APInvoiceRecord;
import com.google.cloud.documentai.v1.Document;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.ProcessRequest;
import com.google.cloud.documentai.v1.ProcessResponse;
import com.google.cloud.documentai.v1.ProcessorName;
import com.google.cloud.documentai.v1.RawDocument;
import com.google.protobuf.ByteString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VertexInvoiceProcessingService {

    
    @Value("${GCP_PROJECT_ID}")
    private  String PROJECT_ID;
    private  String LOCATION = "us"; 
    @Value("${GCP_PROCESSOR_ID}")       // or "eu"
    private  String PROCESSOR_ID = "";

    private final DocumentProcessorServiceClient client;

    public VertexInvoiceProcessingService() throws IOException {
        this.client = DocumentProcessorServiceClient.create();
    }

 
    public APInvoiceRecord processInvoice(Path invoiceFile) throws Exception {

        byte[] bytes = Files.readAllBytes(invoiceFile);

        RawDocument rawDocument = RawDocument.newBuilder()
                .setContent(ByteString.copyFrom(bytes))
                .setMimeType(detectMimeType(invoiceFile))
                .build();

        ProcessRequest request = ProcessRequest.newBuilder()
                .setName(
                        ProcessorName.of(
                                PROJECT_ID,
                                LOCATION,
                                PROCESSOR_ID
                        ).toString()
                )
                .setRawDocument(rawDocument)
                .build();

        ProcessResponse response = client.processDocument(request);
        Document vertexDoc = response.getDocument();

       
        return APInvoiceMapper.map(vertexDoc);
    }

    private String detectMimeType(Path path) {
        String name = path.getFileName().toString().toLowerCase();
        if (name.endsWith(".pdf")) return "application/pdf";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }
}
