package email;

import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;

/**
 * 邮件信息描述类
 */
public class Email {
	/**配置session属性*/
	private Properties props;
	/**发送者昵称*/
	private String nickName;
	/**发送者邮箱账号*/
	private String from;
	/**发送者邮箱密码*/
	private String password;
	/**接收者*/
	private String to;
	/**标题*/
	private String title;
	/**邮件内容*/
	private String content;
	/**附件*/
	private List<DataSource> attachments;
	
	
	public Email() {

	}

	public Email(Properties props, String nickName, String from, String password, String to, String title,String content, List<DataSource> attachments) {
		super();
		this.props = props;
		this.nickName = nickName;
		this.from = from;
		this.password = password;
		this.to = to;
		this.title = title;
		this.content = content;
		this.attachments = attachments;
	}


	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<DataSource> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DataSource> attachments) {
		this.attachments = attachments;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}