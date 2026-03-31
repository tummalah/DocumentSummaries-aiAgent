package com.agent.email.attachment_agent.utils;

import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;


public class EmailParser {

    

    public static EmailRecord parse(Path emailPath, AttachmentFilter filter) throws Exception {

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler bodyHandler = new BodyContentHandler(-1);

        AttachmentExtractor extractor =
                new AttachmentExtractor(filter);

        ParseContext context = new ParseContext();
        context.set(EmbeddedDocumentExtractor.class, extractor);

        try (InputStream is = Files.newInputStream(emailPath)) {
            parser.parse(is, bodyHandler, metadata, context);
        }

        return new EmailRecord(
                metadata.get("Message-From"),
                metadata.get("Message-To"),
                metadata.get("Subject"),
                bodyHandler.toString(),
                extractor.getExtractedTexts()
        );
    }
}