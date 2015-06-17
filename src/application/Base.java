package application;

// We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;

import dbQueryLibraries.AdminQueries;
import dbQueryLibraries.CamperQueries;
import dbQueryLibraries.CouncellorQueries;

import com.apple.laf.resources.aqua;

import dbQueryLibraries.AdminQueries;
import dbQueryLibraries.CamperQueries;

import java.awt.*;
import java.awt.event.*;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class Base implements ActionListener
{
	// list of activities
	private ArrayList<String> activityList;
	// ArrayList of checkboxes
	private ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
	// list of sessions
	private ArrayList<Session> session = new ArrayList<Session>();
	//drop down menu
	private JComboBox selectSession = new JComboBox();
	private JComboBox paySelect = new JComboBox();
	// command line reader 
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private Connection con;
	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JFrame mainFrame;
// main frame for our menu
	private JFrame menuFrame;
	private final CardLayout cl = new CardLayout();
	private final JPanel pages = new JPanel(cl);
	
// Labels
	private JLabel usernameLabel = new JLabel("Enter username: ");
	private JLabel passwordLabel = new JLabel("Enter password: ");
	private JLabel menuQuestion = new JLabel("Are you a:");
	private JLabel adminQuestion = new JLabel("What would you like to do?");
	private JLabel counsellorQuestion = new JLabel("What would you like to do?");
	private JLabel camperQuestion = new JLabel("What would you like to do? ");
	private JLabel searchActivitybyCamp = new JLabel("Enter a camp to find the activities it offers: ");
	private JLabel searchCampbyActivity = new JLabel("Find camp(s) that offers all of the selected activities: ");
	private JLabel registerNameLabel = new JLabel("Name: ");
	private JLabel registerPhoneLabel = new JLabel("Phone: ");
	private JLabel registerSessionLabel = new JLabel("Select a session: ");
	private JLabel registerPayLabel = new JLabel("Pay now?");
	private JLabel cabinSupervisorLabel = new JLabel("Assign counsellor to supervise a cabin: ");
	private JLabel cabinLabel = new JLabel("Cabin ID: ");
	private JLabel counsellorLabel = new JLabel("Counsellor ID: ");
	private JLabel setWorkAtLabel = new JLabel("Assign counsellor to work at the camp: ");
	private JLabel instructorWorkAtLabel = new JLabel("Counsellor ID:  ");
	private JLabel campNameLabel = new JLabel("Camp Name: ");
	private JLabel multicampLabel = new JLabel("Enter campers and camps to find those registered in more than 1 of the given camps: ");
	private JLabel assignRegLabel = new JLabel("Assign registration to a counsellor: ");
	private JLabel regLabel = new JLabel("Confirmation#: ");
	private JLabel insRegLabel = new JLabel("Counsellor ID: ");
	private JLabel checkRegLabel = new JLabel("Check registration payment: ");
	private JLabel superviseCheckLabel = new JLabel("Enter your ID to check the campers under your supervision: ");
	private JLabel camperCabinLabel = new JLabel("Enter camper's ID to assign him/her to cabin: "); 
	private JLabel checkPayLabel = new JLabel("Get phone# for campers who haven't paid from this camp: "); 
	private JLabel offerActivityLabel = new JLabel("Offer a new activity: "); 
	private JLabel offerCampLabel = new JLabel("Enter the camp where it will take place: ");
	private JLabel ActivityNameLabel = new JLabel("Enter the name of the new activity: ");
	private JLabel registeredForLabel = new JLabel("Find campers registered in this camp during this session: ");
	private JLabel registeredForCampLabel = new JLabel("Camp: ");
	private JLabel registeredForSessionLabel = new JLabel("Session: ");
	private JLabel deleteCamperLabel = new JLabel("Delete this camper: ");
	private JLabel multiCampsLabel = new JLabel("Camps: ");
	private JLabel multiCamperLabel = new JLabel("Campers: ");
	private JLabel addressLabel = new JLabel("Address: ");
	private JLabel phoneLabel = new JLabel("Phone: ");
	private JLabel emailLabel = new JLabel("Email: ");
	private JLabel campeNameLabel = new JLabel("Enter the camp to register: ");
	private JLabel confNoLabel = new JLabel("Enter your confirmation#: ");
	private JLabel payLabel = new JLabel("Complete registration payment: ");

// textfields
	private JTextField registerPhoneTxt = new JTextField(10);
	private JTextField registerNameTxt = new JTextField(10);
	private JTextField registerPayTxt = new JTextField(10);
	private JTextField cabinIDtxt = new JTextField(10);
	private JTextField counsellorTxt = new JTextField(10);
	private JTextField campNameTxt = new JTextField(10);
	private JTextField insIDTxt = new JTextField(10);
	private JTextField regNumTxt = new JTextField(10);
	private JTextField insRegTxt = new JTextField(10);
	private JTextField multicampTxt = new JTextField(10);
	private JTextField findActivitybyCampTxt = new JTextField(10);
	private JTextField insIDsuperviseTxt = new JTextField(10);
	private JTextField cabinCamperIDTxt = new JTextField(10);
	private JTextField checkPayTxt = new JTextField(10);
	private JTextField offerCampTxt = new JTextField(10);
	private JTextField offerActivityTxt = new JTextField(10);
	private JTextField registeredForCampTxt = new JTextField(10);
	private JTextField registeredForSessionTxt = new JTextField(10);
	private JTextField deleteCamperTxt = new JTextField(10);
	private JTextField multiCamperTxt = new JTextField(10);
	private JTextField addressTxt = new JTextField(10);
	private JTextField phoneTxt = new JTextField(10);
	private JTextField emailTxt = new JTextField(10);
	private JTextField campeNameTxt = new JTextField(10);
	private JTextField payConfNoTxt = new JTextField(10);
	
//Buttons
	JButton backToUser = new JButton("Return to user selection.");
	JButton backToUser2 = new JButton("Return to user selection.");
	JButton backToUser3 = new JButton("Return to user selection.");
	JButton backToCamperSelect = new JButton("Return to last page.");
	JButton backToCamperSelect2 = new JButton("Return to last page.");
	JButton adminButton = new JButton("Administrator");
	JButton cButton = new JButton("Counsellor");
	JButton camperButton = new JButton("Camper");
	JButton completePaymentButton = new JButton("Complete payment");
	JButton cancelRegButton = new JButton("Cancel registration");
	JButton changeSessionButton = new JButton("Change registered session");
	JButton cabinSupervisorButton = new JButton("Assign");
	JButton workAtButton = new JButton("Assign");
	JButton assignRegButton = new JButton("Assign");
	JButton multicampButton = new JButton("Find");
	JButton worklessInstructorButton = new JButton("Find idle counsellor");
	JButton registerNowButton = new JButton("Register Now");
	JButton activitybyCampButton = new JButton("Find");
	JButton campbyActivityButton = new JButton("Find");
	JButton registeredB = new JButton("I'm a registered camper.");
	JButton notRegisteredB = new JButton("I'm a new camper.");
	JButton superviseCheckButton = new JButton("Check");
	JButton camperCabinButton = new JButton("Assign");
	JButton checkPayButton = new JButton("Find");
	JButton offerButton = new JButton("Offer");
	JButton checkRegButton = new JButton("Check");
	JButton multiSessionButton = new JButton("Find campers registered in more than 1 session");
	JButton deleteCamperButton = new JButton("Delete");
	
//Creating activity check boxes
	private void createCheckBox() {
		for(int i = 0; i<activityList.size();i++){
			String label;
			label = activityList.get(i);

			JCheckBox box = new JCheckBox(label);
			boxList.add(box);
		}
	}

	/*
	 * constructs menu and loads JDBC driver
	 */ 
	public Base() throws SQLException
	{

//login window
		mainFrame = new JFrame("User Login");
		menuFrame = new JFrame("Main Menu");
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');
		JButton loginButton = new JButton("Log In");
// Panel creation
		JPanel contentPane = new JPanel();
		JPanel menuPane = new JPanel();
		mainFrame.setContentPane(contentPane);
		menuFrame.setContentPane(menuPane);
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		menuPane.setLayout(gb);
		menuPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// add the pages to main menu
		JPanel userSelect = new JPanel(new GridBagLayout());
		userSelect.setLayout(gb);
		userSelect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(userSelect, "users");
		
		JPanel adminPanel = new JPanel(new GridBagLayout());
		adminPanel.setLayout(gb);
		adminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(adminPanel, "admin");
		
		JPanel counsellorPanel = new JPanel(new GridBagLayout());
		counsellorPanel.setLayout(gb);
		counsellorPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(counsellorPanel, "counsellor");
		
		JPanel camperPanel = new JPanel(new GridBagLayout());
		camperPanel.setLayout(gb);
		camperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(camperPanel, "camper");
		
		JPanel registerPage = new JPanel(new GridBagLayout());
		registerPage.setLayout(gb);
		registerPage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(registerPage, "register");
		
		JPanel camperQuery = new JPanel(new GridBagLayout());
		camperQuery.setLayout(gb);
		camperQuery.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(camperQuery, "camperQuery");
		
		JPanel completePayment = new JPanel(new GridBagLayout());
		completePayment.setLayout(gb);
		completePayment.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(completePayment, "pay");
		
// place the buttons and label for user selection page
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(menuQuestion, c);
		userSelect.add(menuQuestion);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(adminButton, c);
		userSelect.add(adminButton);
		adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(pages, "admin");
            }});

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(cButton, c);
		userSelect.add(cButton);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "counsellor");
			}});

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(camperButton, c);
		userSelect.add(camperButton);
		camperButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "camper");
			}});

