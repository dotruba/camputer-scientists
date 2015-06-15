package application;

// We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;

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
	// Arraylist of checkboxes
	private ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
	// list of sessions
	private ArrayList<String> session = new ArrayList<String>();
	
	//drop down menu
	private JComboBox selectSession = new JComboBox();
	
	
	// command line reader 
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private Connection con;

	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JFrame mainFrame;
	private JFrame menuFrame;
	
	private final CardLayout cl = new CardLayout();
	private final JPanel pages = new JPanel(cl);

// textfields
	private JTextField registerPhone = new JTextField(10);
	private JTextField registerName = new JTextField(20);
	private JTextField registerSession = new JTextField(10);
	private JTextField registerPay = new JTextField(20);
	
	private JTextField cabinIDtxt = new JTextField(10);
	private JTextField counsellorTxt = new JTextField(10);
	
	private JTextField campName = new JTextField(10);
	private JTextField insID = new JTextField(10);
	
	private JTextField regNum = new JTextField(10);
	private JTextField insReg = new JTextField(10);
	
	private JTextField multicamp = new JTextField(10);
	
	private JTextField checkRegTxt = new JTextField(10);
	
	private JTextField findActivitybyCampTxt = new JTextField(20);
	//private JTextField findCampbyActivityTxt = new JTextField(20);
	private JTextField insIDsupervise = new JTextField(10);
	private JTextField cabinCamperID = new JTextField(10);

	


	/*
	 * constructs login window and loads JDBC driver
	 */ 
	public Base()
	{
		
//initializing session drop down menu
		session.add("week1");
		session.add("week2");
		session.add("week3");
		for(int i=0; i<session.size();i++){
			selectSession.addItem(session.get(i));
		}

//populating dummy activity list
		activityList = new ArrayList<String>();
		activityList.add("swim");
		activityList.add("basketball");
		activityList.add("hockey");
		createCheckBox();
		
		mainFrame = new JFrame("User Login");
		menuFrame = new JFrame("Main Menu");

		JLabel usernameLabel = new JLabel("Enter username: ");
		JLabel passwordLabel = new JLabel("Enter password: ");

		JLabel menuQuestion = new JLabel("Are you a:");
		JLabel adminQuestion = new JLabel("What would you like to do?");
		JLabel counsellorQuestion = new JLabel("What would you like to do?");
		JLabel camperQuestion = new JLabel("What would you like to do? ");
		JLabel searchActivitybyCamp = new JLabel("Enter a camp to find the activities it offers: ");
		JLabel searchCampbyActivity = new JLabel("Find camp(s) that offers all of the selected activities: ");
		
		JLabel registerNameLabel = new JLabel("Name: ");
		JLabel registerPhoneLabel = new JLabel("Phone: ");
		JLabel registerSessionLabel = new JLabel("Select a session: ");
		JLabel registerPayLabel = new JLabel("Enter payment: ");
		
		JLabel cabinSupervisorLabel = new JLabel("Assign counsellor to supervise a cabin: ");
		JLabel cabinLabel = new JLabel("Cabin ID: ");
		JLabel counsellorLabel = new JLabel("Counsellor: ");
		
		JLabel setWorkAtLabel = new JLabel("Assign counsellor to work at the camp: ");
		JLabel instructorWorkAtLabel = new JLabel("Counsellor ID:  ");
		JLabel campNameLabel = new JLabel("Camp Name: ");
		
		JLabel multicampLabel = new JLabel("Enter campers and find those who registered for more than 1 camp: ");
		
		JLabel assignRegLabel = new JLabel("Assign registration to a counsellor: ");
		JLabel regLabel = new JLabel("Confirmation#: ");
		JLabel insRegLabel = new JLabel("Counsellor ID: ");
		
		JLabel checkRegLabel = new JLabel("Check registration payment: ");
		
		JLabel superviseCheckLabel = new JLabel("Enter your ID to check the campers under your supervision: ");
		JLabel camperCabinLabel = new JLabel("Enter camper's ID to assign him/her to cabin: "); 
		
		

		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');

		JButton loginButton = new JButton("Log In");
		
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
		
		
// place the buttons and label for main menu
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
		// admin button functionality
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
		// counsellor button functionality
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
		// camper button functionality
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
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(setWorkAtLabel, c);
		adminPanel.add(setWorkAtLabel);
		gb.setConstraints(instructorWorkAtLabel, c);
		adminPanel.add(instructorWorkAtLabel);
		gb.setConstraints(insID, c);
		adminPanel.add(insID);
		gb.setConstraints(campNameLabel, c);
		adminPanel.add(campNameLabel);
		gb.setConstraints(campName, c);
		adminPanel.add(campName);
		gb.setConstraints(workAtButton, c);
		adminPanel.add(workAtButton);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(assignRegLabel, c);
		adminPanel.add(assignRegLabel);
		gb.setConstraints(regLabel, c);
		adminPanel.add(regLabel);
		gb.setConstraints(regNum, c);
		adminPanel.add(regNum);
		gb.setConstraints(insRegLabel, c);
		adminPanel.add(insRegLabel);
		gb.setConstraints(insReg, c);
		adminPanel.add(insReg);
		gb.setConstraints(assignRegButton, c);
		adminPanel.add(assignRegButton);
		
		gb.setConstraints(multicampLabel, c);
		adminPanel.add(multicampLabel);
		gb.setConstraints(multicamp, c);
		adminPanel.add(multicamp);
		gb.setConstraints(multicampButton, c);
		adminPanel.add(multicampButton);
		
		gb.setConstraints(checkRegLabel, c);
		adminPanel.add(checkRegLabel);
		gb.setConstraints(checkRegTxt, c);
		adminPanel.add(checkRegTxt);
		
		gb.setConstraints(worklessInstructorButton, c);
		adminPanel.add(worklessInstructorButton);
		
		
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(backToUser, c);
		adminPanel.add(backToUser);
		// return to user select button functionality
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
		gb.setConstraints(insIDsupervise, c);
		counsellorPanel.add(insIDsupervise);
		gb.setConstraints(superviseCheckButton, c);
		counsellorPanel.add(superviseCheckButton);
		
		gb.setConstraints(camperCabinLabel, c);
		counsellorPanel.add(camperCabinLabel);
		gb.setConstraints(cabinCamperID, c);
		counsellorPanel.add(cabinCamperID);
		gb.setConstraints(camperCabinButton, c);
		counsellorPanel.add(camperCabinButton);
		
		gb.setConstraints(backToUser2, c);
		counsellorPanel.add(backToUser2);
		// return to user select button functionality
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
		// return to user select button functionality
		backToUser3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(pages, "users");
			}});
		
