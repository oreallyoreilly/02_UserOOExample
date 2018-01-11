package com.oreallyoreilly.UserOOExample;

import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*****************************************************************
 *
 *	Date: 2017
 *	@author COR
 *  
 * References: 
 * 
 * https://github.com/xerial/sqlite-jdbc#using-sqlitejdbc-with-maven2
 * http://tutorials.jenkov.com/java-collections/list.html
 * http://www.sqlitetutorial.net/sqlite-date/
 * https://github.com/xerial/sqlite-jdbc
 * https://stackoverflow.com/questions/16725377/no-suitable-driver-found-sqlite
 * https://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html#ServicesResourceTransformer
 * 
 * The purpose of this application is to provide an example for the following:
 * - Demonstrates the use of development tools : GIT, MAVEN, Eclipse
 * - Demonstrates how to use Eclipse
 * - Provides a refresher of OOP in Java
 * - Query a SQLite database User table and displays the users
 * - Provide an introduction to collections
 * 
 * 	TODO: 
 * 	- It would be better to take the location of the sqlite database as a parameter
 * 	- The ListArray<User> is populated but not really used ( just a prep for later examples)
 * 	
 *****************************************************************/

public class App
{ 
	
	public static void main(String args[])
	{
			App anApp = new App();
	}
	
	// DATA
	//............................................................
	//declare objects
	
	// for windows the database file location would be: "jdbc:sqlite:C://Dropbox/_DEV2017/DATA/oreallyoreilly.db"
	// for MAC or Linux database file location would be: "jdbc:sqlite:/Volumes/DataHD/Dropbox/_DEV2017/DATA/oreallyoreilly.db"
	
	private String databaseFile = "jdbc:sqlite:/Volumes/DataHD/Dropbox/_DEV2017/DATA/oreallyoreilly.db";
	private	Scanner someInput;
	private Date today;
	
	@SuppressWarnings("unused")
	private User user;
	private Role role;
	private ArrayList<User> userList;
	
	// CONSTRUCTORS
	//............................................................
	
	public App()
	{
		//initialise variables
		
        //create objects 
		this.someInput = new Scanner(System.in);
		
		//Create a list of Users
        showListOfUsers();		
		
		//pause before exit (this is only useful if an error occurs)
        System.out.println(" \n Press enter to exit the program");
		this.someInput.nextLine();

		//close the program without error
		System.exit(0);
	}
	
	// METHODS
	//............................................................
	
	private void showListOfUsers()
	{

		this.today = new Date();
		System.out.println( "Getting list of Users from Database as of " + today );
     	this.userList = new ArrayList<User>();
		
        // make sure it can find the sqlite class in the Maven built JAR
		// Ref: 
     	// What to add into the Maven POM.xml: https://github.com/xerial/sqlite-jdbc
     	// use of transformer: https://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html#ServicesResourceTransformer
		// To use the sqlite driver the Maven POM.xml must be updated to include the class and a transformer
     	
     	// Below is debug code used to check that the sqlite class was being included in the build JAR
		/*
		try
		{
      	 Class.forName("org.sqlite.JDBC");
		}
        catch (ClassNotFoundException e) 
        {
			// if can't find the sqlite class in the deployment JAR
        		// see message: No suitable driver found for jdbc:sqlite:/xx.db
            System.err.println(e.getMessage());
		}
		*/
		
		// Get JDBC connection to database
		
		Connection connection = null;
        try
        {
        	  // create a database connection
          connection = DriverManager.getConnection( this.databaseFile);
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          
          // Run the query
          
          ResultSet resultSet = statement.executeQuery("select * from user");
          
          // iterate through the results create User objects put in the ListArray
          
          while(resultSet.next())
          {
        	  	  User user  = new User();
        	  	  
              user.setUserID(resultSet.getInt("userID"));
              user.setUserName(resultSet.getString("userName"));
              user.setUserEmail(resultSet.getString("userEmail"));
              user.setUserRole(resultSet.getString("userRole"));
              user.setUserToken(resultSet.getString("userToken"));
              user.setUserStatus(resultSet.getInt("userStatus"));
              user.setUserLastUpdate(resultSet.getString("userLastUpdate"));
              
              // putting the user objects into the list but not using them yet
              this.userList.add(user);
              
              // print the results by using the toString() on User
              System.out.println(user);
          }
        	  
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        } 
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e);
          }
        }
		
	}//EOM
	
}//EOC