// populate sub-pages with stuff
//admin panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(adminQuestion, c);
		adminPanel.add(adminQuestion);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(cabinSupervisorLabel, c);
		adminPanel.add(cabinSupervisorLabel);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(cabinLabel, c);
		adminPanel.add(cabinLabel);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(cabinIDtxt, c);
		adminPanel.add(cabinIDtxt);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(counsellorLabel, c);
		adminPanel.add(counsellorLabel);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(counsellorTxt, c);
		adminPanel.add(counsellorTxt);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(cabinSupervisorButton, c);
		adminPanel.add(cabinSupervisorButton);
		//6.9
		cabinSupervisorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminQueries adminQuery = new AdminQueries();
				Integer cabin = Integer.parseInt(cabinIDtxt.getText());
				Integer counsellor = Integer.parseInt(counsellorTxt.getText());
					try {
						adminQuery.assignCabinSupervisor(con, cabin, counsellor);
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.out.println(e1);
					}
		
			}});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(setWorkAtLabel, c);
		adminPanel.add(setWorkAtLabel);
		gb.setConstraints(instructorWorkAtLabel, c);
		adminPanel.add(instructorWorkAtLabel);
		gb.setConstraints(insIDTxt, c);
		adminPanel.add(insIDTxt);
		gb.setConstraints(campNameLabel, c);
		adminPanel.add(campNameLabel);
		gb.setConstraints(campNameTxt, c);
		adminPanel.add(campNameTxt);
		gb.setConstraints(workAtButton, c);
		adminPanel.add(workAtButton);
		//6.10 set works at
		workAtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int insID;
            	String campName;
            	insID = Integer.parseInt(insIDTxt.getText());
            	campName = campNameTxt.getText();
                AdminQueries adminQuery = new AdminQueries();
                try {
					adminQuery.setWorksAt(con, insID, campName);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(assignRegLabel, c);
		adminPanel.add(assignRegLabel);
		gb.setConstraints(regLabel, c);
		adminPanel.add(regLabel);
		gb.setConstraints(regNumTxt, c);
		adminPanel.add(regNumTxt);
		gb.setConstraints(insRegLabel, c);
		adminPanel.add(insRegLabel);
		gb.setConstraints(insRegTxt, c);
		adminPanel.add(insRegTxt);
		gb.setConstraints(assignRegButton, c);
		adminPanel.add(assignRegButton);
		//TODO: 6.11 (still need to do testing)
		assignRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int insID;
            	int confirmation;
            	insID = Integer.parseInt(insRegTxt.getText());
            	confirmation = Integer.parseInt(regNumTxt.getText());
                AdminQueries adminQuery = new AdminQueries();
                try {
					adminQuery.assignRegToCounsellor(con, insID, confirmation);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		gb.setConstraints(deleteCamperLabel, c);
		adminPanel.add(deleteCamperLabel);
		gb.setConstraints(deleteCamperTxt, c);
		adminPanel.add(deleteCamperTxt);
		gb.setConstraints(deleteCamperButton, c);
		adminPanel.add(deleteCamperButton);
		// new 6.12 Delete camper
		deleteCamperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int camperID;
            	camperID = Integer.parseInt(deleteCamperTxt.getText());
                AdminQueries adminQuery = new AdminQueries();
                try {
					adminQuery.deleteCamper(con, camperID);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
            }});
		
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(checkPayLabel, c);
		adminPanel.add(checkPayLabel);
		gb.setConstraints(checkPayTxt, c);
		adminPanel.add(checkPayTxt);
		gb.setConstraints(checkPayButton, c);
		adminPanel.add(checkPayButton);
		//6.13 check payment
		checkPayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> output;
                AdminQueries adminQuery = new AdminQueries();
                String campName = checkPayTxt.getText();
				try {
					adminQuery.checkRegPayments(con,campName);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		
		gb.setConstraints(multicampLabel, c);
		adminPanel.add(multicampLabel);
		gb.setConstraints(multiCampsLabel, c);
		adminPanel.add(multiCampsLabel);
		gb.setConstraints(multicampTxt, c);
		adminPanel.add(multicampTxt);
		gb.setConstraints(multiCamperLabel, c);
		adminPanel.add(multiCamperLabel);
		gb.setConstraints(multiCamperTxt, c);
		adminPanel.add(multiCamperTxt);
		gb.setConstraints(multicampButton, c);
		adminPanel.add(multicampButton);
		//TODO: 6.14 (still need to do testing)
		multicampButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminQueries adminQuery = new AdminQueries();
                String camperIDs = multiCamperTxt.getText();
                List<String> cID = Arrays.asList(camperIDs.split("\\s*,\\s*"));;
                ArrayList<Integer> camperIDList = new ArrayList<Integer>();
                int id;
                for(String s : cID){
                	id = Integer.parseInt(s);
                	camperIDList.add(id);
                }
                String campNames = multiCamperTxt.getText();
                List<String> cNL = Arrays.asList(campNames.split("\\s*,\\s*"));;
                ArrayList<String> campNameList = new ArrayList<String>(cNL);
                
				try {
					adminQuery.multipleCamps(con, camperIDList, campNameList);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		
		gb.setConstraints(worklessInstructorButton, c);
		adminPanel.add(worklessInstructorButton);
		//6.15 check counsellor role
		worklessInstructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminQueries adminQuery = new AdminQueries();
				try {
					adminQuery.checkCounsellor(con);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(backToUser, c);
		adminPanel.add(backToUser);
		backToUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(pages, "users");
            }});
		
