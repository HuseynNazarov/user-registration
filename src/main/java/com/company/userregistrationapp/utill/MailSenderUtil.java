package com.company.userregistrationapp.utill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailSenderUtil {

//   private final JavaMailSender javaMailSender;
//
//    @Async
//    public void sendMail(MailDto mailDto) throws MessagingException, UnsupportedEncodingException {
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("no-reply", "no-reply");
//        helper.setTo(mailDto.getToAddress());
//        helper.setSubject(mailDto.getSubject());
//
//        helper.setText(mailDto.getContent(), true);
//
//        javaMailSender.send(message);
//
//    }


}
