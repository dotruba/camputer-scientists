package application;

public class Registration {
	
	int confNo;
	int sessionID;
	int camperID;
	int cabinID;
	int counsellorID;
	String campName;
	boolean isPaid = false;

	
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
	
	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public int getConfNo() {
		return confNo;
	}

	public int getSessionID() {
		return sessionID;
	}

	public int getCamperID() {
		return camperID;
	}

	public String getCampName() {
		return campName;
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
	
	public void setCabin(int cabinID){
		this.cabinID = cabinID;
	}
	
	public int getCabin(){
		return this.cabinID;
	}
	
	public void setCounsellor(int counsellorID){
		this.counsellorID = counsellorID;
	}
	
	public int getCounsellor(){
		return this.counsellorID;
	}
	
	public String toString() {
		return "confNo: " + this.confNo
				+ "\nsessionId: " + this.sessionID
				+ "\ncamperId: " + this.camperID
				+ "\ncampName: " + this.campName
				+ "\nisPaid: " + this.isPaid;
	}
	
}
