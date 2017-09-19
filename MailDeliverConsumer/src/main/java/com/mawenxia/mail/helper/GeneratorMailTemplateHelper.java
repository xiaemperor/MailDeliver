package com.mawenxia.mail.helper;

import com.mawenxia.mail.constant.Const;
import com.mawenxia.mail.vo.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by sam on 2017/6/6.
 * <p>
 * coding like an artist
 */
@Service
public class GeneratorMailTemplateHelper {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    public void generatorAndSend(MailData mailData) throws Exception{
        Context context = new Context();
        context.setLocale(Locale.CHINA);
        context.setVariables(mailData.getParam());
        String templateLocation = mailData.getTemplateName();
        String content = templateEngine.process(templateLocation,context);
        mailData.setContent(content);
        //调用发送方法
        send(mailData);
    }

    private void send(MailData mailData) throws Exception{
        MimeMessage mime =javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mime,true, Const.CHARSET_UTF8);
        mimeMessageHelper.setFrom(mailData.getFrom());
        mimeMessageHelper.setTo(mailData.getTo());
        mimeMessageHelper.setSubject(mailData.getSubject());
        mimeMessageHelper.setText(mailData.getContent(),true);
        javaMailSender.send(mime);
    }
}
