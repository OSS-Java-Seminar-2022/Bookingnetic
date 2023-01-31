package com.project.bookingnetic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String user, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        String content = "Hello " + user + ", \n\n"+ body + "\n\nkind regards,\nBookingnetic team";
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

}
