package beans;

public class Employee {
	private String soCode; //매장 
	private String soName;
	
	private String slCode; // 직원
	private String slName;
	
	private String slStateCode;
	private String slStateName;
	private int stCode;
	
	private String todayInfo;
	
	
	private String stName;
	private String slPassword;
	private String date;
	
	public String getTodayInfo() {
		return todayInfo;
	}
	public void setTodayInfo(String todayInfo) {
		this.todayInfo = todayInfo;
	}

	public int getStCode() {
		return stCode;
	}
	public void setStCode(int stCode) {
		this.stCode = stCode;
	}
	
	
	public String getSlStateCode() {
		return slStateCode;
	}
	public void setSlStateCode(String slStateCode) {
		this.slStateCode = slStateCode;
	}
	public String getSlStateName() {
		return slStateName;
	}
	public void setSlStateName(String slStateName) {
		this.slStateName = slStateName;
	}
	
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
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