//Counsellor page:
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(counsellorQuestion, c);
		counsellorPanel.add(counsellorQuestion);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(superviseCheckLabel, c);
		counsellorPanel.add(superviseCheckLabel);
		gb.setConstraints(insIDsuperviseTxt, c);
		counsellorPanel.add(insIDsuperviseTxt);
		gb.setConstraints(superviseCheckButton, c);
		counsellorPanel.add(superviseCheckButton);

		// 6.16 check campers to supervise
		superviseCheckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CouncellorQueries councellorQuery = new CouncellorQueries();
				Integer counsellorID = Integer.parseInt(insIDsuperviseTxt.getText());
				try {
					ArrayList<String> campers = councellorQuery.checkCamperSupervision(con, counsellorID);
					StringBuilder formattedOutput = new StringBuilder();
					for(String camper : campers) {
						formattedOutput.append(camper + "\n");
					}
					String output = formattedOutput.toString();
					if(output.isEmpty()) {
						output = "No campers supervised by this counsellor";
					}
					Popup.infoBox(output, "Counsellor " + counsellorID);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
			}
		});
		
		gb.setConstraints(camperCabinLabel, c);
		counsellorPanel.add(camperCabinLabel);
		gb.setConstraints(cabinCamperIDTxt, c);
		counsellorPanel.add(cabinCamperIDTxt);
		gb.setConstraints(camperCabinButton, c);
		counsellorPanel.add(camperCabinButton);
		//TODO: 6.17 assign camper cabin (do we need a 2nd input box here?)
		
		gb.setConstraints(offerActivityLabel, c);
		counsellorPanel.add(offerActivityLabel);
		gb.setConstraints(offerCampLabel, c);
		counsellorPanel.add(offerCampLabel);
		gb.setConstraints(offerCampTxt, c);
		counsellorPanel.add(offerCampTxt);
		gb.setConstraints(ActivityNameLabel, c);
		counsellorPanel.add(ActivityNameLabel);
		gb.setConstraints(offerActivityTxt, c);
		counsellorPanel.add(offerActivityTxt);
		gb.setConstraints(offerButton, c);
		counsellorPanel.add(offerButton);
		//TODO: 6.18 offer activity
		
		gb.setConstraints(registeredForLabel, c);
		counsellorPanel.add(registeredForLabel);
		gb.setConstraints(registeredForCampLabel, c);
		counsellorPanel.add(registeredForCampLabel);
		gb.setConstraints(registeredForCampTxt, c);
		counsellorPanel.add(registeredForCampTxt);
		gb.setConstraints(registeredForSessionLabel, c);
		counsellorPanel.add(registeredForSessionLabel);
		gb.setConstraints(registeredForSessionTxt, c);
		counsellorPanel.add(registeredForSessionTxt);
		gb.setConstraints(checkRegButton, c);
		counsellorPanel.add(checkRegButton);
		//TODO: 6.19 check registered for session
		
		gb.setConstraints(multiSessionButton, c);
		counsellorPanel.add(multiSessionButton);
		//TODO: 6.20 multiple sessions
		
		gb.setConstraints(backToUser2, c);
		counsellorPanel.add(backToUser2);
		backToUser2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "users");
			}});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(registeredB, c);
		camperPanel.add(registeredB);
		registeredB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(pages, "camperQuery");
            }});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(notRegisteredB, c);
		camperPanel.add(notRegisteredB);
		notRegisteredB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(pages, "register");
            }});

		
		gb.setConstraints(backToUser3, c);
		camperPanel.add(backToUser3);
		backToUser3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "users");
			}});
		
