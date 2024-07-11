package kg.attractor.xfood.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(Long appealsId, String supervisorsMessage) throws MessagingException, UnsupportedEncodingException;
}
