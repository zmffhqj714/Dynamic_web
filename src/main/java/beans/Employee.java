package beans;

public class Employee {
	private String soCode; //매장 
	private String soName;
	
	private String slCode; // 직원
	private String slName;
	private String slPassword;
	private String date;
	private int log;
	
	public int getLog() {
		return log;
	}
	public void setLog(int log) {
		this.log = log;
	}
	
	public String getSoName() {
		return soName;
	}
	public void setSoName(String soName) {
		this.soName = soName;
	}

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSoCode() {
		return soCode;
	}
	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}
	public String getSlCode() {
		return slCode;
	}
	public void setSlCode(String slCode) {
		this.slCode = slCode;
	}
	public String getSlPassword() {
		return slPassword;
	}
	public void setSlPassword(String slPassword) {
		this.slPassword = slPassword;
	}
	public String getSlName() {
		return slName;
	}
	public void setSlName(String slName) {
		this.slName = slName;
	}

	
}
