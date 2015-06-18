package application;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dbQueryLibraries.AdminQueries;
import dbQueryLibraries.CamperQueries;
import dbQueryLibraries.CouncellorQueries;

public class Base
{
	// Query libraries
	AdminQueries adminQuery = new AdminQueries();
	CamperQueries camperQuery = new CamperQueries();
	CouncellorQueries councellorQuery = new CouncellorQueries();

	// list of activities
	private ArrayList<String> activityList;
	// ArrayList of check boxes
	private ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
	// list of sessions
	private ArrayList<Session> availableSessions = new ArrayList<Session>();
	//drop down menu
	private JComboBox<Session> selectSession = new JComboBox<Session>();
	private JComboBox<String> paySelect = new JComboBox<String>();
	private JComboBox<Session> selectSession2 = new JComboBox<Session>();

	// Connection object
	private Connection con;

	// main frame for our menu
	private JFrame menuFrame;
	private final CardLayout cl = new CardLayout();
	private final JPanel pages = new JPanel(cl);
	
	// Labels
	private JLabel menuQuestion = new JLabel("Are you a:");
	private JLabel adminQuestion = new JLabel("What would you like to do?");
	private JLabel counsellorQuestion = new JLabel("What would you like to do?");
	private JLabel camperQuestion = new JLabel("What would you like to do? ");
	private JLabel searchActivitybyCamp = new JLabel("Enter a camp to find the activities it offers: ");
	private JLabel searchCampbyActivity = new JLabel("Find camp(s) that offers all of the selected activities: ");
	private JLabel registerNameLabel = new JLabel("Name: ");
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
	private JLabel superviseCheckLabel = new JLabel("Enter your ID to check the campers under your supervision: ");
	private JLabel camperCabinLabel = new JLabel("Enter a Registration # to assign Camper to cabin: "); 
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
	private JLabel regCampNameLabel = new JLabel("Enter the camp to register: ");
	private JLabel confNoLabel = new JLabel("Enter your confirmation#: ");
	private JLabel payLabel = new JLabel("Complete registration payment: ");
	private JLabel cancelLabel = new JLabel("Enter confirmation# to cancel registration: ");
	private JLabel changeLabel = new JLabel("Enter confirmation# to change registered session: ");
	
	// textfields
	private JTextField registerNameTxt = new JTextField(10);
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
	private JTextField regCampNameTxt = new JTextField(10);
	private JTextField payConfNoTxt = new JTextField(10);
	private JTextField cancelConfNoTxt = new JTextField(10);
	private JTextField changeTxt = new JTextField(10);
	
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
	JButton registeredB = new JButton("I'm already registered!");
	JButton notRegisteredB = new JButton("I want to sign up for a Camp!");
	JButton superviseCheckButton = new JButton("Check");
	JButton camperCabinButton = new JButton("Assign");
	JButton checkPayButton = new JButton("Find");
	JButton offerButton = new JButton("Offer");
	JButton checkRegButton = new JButton("Check");
	JButton multiSessionButton = new JButton("Find campers registered in more than 1 session");
	JButton deleteCamperButton = new JButton("Delete");
	JButton statButton = new JButton("Show registration statistics. ");
	
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
		// Login to database
		try 
		{
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connect();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}

		// Some initialization that requires the connection to be established

		// Populate available sessions dropdown
		availableSessions = camperQuery.getAllSessions(con);
		for(Session s : availableSessions){
			selectSession.addItem(s);
			selectSession2.addItem(s);
		}

		// Populate activity list and generate checkboxes
		activityList = camperQuery.getAllActivities(con);
		createCheckBox();
		
		menuFrame = new JFrame("Main Menu");

		// Panel creation
		JPanel menuPane = new JPanel();
		menuFrame.setContentPane(menuPane);
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
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
		
		JPanel camperQueryPage = new JPanel(new GridBagLayout());
		camperQueryPage.setLayout(gb);
		camperQueryPage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pages.add(camperQueryPage, "camperQuery");
		
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
		// admin panel
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

