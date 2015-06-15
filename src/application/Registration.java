package application;

public class Registration {
	
	int confNo;
	int sessionID;
	int camperID;
	String campName;
	boolean isPaid;
	
	public Registration(){}
	
	public Registration(int confNo, int session, int camperID, String campName, boolean isPaid){
		this.confNo = confNo;
		this.sessionID = session;
		this.camperID = camperID;
		this.campName = campName;
		this.isPaid = isPaid;
	}
	
	public void setConfNo(int confNo){
		this.confNo = confNo;
	}
	
	public void setCamperID(int camperID){
		this.camperID = camperID;
	}
	
	public void setSessionID(int sessionID){
		this.sessionID = sessionID;
	}
	
	public void setCampName(String campName){
		this.campName = campName;
	}
	
	public void setIsPaid(boolean isPaid){
		this.isPaid = isPaid;
	}
	
}
