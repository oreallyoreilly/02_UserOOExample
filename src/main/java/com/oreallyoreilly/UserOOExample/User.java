package com.oreallyoreilly.UserOOExample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*****************************************************************
*	Date: 2017
*	@author COR
*  
* User class
* 
*****************************************************************/

public class User {
	
	// DATA
	//............................................................
	//declare objects
	
	private int 	userID;
	private String userName;
	private String userEmail;
	private String userRole;
	private String userToken;
	private int 	userStatus;
	private String userLastUpdate;
	
	// This is added to every class that needs to log with one change
	// The getLogger( ) part should contain the name of the class its in
	// So you know the messages that came from objects of this class
	private final Logger LOG = LogManager.getLogger(User.class);
	
	
	// CONSTRUCTORS
	//............................................................
	
	// if you comment out this constructor you will see errors in App as it uses
	// setters and getters to create the user objects - good for demo if errors in IDE
	
	public User()
	{
		LOG.debug("Empty user object created");
	}
	
	// Second constructor allowed as a different signature
	
	public User(
			int userID, 
			String userName, 
			String userEmail, 
			String userRole, 
			String userToken, 
			int userStatus,
			String userLastUpdate) 
	{
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userRole = userRole;
		this.userToken = userToken;
		this.userStatus = userStatus;
		this.userLastUpdate = userLastUpdate;
		
		LOG.debug("User object created : " + this.toString());
	}

	// METHODS - Gets and Sets
	//............................................................
	
	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	public String getUserToken() {
		return userToken;
	}


	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}


	public int getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}


	public String getUserLastUpdate() {
		return userLastUpdate;
	}


	public void setUserLastUpdate(String userLastUpdate) {
		this.userLastUpdate = userLastUpdate;
	}
	
	/**
	 * Overriding the Object method toString with an implementation for this class
	 */

	@Override
	public String toString() {
		return String.format(
				"User [userID=%s, userName=%s, userEmail=%s, userRole=%s, userToken=%s, userStatus=%s, userLastUpdate=%s]",
				userID, userName, userEmail, userRole, userToken, userStatus, userLastUpdate);
	}
	
	


}
