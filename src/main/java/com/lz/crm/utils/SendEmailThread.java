package com.lz.crm.utils;


import com.lz.crm.settings.domain.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailThread implements Runnable {
    private User user;

    public SendEmailThread(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println("~~~"+user.getId());
        String content="<a href='http://localhost:8081/one_war_exploded/settings/user/activeUser.do?code="+user.getId()+"'>点击激活</a>";
        sendEmail(user.getEmail(),"激活",content);
}
public void sendEmail(String recipient,String subject,String content){
    try{
        Properties prop = new Properties();
        prop.setProperty( "mai1.host", "smtp.163.com");
        prop.setProperty ("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth","true");

        //关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true) ;
        prop.put( "mail.smtp.ssl.enable" , "true");
        prop.put( "mail.smtp.ssl.socketFactory" , sf);


        //1、创建定义整个应用程序所需的环境信息的Session 对象
        Session session = Session.getDefaultInstance(prop,new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication("lz2955909451@163.com", "LMSVWYNVVLCHIKRJ");
            }
        });
        //开启Session 的debug模式，这样就可以查看到彩序发送Email的运行状态
        session.setDebug(true) ;
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用广名和授权码连上邮件服务器
        ts.connect("smtp.163.com","lz2955909451@163.com", "LMSVWYNVVLCHIKRJ");


        //4、创建邮件
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom( new InternetAddress("lz2955909451@163.com"));
        //收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
        //邮件的标题
        message.setSubject(subject);

        message.setContent(content,"text/html;charset=UTF-8");
        message.saveChanges();
        //发送邮件
        ts.sendMessage(message,message.getAllRecipients( ));
        ts.close();
    }catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}

