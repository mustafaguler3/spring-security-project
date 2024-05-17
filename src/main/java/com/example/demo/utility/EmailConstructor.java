package com.example.demo.utility;

import com.example.demo.model.User;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class EmailConstructor {
    @Autowired
    private Environment env;
    @Autowired
    private TemplateEngine templateEngine;

    public MimeMessagePreparator constructNewUserEmail(User user, String password){
        Context context = new Context();
        context.setVariable("user",user);
        context.setVariable("password",password);
        String text = templateEngine.process("newUserEmailTemplate",context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(jakarta.mail.internet.MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("Welcome");
                email.setText(text,true);
                email.setFrom(String.valueOf(new InternetAddress(env.getProperty("support.email"))));
            }
        };
        return messagePreparator;
    }
}


















