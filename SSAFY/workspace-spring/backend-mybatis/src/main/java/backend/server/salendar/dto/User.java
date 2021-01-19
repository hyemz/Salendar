package backend.server.salendar.dto;


public class User {
	private int usr_seq;
	private String usr_email;
	private String usr_password;
	private String usr_nick;
	private int usr_following;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int usr_seq, String usr_email, String usr_password, String usr_nick, int usr_following) {
		super();
		this.usr_seq = usr_seq;
		this.usr_email = usr_email;
		this.usr_password = usr_password;
		this.usr_nick = usr_nick;
		this.usr_following = usr_following;
	}

	public int getUsr_seq() {
		return usr_seq;
	}

	public void setUsr_seq(int usr_seq) {
		this.usr_seq = usr_seq;
	}

	public String getUsr_email() {
		return usr_email;
	}

	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}

	public String getUsr_password() {
		return usr_password;
	}

	public void setUsr_password(String usr_password) {
		this.usr_password = usr_password;
	}

	public String getUsr_nick() {
		return usr_nick;
	}

	public void setUsr_nick(String usr_nick) {
		this.usr_nick = usr_nick;
	}

	public int getUsr_following() {
		return usr_following;
	}

	public void setUsr_following(int usr_following) {
		this.usr_following = usr_following;
	}

	@Override
	public String toString() {
		return "Member [usr_seq=" + usr_seq + ", usr_email=" + usr_email + ", usr_password=" + usr_password
				+ ", usr_nick=" + usr_nick + ", usr_following=" + usr_following + "]";
	}



	
}
