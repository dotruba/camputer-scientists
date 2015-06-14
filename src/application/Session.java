package application;

public class Session {
	
	String startDate;
	String endDate;
	int sessionID;
	
	public Session(String start, String end, int id){
		this.startDate = start;
		this.endDate = end;
		this.sessionID = id;
	}
	
	public int getSessionID(){
		return this.sessionID;
	}

}