//register page
		gb.setConstraints(registerNameLabel, c);
		registerPage.add(registerNameLabel);
		gb.setConstraints(registerName, c);
		registerPage.add(registerName);
		gb.setConstraints(registerPhoneLabel, c);
		registerPage.add(registerPhoneLabel);
		gb.setConstraints(registerPhone, c);
		registerPage.add(registerPhone);
		gb.setConstraints(registerSessionLabel, c);
		registerPage.add(registerSessionLabel);
		
		gb.setConstraints(selectSession, c);
		registerPage.add(selectSession);
		
		gb.setConstraints(registerSession, c);
		registerPage.add(registerSession);
		gb.setConstraints(registerPayLabel, c);
		registerPage.add(registerPayLabel);
		gb.setConstraints(registerPay, c);
		registerPage.add(registerPay);
		gb.setConstraints(registerNowButton, c);
		registerPage.add(registerNowButton);
		
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
		
		gb.setConstraints(completePaymentButton, c);
		camperQuery.add(completePaymentButton);
		
		gb.setConstraints(searchActivitybyCamp, c);
		camperQuery.add(searchActivitybyCamp);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(findActivitybyCampTxt, c);
		camperQuery.add(findActivitybyCampTxt);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(activitybyCampButton, c);
		camperQuery.add(activitybyCampButton);
		activitybyCampButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Popup.infoBox("Nothing here yo!", "Go away!");
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
		//gb.setConstraints(findCampbyActivityTxt, c);
		//camperQuery.add(findCampbyActivityTxt);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		gb.setConstraints(campbyActivityButton, c);
		camperQuery.add(campbyActivityButton);
		gb.setConstraints(changeSessionButton, c);
		camperQuery.add(changeSessionButton);
		gb.setConstraints(cancelRegButton, c);
		camperQuery.add(cancelRegButton);
		
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
	 * connects to Oracle database named ug using user supplied username and password
	 */ 
	private boolean connect(String username, String password)
	{
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

		try 
		{
			con = DriverManager.getConnection(connectURL,username,password);

			System.out.println("\nConnected to Oracle!");
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
			// showMenu();     
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


	/*
	 * displays simple text interface
	 */ 
	private void showMenu()
	{
		int choice;
		boolean quit;

		quit = false;

		try 
		{
			// disable auto commit mode
			con.setAutoCommit(false);

			while (!quit)
			{
				System.out.print("\n\nPlease choose one of the following: \n");
				System.out.print("1.  Insert branch\n");
				System.out.print("2.  Delete branch\n");
				System.out.print("3.  Update branch\n");
				System.out.print("4.  Show branch\n");
				System.out.print("5.  Quit\n>> ");

				choice = Integer.parseInt(in.readLine());

				System.out.println(" ");

				switch(choice)
				{
				case 1:  insertBranch(); break;
				case 2:  deleteBranch(); break;
				case 3:  updateBranch(); break;
				case 4:  showBranch(); break;
				case 5:  quit = true;
				}
			}

			con.close();
			in.close();
			System.out.println("\nGood Bye!\n\n");
			System.exit(0);
		}
		catch (IOException e)
		{
			System.out.println("IOException!");

			try
			{
				con.close();
				System.exit(-1);
			}
			catch (SQLException ex)
			{
				System.out.println("Message: " + ex.getMessage());
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
	}


	/*
	 * inserts a branch
	 */ 
	private void insertBranch()
	{
		int                bid;
		String             bname;
		String             baddr;
		String             bcity;
		int                bphone;
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(1, bid);

			System.out.print("\nBranch Name: ");
			bname = in.readLine();
			ps.setString(2, bname);

			System.out.print("\nBranch Address: ");
			baddr = in.readLine();

			if (baddr.length() == 0)
			{
				ps.setString(3, null);
			}
			else
			{
				ps.setString(3, baddr);
			}

			System.out.print("\nBranch City: ");
			bcity = in.readLine();
			ps.setString(4, bcity);

			System.out.print("\nBranch Phone: ");
			String phoneTemp = in.readLine();
			if (phoneTemp.length() == 0)
			{
				ps.setNull(5, java.sql.Types.INTEGER);
			}
			else
			{
				bphone = Integer.parseInt(phoneTemp);
				ps.setInt(5, bphone);
			}

			ps.executeUpdate();

			// commit work 
			con.commit();

			ps.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException!");
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}


	/*
	 * deletes a branch
	 */ 
	private void deleteBranch()
	{
		int                bid;
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("DELETE FROM branch WHERE branch_id = ?");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(1, bid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nBranch " + bid + " does not exist!");
			}

			con.commit();

			ps.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException!");
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}


	/*
	 * updates the name of a branch
	 */ 
	private void updateBranch()
	{
		int                bid;
		String             bname;
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(2, bid);

			System.out.print("\nBranch Name: ");
			bname = in.readLine();
			ps.setString(1, bname);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0)
			{
				System.out.println("\nBranch " + bid + " does not exist!");
			}

			con.commit();

			ps.close();
		}
		catch (IOException e)
		{
			System.out.println("IOException!");
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				con.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}	
	}


	/*
	 * display information about branches
	 */ 
	private void showBranch()
	{
		String     bid;
		String     bname;
		String     baddr;
		String     bcity;
		String     bphone;
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM branch");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it

				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string

				// simplified output formatting; truncation may occur

				bid = rs.getString("branch_id");
				System.out.printf("%-10.10s", bid);

				bname = rs.getString("branch_name");
				System.out.printf("%-20.20s", bname);

				baddr = rs.getString("branch_addr");
				if (rs.wasNull())
				{
					System.out.printf("%-20.20s", " ");
				}
				else
				{
					System.out.printf("%-20.20s", baddr);
				}

				bcity = rs.getString("branch_city");
				System.out.printf("%-15.15s", bcity);

				bphone = rs.getString("branch_phone");
				if (rs.wasNull())
				{
					System.out.printf("%-15.15s\n", " ");
				}
				else
				{
					System.out.printf("%-15.15s\n", bphone);
				}      
			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}


	public static void main(String args[])
	{
		Base b = new Base();
	}
}
