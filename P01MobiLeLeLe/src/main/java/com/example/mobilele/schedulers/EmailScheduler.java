package com.example.mobilele.schedulers;

import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
//
//    private final JavaMailSender mailSender;
//
//    public EmailScheduler(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    private List<String> getAllUserEmails() {
//
//        return List.of("user1@example.com", "user2@example.com");
//    }
//
//    @Scheduled(cron = "0 0 9 * * MON")
//    public void sendEmailToAllUsers() {
//        List<String> userEmails = getAllUserEmails();
//
//        for (String toEmail : userEmails) {
//            sendEmail(toEmail, "Weekly Update from Our Team", createEmailBody());
//        }
//    }
//
//    private void sendEmail(String toEmail, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom("your-email@example.com");
//
//        mailSender.send(message);
//    }
//
//    private String createEmailBody() {
//        return "Hello,\n\n" +
//                "   - We have added new functionalities to our platform to help you work more efficiently.\n\n" +
//                "Your Team";
//    }
}