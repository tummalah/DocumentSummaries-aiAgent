package com.agent.email.attachment_agent.utils;

import java.util.List;

public record EmailRecord(
        String from,
        String to,
        String subject,
        String body,
        List<String> attachmentsText
) {}
