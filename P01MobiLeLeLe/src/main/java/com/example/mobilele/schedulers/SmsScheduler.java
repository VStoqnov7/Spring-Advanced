package com.example.mobilele.schedulers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmsScheduler {


    private final String accountSid;
    private final String authToken;
    private final String twilioPhoneNumber;

    public SmsScheduler(@Value("${twilio.accountSid}") String accountSid,
                        @Value("${twilio.authToken}") String authToken,
                        @Value("${twilio.phoneNumber}") String twilioPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.twilioPhoneNumber = twilioPhoneNumber;
    }

    private List<String> getAllUserPhoneNumbers() {
        return List.of("+1234567890", "+1987654321");
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendSmsToAllUsers() {
        List<String> userPhoneNumbers = getAllUserPhoneNumbers();

        for (String phoneNumber : userPhoneNumbers) {
            sendSms(phoneNumber, "Weekly Update from Our Team", createSmsBody());
        }
    }

    private void sendSms(String toPhoneNumber, String subject, String body) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        body)
                .create();

        System.out.println("SMS sent successfully to: " + toPhoneNumber);
    }

    private String createSmsBody() {
        return "Hello,\n\n" +
                "   - We have added new functionalities to our platform to help you work more efficiently.\n\n" +
                "Your Team";
    }
}