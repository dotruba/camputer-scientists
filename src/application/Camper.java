package application;

public class Camper {
	
	int camperID;
	String name;
	String address;
	String phoneNo;
	String email;
	
	public Camper(String name, String address, String phoneNo, String email){
		// TODO: generate camperID
		this.camperID = 0;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.email = email;
	}
	
	public String getCamperName(){
		return this.name;
	}
	
	public String getCamperAddress(){
		return this.address;
	}
	
	public String getCamperPhone(){
		return this.phoneNo;
	}
	
	public String getCamperEmail(){
		return this.email;
	}
	
	public int getCamperID(){
		return this.camperID;
	}

}
