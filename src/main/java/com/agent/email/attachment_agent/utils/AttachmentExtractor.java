package com.agent.email.attachment_agent.utils;

import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.util.ArrayList;
import java.util.List;

public class AttachmentExtractor implements EmbeddedDocumentExtractor {

    private final AttachmentFilter filter;
    private final List<String> extractedTexts = new ArrayList<>();

    public AttachmentExtractor(AttachmentFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean shouldParseEmbedded(Metadata metadata) {
        return filter.accept(metadata);
    }

    @Override
    public void parseEmbedded(
            java.io.InputStream stream,
            ContentHandler handler,
            Metadata metadata,
            boolean outputHtml
    ) throws java.io.IOException {

        BodyContentHandler contentHandler = new BodyContentHandler(-1);
        extractedTexts.add(contentHandler.toString());
    }

    public List<String> getExtractedTexts() {
        return extractedTexts;
    }
}
