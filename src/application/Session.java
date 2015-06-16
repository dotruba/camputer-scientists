package application;

public class Session {

	String name;
	// A string indicating start and end date for the session
	String description;
	
	public Session(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public String getSessionName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String sessionToString(){
		StringBuilder s = new StringBuilder(this.name + ": " + this.description);
		String stringSession = s.toString();
		
		return stringSession;
	}

}
