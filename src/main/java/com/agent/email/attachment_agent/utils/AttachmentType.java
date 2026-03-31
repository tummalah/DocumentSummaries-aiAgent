package com.agent.email.attachment_agent.utils;
import org.apache.tika.metadata.Metadata;


public class AttachmentType implements AttachmentFilter {

    @Override
    public boolean accept(Metadata metadata) {
        String type = metadata.get(Metadata.CONTENT_TYPE);
        return type != null && (
                type.equals("application/pdf") ||
                type.contains("word")
        );
    }
}
