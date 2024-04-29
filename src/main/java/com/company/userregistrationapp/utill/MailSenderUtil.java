package com.company.userregistrationapp.utill;

import com.company.userregistrationapp.dto.response.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
public class MailSenderUtil {

   private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(MailDto mailDto) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("no-reply", "no-reply");
        helper.setTo(mailDto.getToAddress());
        helper.setSubject(mailDto.getSubject());

        helper.setText(mailDto.getContent(), true);

        javaMailSender.send(message);

    }


}
