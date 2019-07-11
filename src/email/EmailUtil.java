package email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


/**
 * 邮件发送工具
 * 1．SMTP（递送邮件机制）
 * 简单邮件传输协议
 * SMTP服务器将邮件转发到接收者的SMTP服务器，直至最后被接收者通过POP或者IMAP协议获取。
 * 2．POP（获取邮件机制）
 * 邮局协议，目前为第3个版本POP3
 * 3．IMAP（多目录共享）
 * 接收信息的高级协议，目前版本为第4版IMAP4
 * 接收新信息，将这些信息递送给用户，维护每个用户的多个目录。
 * 4．MIME
 * 邮件扩展内容格式：信息格式、附件格式等等
 * 5．NNTP
 * 第三方新闻组协议
 *
 */
public class EmailUtil {
	
	/**
	 * 发送邮件
	 * @param email 邮件信息
	 */
	public static void sendEmail(Email email) {
		// 邮件配置信息
		Properties props = email.getProps();
		String nickName = email.getNickName();
		String receiver = email.getTo();
		String title = email.getTitle();
		String content = email.getContent();
		String username = email.getFrom();
		String password = email.getPassword();
		Authenticator authenticator = new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		// 根据属性新建一个邮件会话
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(false);
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 设置发件人
			message.setFrom(new InternetAddress(MimeUtility.encodeText(nickName)+"<"+username+">"));
			// 设置收件人,并设置其接收类型为TO
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			// 设置标题
			message.setSubject(title);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart("mixed");
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content,"text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
    		List<DataSource> attachments = email.getAttachments();
            for(DataSource attach : attachments){
            	// 添加附件
                BodyPart msgAttach = new MimeBodyPart();
                // 添加附件的内容
				msgAttach.setDataHandler(new DataHandler(attach));
                // 添加附件的标题
				msgAttach.setFileName(MimeUtility.encodeText(attach.getName()));
                multipart.addBodyPart(msgAttach);
            }
            // 将multipart对象放到message中
            message.setContent(multipart);
			// 设置发信时间
			message.setSentDate(new Date());
			// 存储邮件信息
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.host", "smtp.qq.com");
		String nickName = "上和云医院";
		String from = "470918743@qq.com";
		String to = "bzxianhu12345@163.com";
		String title = "注册成功";
		String content = "你已成功注册，欢迎使用本系统";
		String password = "BZxianhUlI|||???";
		List<DataSource> attachments = new ArrayList<>();
		Email email = new Email(props,nickName, from, password, to, title, content,attachments);
		EmailUtil.sendEmail(email);
	}
}