		//TODO: 6.14 (still need to do testing) (remove this maybe? might not be worth the time)
		multicampButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
				try {
					adminQuery.checkCounsellor(con);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
                
            }});
		
		gb.setConstraints(statButton, c);
		adminPanel.add(statButton);
		
		// GET STATISTICS
		statButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AdminQueries aq = new AdminQueries();
				try {
					ArrayList<String> stats = new ArrayList<String>();
					stats = aq.getStats(con);
					StringBuilder string = new StringBuilder();
					for(String s: stats){
						string.append(s);
						string.append("\n");
					}
					String toPrint = string.toString();
					Popup.infoBox(toPrint, "Camp Statistics");
					
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
		// 6.17 assign camper cabin
		camperCabinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CouncellorQueries cq = new CouncellorQueries();
				int confNo = Integer.parseInt(cabinCamperIDTxt.getText());
				try {
					int updated = cq.assignCamperToCabin(con, confNo);
					if (updated == 1){
						Popup.infoBox("Camper has been assigned to least full cabin.", "Complete");
					} else {
						Popup.infoBox("No registration for given id, please try again", "Incomplete");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
					Popup.infoBox("ERROR - please check your inputs", "ERROR");
				}
			}
		});
		
		
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

		// 6.18 offer activity
		offerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String activityName = offerActivityTxt.getText();
				String campName = offerCampTxt.getText();
				try {
					councellorQuery.offerActivity(con, campName, activityName);
					Popup.infoBox(campName +  " now offers " + activityName, "Activity Added");
				} catch (SQLException e1) {
					// POSSIBLE UPDATE: if the activity does not exist, direct them somewhere where the can create a new activit.

					Popup.infoBox("That activity or camp does not exist. Please check your input.", "Error");
				}
			}
		});
		
		
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

		// 6.19 check registered for session
		checkRegButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String campName = registeredForCampTxt.getText();
				int sessionID = Integer.parseInt(registeredForSessionTxt.getText());
				try {					
					ArrayList<String> campers = councellorQuery.getRegisteredCampers(con, campName, sessionID);
					StringBuilder c = new StringBuilder();
					for (String s : campers){
						c.append(s + "\n");
					}
					
					Popup.infoBox(c.toString(), "Registered Campers");	
				} catch (SQLException e1) {
					Popup.infoBox("HELP ME", "Error");
					e1.printStackTrace();
					System.out.println(e1);
				}
			}});
		
		gb.setConstraints(multiSessionButton, c);
		counsellorPanel.add(multiSessionButton);

		//6.20 multiple sessions
		multiSessionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					StringBuilder c = new StringBuilder();
					ArrayList<String> campers = adminQuery.multipleSessions(con);
					for (String s : campers){
						c.append(s + "\n");
					}
					Popup.infoBox(c.toString(), "Registered Campers");	
				} catch (SQLException e1) {
					Popup.infoBox("HELP ME", "Error");
					e1.printStackTrace();
					System.out.println(e1);
				}
			}});
		
		
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
		gb.setConstraints(registerSessionLabel, c);
		registerPage.add(registerSessionLabel);
		gb.setConstraints(selectSession, c);
		registerPage.add(selectSession);
		gb.setConstraints(regCampNameLabel, c);
		registerPage.add(regCampNameLabel);
		gb.setConstraints(regCampNameTxt, c);
		registerPage.add(regCampNameTxt);
		gb.setConstraints(registerPayLabel, c);
		registerPage.add(registerPayLabel);
		gb.setConstraints(paySelect, c);
		registerPage.add(paySelect);
		gb.setConstraints(registerNowButton, c);
		registerPage.add(registerNowButton);
		
		// Initialize pay select dropdown
		paySelect.addItem("Yes");
		paySelect.addItem("No");

		// 6.1 registration
		registerNowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String isPaying = (String) paySelect.getSelectedItem();
				String name = registerNameTxt.getText();
				String address = addressTxt.getText();
				String phone = phoneTxt.getText();
				String email = emailTxt.getText();
				Session selectedSession = (Session) selectSession.getSelectedItem();
				String campName = regCampNameTxt.getText();
				int confNo;
				int id;
				try {
					// try and add Camper to Camper Table, and then register.
					id = camperQuery.addCamper(con, name, address, phone, email);

					confNo = camperQuery.completeRegistration(con, id, selectedSession.getId(), campName);
					
					if(isPaying.equalsIgnoreCase("yes")){
						camperQuery.makePayment(con, confNo);
					}
					Popup.infoBox("Registration is complete. Your confirmation number is " + confNo, "Complete");					
				} catch (SQLException e1) {
					System.out.println("Error caught in first catch");
					//e1.printStackTrace();
					System.out.println(e1);	
					// Try and see if the camper is already in the database
					// register with already existing camperID
					try {
						id = camperQuery.getCamperID(con, email);
						confNo = camperQuery.completeRegistration(con, id, selectedSession.getId(), campName);
						Popup.infoBox("Registration is complete. Your confirmation number is " + confNo, "Complete");
						if((String)paySelect.getSelectedItem()=="yes"){
							camperQuery.makePayment(con, confNo);
						}
					// reaches this case if already registered in that session
					// or if camp name does not exist.
					} catch (SQLException e2){
						Popup.infoBox("Registration not completed. Please ensure you are picking a unique session"
								+ " and have entered a correct camp name.", "Error");
						e2.printStackTrace();
						System.out.println(e2);
						System.out.println("Error caught in second catch");
					}
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
		camperQueryPage.add(camperQuestion);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(payLabel, c);
		camperQueryPage.add(payLabel);
		gb.setConstraints(confNoLabel, c);
		camperQueryPage.add(confNoLabel);
		gb.setConstraints(payConfNoTxt, c);
		camperQueryPage.add(payConfNoTxt);
		gb.setConstraints(completePaymentButton, c);
		camperQueryPage.add(completePaymentButton);

		completePaymentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confNo = Integer.parseInt(payConfNoTxt.getText());
				try {
					camperQuery.makePayment(con, confNo);
				} catch (SQLException e1) {
					Popup.infoBox("Invalid registration#.", "#oops");
					e1.printStackTrace();
				}
			}});
		
		gb.setConstraints(searchActivitybyCamp, c);
		camperQueryPage.add(searchActivitybyCamp);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(findActivitybyCampTxt, c);
		camperQueryPage.add(findActivitybyCampTxt);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(activitybyCampButton, c);
		camperQueryPage.add(activitybyCampButton);
		
		// Activities by camp
		// POSSIBLE CHANGE: change camp name to a dropdown instead of a text field?
		activitybyCampButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String campName = findActivitybyCampTxt.getText();
				ArrayList<String> a = new ArrayList<String>();
				
				try {
					a = camperQuery.findCampActivities(con, campName);
					if (a.isEmpty()){
						Popup.infoBox("No activities offered. Please check name for correctness, or enter a different camp name", "#oops");
					} else {
					
					String acts = "";
					for (String act : a){
						acts = acts + act + "\n";
					}
					Popup.infoBox(acts, "Activities!");
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
					Popup.infoBox("No such camp. ERROR CANNOT COMPUTE", "Go away!");
				}
			}});
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(searchCampbyActivity, c);
		camperQueryPage.add(searchCampbyActivity);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		for(int i=0; i<boxList.size();i++){
			JCheckBox box = boxList.get(i);
			gb.setConstraints(box, c);
			camperQueryPage.add(box);
		}
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(campbyActivityButton, c);
		camperQueryPage.add(campbyActivityButton);

		campbyActivityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		
		gb.setConstraints(changeLabel, c);
		camperQueryPage.add(changeLabel);
		gb.setConstraints(changeTxt, c);
		camperQueryPage.add(changeTxt);
		gb.setConstraints(selectSession2, c);
		camperQueryPage.add(selectSession2);
		gb.setConstraints(changeSessionButton, c);
		camperQueryPage.add(changeSessionButton);
		changeSessionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CamperQueries camperQuery = new CamperQueries();
				int confNo = Integer.parseInt(changeTxt.getText());
				Session selectedSession = (Session) selectSession2.getSelectedItem();
				try {
					camperQuery.switchSession(con, confNo, selectedSession.getId());
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
					Popup.infoBox("Please check your input.", "Error");
				}	
			}});
		
		gb.setConstraints(cancelLabel, c);
		camperQueryPage.add(cancelLabel);
		gb.setConstraints(cancelConfNoTxt, c);
		camperQueryPage.add(cancelConfNoTxt);
		gb.setConstraints(cancelRegButton, c);
		camperQueryPage.add(cancelRegButton);
		cancelRegButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CamperQueries camperQuery = new CamperQueries();
				int confNo = Integer.parseInt(cancelConfNoTxt.getText());
				try {
					camperQuery.cancelRegistration(con, confNo);
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
				
			}});
		
		
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(backToCamperSelect2, c);
		camperQueryPage.add(backToCamperSelect2);
		backToCamperSelect2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "camper");
			}});


		
		menuPane.add(pages);

		menuFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});
		// size the window to obtain a best fit for the components
		menuFrame.pack();
		// center the frame
		Dimension d2 = menuFrame.getToolkit().getScreenSize();
		Rectangle r2 = menuFrame.getBounds();
		menuFrame.setLocation( (d2.width - r2.width)/2, (d2.height - r2.height)/2 );

		menuFrame.setVisible(true);
	}

	/*
	 * Connect to Oracle database named ug using hardcoded user
	 */ 
	private void connect() throws SQLException
	{
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

		con = DriverManager.getConnection(connectURL,"ora_m4c8","a40441115");

		System.out.println("\nConnected to Oracle!");

		//FOR TESTING PURPOSES - KAITLYN
		//cq.addCamper(con, "Bobby Tables", "123 Peach Street", "778-985-6655", "lol@hotmail.com");
		//cq.completeRegistration(con, 102, 4, "Sculptural Pursuit");
		//cq.makePayment(con, 110);
		//cq.findCampActivities(con, "Beachside Fitness");
		//cq.getAllActivities(con);
		//ArrayList<String> activities = new ArrayList<String>();
		//activities.add("5km Beach Run");
		//activities.add("Beach Volleyball");
		//cq.findCampsOfferingActivities(con, activities);
		//cq.cancelRegistration(con, 104);
		//cq.switchSession(con, 101, 5);
		//cq.getRegistration(con, 101);
		//ccq.offerActivity(con, "Beachside Fitness", "CPR Rescue Breathing");
		//ccq.addActivity(con, "Swimming Lessons", "Learn how to swim!", "Life jackets, first aid kit");
		//ccq.getRegisteredCampers(con, "Sculptural Pursuit", 5);
		//camperQuery.getAllCamps(con);
		//AdminQueries aq = new AdminQueries();
		//aq.getStats(con);
		
	}

	public static void main(String args[]) throws SQLException
	{
		@SuppressWarnings("unused")
		Base b = new Base();
	}
}