//camper register page
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(registerNameLabel, c);
		registerPage.add(registerNameLabel);
		gb.setConstraints(registerNameTxt, c);
		registerPage.add(registerNameTxt);
		gb.setConstraints(addressLabel, c);
		registerPage.add(addressLabel);
		gb.setConstraints(addressTxt, c);
		registerPage.add(addressTxt);
		gb.setConstraints(phoneLabel, c);
		registerPage.add(phoneLabel);
		gb.setConstraints(phoneTxt, c);
		registerPage.add(phoneTxt);
		gb.setConstraints(emailLabel, c);
		registerPage.add(emailLabel);
		gb.setConstraints(emailTxt, c);
		registerPage.add(emailTxt);
		
		gb.setConstraints(registerPhoneLabel, c);
		registerPage.add(registerPhoneLabel);
		gb.setConstraints(registerPhoneTxt, c);
		registerPage.add(registerPhoneTxt);
		gb.setConstraints(registerSessionLabel, c);
		registerPage.add(registerSessionLabel);
		gb.setConstraints(selectSession, c);
		registerPage.add(selectSession);
		gb.setConstraints(campeNameLabel, c);
		registerPage.add(campeNameLabel);
		gb.setConstraints(campeNameTxt, c);
		registerPage.add(campeNameTxt);
		gb.setConstraints(registerPayLabel, c);
		registerPage.add(registerPayLabel);
		gb.setConstraints(paySelect, c);
		registerPage.add(paySelect);
		gb.setConstraints(registerNowButton, c);
		registerPage.add(registerNowButton);
		//TODO: 6.1 registration (Still need testing and checking payment)
		registerNowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CamperQueries camperQuery = new CamperQueries();
				String pay = (String) paySelect.getSelectedItem();
				String name = registerNameTxt.getText();
				String address = registerPhoneTxt.getText();
				String phone = phoneTxt.getText();
				String email = emailTxt.getText();
				String sessionName = (String) selectSession.getSelectedItem();
				String campName = campeNameTxt.getText();
				int confNo;
				int id;
				try {
					id = camperQuery.addCamper(con, name, address, phone, email);
					confNo = camperQuery.completeRegistration(con, id, sessionName, campName);
					
					if((String)paySelect.getSelectedItem()=="yes"){
						camperQuery.makePayment(con, confNo);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
			}});
		
		gb.setConstraints(backToCamperSelect, c);
		registerPage.add(backToCamperSelect);
		backToCamperSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "camper");
			}});
		
