package com.memorynotfound.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;

@Component
@Slf4j
public class MailRunner implements CommandLineRunner {

    private static final String ADDRESS_SENDER = "crpVoskhod@gmail.com";
    private static final String SUBJECT = "subject";
    private static final String ADDRESS_RECIPIENT_NAME = "crpVoskhod";
    private static final String EMAIL_DOG_SYMBOL = "@";
    private static final String ADDRESS_RECIPIENT_DOMAIN_NAME = "gmail.com";
    private static final String TEXT_BODY_WITH_TAGS = "<java>arbitrary text</java>";
    private static final String TEXT_BODY_AS_HTML = "&lt;java&gt;arbitrary text&lt;/java&gt;";

    private static final UUID FILE_1_UUID = UUID.randomUUID();
    private static final String FILE_1_NAME = "root/directory/file1";
    private static final UUID FILE_2_UUID = UUID.randomUUID();
    private static final String FILE_2_NAME = "root/directory/file2";

    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        emailService.sendSimpleMessage(createEmail());
       // log.info(responseStatus.toString());
    }

    public static Mail createEmail() {
        return Mail.builder().
                from(ADDRESS_SENDER).
                to(createAddressRecipientAsString()).
                subject(SUBJECT).
                content(TEXT_BODY_WITH_TAGS).
                build();
    }

    private static String createAddressRecipientAsString() {
        return new StringBuilder().
                append(ADDRESS_RECIPIENT_NAME).
                append(EMAIL_DOG_SYMBOL).
                append(ADDRESS_RECIPIENT_DOMAIN_NAME).
                toString();
    }

    private static Map<UUID, String> createAttachmentsMap() {
        Map<UUID, String> attachmentsMap = new HashMap<>();
        attachmentsMap.put(FILE_1_UUID, FILE_1_NAME);
        attachmentsMap.put(FILE_2_UUID, FILE_2_NAME);
        return attachmentsMap;
    }

}
