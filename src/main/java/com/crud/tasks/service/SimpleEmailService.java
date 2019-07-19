package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import sun.security.validator.ValidatorException;

import static org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator.*;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail){
        LOGGER.info("Starting email properation...");
        try{
        javaMailSender.send(createMimeMessage(mail));
        LOGGER.info("Email has been sent.");
    } catch (MailException e){
        LOGGER.error("Failed to proccess email sending: ",e.getMessage(),e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if(mail.getSubject().contains("day")){
                messageHelper.setText(mailCreatorService.buildNumberOfTasksEmail(mail.getMessage()), true);
            }else{
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            }

        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        if(mail.getSubject().contains("day")){
            simpleMailMessage.setText(mailCreatorService.buildNumberOfTasksEmail(mail.getMessage()));
        }else{
            simpleMailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        }
        return simpleMailMessage;
    }
}