//camper query page
		gb.setConstraints(camperQuestion, c);
		camperQuery.add(camperQuestion);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(payLabel, c);
		camperQuery.add(payLabel);
		gb.setConstraints(confNoLabel, c);
		camperQuery.add(confNoLabel);
		gb.setConstraints(payConfNoTxt, c);
		camperQuery.add(payConfNoTxt);
		gb.setConstraints(completePaymentButton, c);
		camperQuery.add(completePaymentButton);
		//TODO: 6.2 Make Payment (Still require testing)
		completePaymentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confNo = Integer.parseInt(payConfNoTxt.getText());
				CamperQueries camperQuery = new CamperQueries();
				try {
					camperQuery.makePayment(con, confNo);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}});
		
		gb.setConstraints(searchActivitybyCamp, c);
		camperQuery.add(searchActivitybyCamp);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(findActivitybyCampTxt, c);
		camperQuery.add(findActivitybyCampTxt);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(activitybyCampButton, c);
		camperQuery.add(activitybyCampButton);
		activitybyCampButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Popup.infoBox("Nothing here yo!", "Go away!");
				//TODO: 6.3 search activity by camp
			}});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(searchCampbyActivity, c);
		camperQuery.add(searchCampbyActivity);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		for(int i=0; i<boxList.size();i++){
			JCheckBox box = boxList.get(i);
			gb.setConstraints(box, c);
			camperQuery.add(box);
		}
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(campbyActivityButton, c);
		camperQuery.add(campbyActivityButton);
		//TODO: 6.4 search camps by activity
		campbyActivityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CamperQueries camperQuery = new CamperQueries();
				ArrayList<String> activities = new ArrayList<String>();
				for (int i = boxList.size() - 1; i >=0; i--)
				{
				    JCheckBox cb = boxList.get(i);
				    if (cb.isSelected())
				    {
				        activities.add(cb.getText());
				    }
				}
					try {
						camperQuery.findCampsOfferingActivities(con, activities);
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.out.println(e1);
					}
		
			}});
		
		gb.setConstraints(changeSessionButton, c);
		camperQuery.add(changeSessionButton);
		//TODO: 6.8 change registration
		
		gb.setConstraints(cancelRegButton, c);
		camperQuery.add(cancelRegButton);
		//TODO: 6.7 cancel registration
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(backToCamperSelect2, c);
		camperQuery.add(backToCamperSelect2);
		backToCamperSelect2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "camper");
			}});


		
		menuPane.add(pages);
		
