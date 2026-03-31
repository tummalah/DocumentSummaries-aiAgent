package com.agent.email.attachment_agent.services;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class EmailAttachmentService {

    private final AttachmentAgent agent;
    private final APInvoiceDataStructureAgent invoiceAgent;

    public EmailAttachmentService(AttachmentAgent agent, APInvoiceDataStructureAgent invoiceAgent) {
        this.agent = agent;
        this.invoiceAgent=invoiceAgent;
    }

    public String processFile(String path) {
        // Load and Parse
        Document doc = FileSystemDocumentLoader.loadDocument(path, new ApacheTikaDocumentParser());

        // Split into 1000-character chunks for local Llama performance
        var segments = DocumentSplitters.recursive(1000, 100).split(doc);

        // Map: Summarize each chunk
        String partialSummaries = segments.stream()
                .map(s -> (String)agent.summarize(s.text()))
                .collect(Collectors.joining("\n"));

        // Reduce: One final summary of all partial summaries
        return agent.summarize("Create a final executive summary from these notes: " + partialSummaries);
    }

    public String getPayload(String summary) {
        // Load and Parse
        
        // Reduce: One final summary of all partial summaries
        return invoiceAgent.invoicePayload( summary);
    }
}