package com.agent.email.attachment_agent.utils;
import org.apache.tika.metadata.Metadata;

public interface AttachmentFilter {
    boolean accept(Metadata metadata);
}