//login related stuff:
		// place the username label 
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		contentPane.add(usernameLabel);
		// place the text field for the username 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);
		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);
		// place the password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);
		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		contentPane.add(loginButton);
		// register password field and OK button with action event handler
		passwordField.addActionListener(this);
		loginButton.addActionListener(this);
		// anonymous inner class for closing the window
		mainFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});
		menuFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});
		// size the window to obtain a best fit for the components
		mainFrame.pack();
		menuFrame.pack();
		// center the frame
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
		Dimension d2 = menuFrame.getToolkit().getScreenSize();
		Rectangle r2 = menuFrame.getBounds();
		menuFrame.setLocation( (d2.width - r2.width)/2, (d2.height - r2.height)/2 );
		// make the window visible
		mainFrame.setVisible(true);
		// place the cursor in the text field for the username
		usernameField.requestFocus();
		try 
		{
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}
	}


	/*
	 * 
s to Oracle database named ug using user supplied username and password
	 */ 
	private boolean connect(String username, String password)
	{
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

		try 
		{
			con = DriverManager.getConnection(connectURL,"ora_k2r7","a25920109");
			//con = DriverManager.getConnection(connectURL, username, password);

			System.out.println("\nConnected to Oracle!");
			

/*		FOR TESTING PURPOSES - KAITLYN	
 			CamperQueries cq = new CamperQueries();
			CouncellorQueries ccq = new CouncellorQueries();
			//cq.addCamper(con, "Bobby Tables", "123 Peach Street", "778-985-6655", "lol@hotmail.com");
			//cq.completeRegistration(con, 102, 4, "Sculptural Pursuit");
			//cq.makePayment(con, 110);
			//cq.findCampActivities(con, "Beachside Fitness");
			//cq.getAllActivities(con);
			ArrayList<String> activities = new ArrayList<String>();
			activities.add("5km Beach Run");
			activities.add("Beach Volleyball");
			//cq.findCampsOfferingActivities(con, activities);
			//cq.cancelRegistration(con, 104);
			//cq.switchSession(con, 101, 5);
			//cq.getRegistration(con, 101);
			//ccq.offerActivity(con, "Beachside Fitness", "CPR Rescue Breathing");
			//ccq.addActivity(con, "Swimming Lessons", "Learn how to swim!", "Life jackets, first aid kit");
			ccq.getRegisteredCampers(con, "Sculptural Pursuit", 5);
			*/
			
			return true;
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}
	/*
	 * event handler for login window
	 */ 
	public void actionPerformed(ActionEvent e) 
	{
		if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
		{
			// if the username and password are valid, 
			// remove the login window and display a text menu 
			mainFrame.dispose();
			menuFrame.setVisible(true);
			//initializing session drop down menu
			String yes = "Yes";
			String no = "No";
			paySelect.addItem(yes);
			paySelect.addItem(no);
			
			CamperQueries camperQueries = new CamperQueries();
			try {
				session = camperQueries.getAllSessions(con);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}
			for(Session s : session){
				selectSession.addItem((s.sessionToString()));
			}
			//populating dummy activity list
			activityList = new ArrayList<String>();
			try {
				activityList = camperQueries.getAllActivities(con);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}
			createCheckBox();
		}
		else
		{
			loginAttempts++;

			if (loginAttempts >= 3)
			{
				mainFrame.dispose();
				System.exit(-1);
			}
			else
			{
				// clear the password
				passwordField.setText("");
			}
		}             
	}

	public static void main(String args[]) throws SQLException
	{
		Base b = new Base();
		
		
	}
}
