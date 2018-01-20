package com.oreallyoreilly.UserOOExample;

import java.util.Date;
import java.util.Scanner;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Arrays;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.lookup.EnvironmentLookup;


/*****************************************************************
 *
 *	Date: 2017
 *	@author COR
 *  
 * References: 
 * 
 * Eclipse
 * Ctrl + S to refresh problems if seeing missing method errors but they are there as added manually
 * Or pick create method with signature option when hover over the problem message 
 * and it will pick up your method or create a new one then you know you're in the wrong and not eclipse!
 * http://eclipsetutorial.sourceforge.net/totalbeginner.html
 * 
 * SQLite References
 * https://github.com/xerial/sqlite-jdbc#using-sqlitejdbc-with-maven2
 * http://tutorials.jenkov.com/java-collections/list.html
 * http://www.sqlitetutorial.net/sqlite-date/
 * http://www.sqlitetutorial.net/sqlite-java/
 * https://github.com/xerial/sqlite-jdbc
 * https://stackoverflow.com/questions/16725377/no-suitable-driver-found-sqlite
 * https://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html#ServicesResourceTransformer
 * https://www.thoughtco.com/using-command-line-arguments-2034196
 * https://stackoverflow.com/questions/8944199/how-to-deal-with-command-line-arguments-in-java
 * 
 * Command Line References
 * http://pholser.github.io/jopt-simple/
 * Maven: Jopt-Simple download: http://pholser.github.io/jopt-simple/download.html
 * https://dzone.com/articles/java-clis-part-6-jopt-simple
 * https://github.com/dustinmarx/java-cli-demos
 * http://marxsoftware.blogspot.ie/2017/10/diy-commandline.html
 * http://marxsoftware.blogspot.ie/2017/11/java-cmd-line-observations.html
 * https://github.com/remkop/picocli/wiki/CLI-Comparison
 * http://www.massapi.com/source/github/20/81/2081705684/src/java/voldemort/tools/ExportBDBToTextDump.java.html#88
 * 
 * Logging References
 * https://dzone.com/articles/how-configure-slf4j-different
 * https://stackify.com/log4j2-java/
 * https://www.journaldev.com/7128/log4j2-example-tutorial-configuration-levels-appenders
 * https://examples.javacodegeeks.com/enterprise-java/log4j/log4j-2-best-practices-example/
 * http://www.baeldung.com/log4j2-appenders-layouts-filters
 * http://musigma.org/logging/2017/11/06/logging.html
 * https://memorynotfound.com/log4j2-with-log4j2-xml-configuration-example/
 * https://stackoverflow.com/questions/47656300/use-log4j2-for-multiple-classes
 * http://www.codepreference.com/2017/02/how-to-fix-log4j2-problem-with-maven-shade-plugin.html
 * https://stackoverflow.com/questions/23434252/programmatically-change-log-level-in-log4j2
 * https://garygregory.wordpress.com/2016/01/11/changing-log-levels-in-log4j2/
 * http://www.logicbig.com/tutorials/misc/java-logging/slf4j-with-log4j2/
 * https://stackoverflow.com/questions/41498021/is-it-worth-to-use-slf4j-with-log4j2
 * https://github.com/matthiaswelz/journeyofcode/tree/master/log4j2args4j
 * 
 * Markdown references
 * https://daringfireball.net/projects/markdown/syntax
 * 
 * The purpose of this application is to provide an example for the following:
 * 
 * - Demonstrates the use of development tools : GIT, MAVEN, Eclipse
 * - Demonstrates how to use Eclipse
 * - Provides a refresher of OOP in Java
 * - Provide an introduction to project file structure layout - MAVEN Archetype
 * - Query a SQLite database User table and displays the users
 * - Provide an introduction to collections
 * - Provide an introduction to the command line interface and show that there are many solution options to just this simple feature
 * - Markdown syntax for readme's
 * - Provide an introduction to logging
 * 
 * 	TODO: The ListArray<User> is populated but not really used ( just a prep for later examples)
 *  TODO: The Role class is not used ( just a prep for later examples)
 *  TODO: Refactor the command line parameters into a method
 *  TODO: Add JSON output for logging see: https://stackify.com/log4j2-java/
 *  TODO: JUnit tests
 * 	
 *****************************************************************/

public class App
{ 

	public static void main(String args[])
	{
			
			// To view the arguments being entered
			
			if (args.length == 0)
	        {
	            System.out.println("There were no commandline arguments passed!");
	        }
			else
			{
				// display the command line  entered 
				for(int i = 0; i < args.length; i++) 
				{
		            System.out.println(args[i]);
		            
		        }
			}
			
			
		// A library for parsing the commandline that makes it easy to look for
		// the -d option and get the database location/name passed in at the command line
		
		try
		{	
			final OptionParser optionParser = new OptionParser();
			
			//define the allowed arguments
			optionParser.acceptsAll(Arrays.asList("d", "database"), "Path and name of database file.")
						.withRequiredArg()
						.ofType(String.class)
						.describedAs("SQlite database");
			optionParser.acceptsAll(Arrays.asList("v", "verbose"), "Set logging level to INFO to see all levels of log messages").forHelp();			
			optionParser.acceptsAll(Arrays.asList("h", "help"), "Display help/usage information").forHelp();
			
			final OptionSet options = optionParser.parse(args);
			
			Integer exitStatus = null;
			
			if (options.has("help"))
			{
				System.out.println("This program takes an SQL database with a User table as displays the users.");
				System.out.println("It is provided as an example for teaching Java programming.");
		        exitStatus = 0;
			}
			else if (!options.has("database"))
			{
				System.err.println("Option \"-d database\" is required");
	            exitStatus = 1;
			}
			else if (!new File((String)options.valueOf("database")).isFile())
			{
		    	 	System.out.println("ERROR: Database file does not exist : " + (String)options.valueOf("database"));
		    	 	System.out.println("If the file is in the same directory as the JAR then the location would be: databaseFileName.Extention");
		    	 	System.out.println("for windows the database file location would be: C://folder/folder/databaseFileName.Extention");
		    	 	System.out.println("for MAC the database file location would be: /Volumes/VolumeName/folder/folder/databaseFileName.Extention");
		    	 	exitStatus = 1;
			}
			
		   // if an error encountered
		   if(exitStatus != null) {
	            if(exitStatus == 0)
	            {
					printUsage(optionParser);
	            		System.exit(0);
	            }
	            else
	            {
	            		printUsage(optionParser);
	            	    System.exit(1);
	            }
	        }
	
		   // valid input so start the program with the name of the database file to use
		   if (options.has("database") && options.has("verbose") )
		   {
			   String dbFile = "jdbc:sqlite:" + (String)options.valueOf("database");
			   Level logLevel = Level.DEBUG;
			   System.out.println("RUN WITH: Database: " + dbFile + " logging level requested: " + logLevel);
			   App anApp = new App( dbFile, logLevel);
		   }
		   else
		   {
			   String dbFile = "jdbc:sqlite:" + (String)options.valueOf("database");
			   System.out.println("RUN WITH: Database: " + dbFile + " logging as per main/resources/Log4J2.xml");
			   App anApp = new App(dbFile);
		   }
		}
        catch (OptionException argsEx)
        {
        		System.out.println("ERROR: Arguments\\parameter is not valid. " + argsEx);
        }
			
	}
	
	// DATA
	//............................................................
	//define attributes
	
	// This is added to every class that needs to log with one change
	// The getLogger( ) part should contain the name of the class its in
	private static Logger LOG;
	
	private	Scanner someInput;
	private Date today;
	
	// for windows the database file location would be: "jdbc:sqlite:C://path/oreallyoreilly.db"
	// for MAC or Linux database file location would be: "jdbc:sqlite:/Volumes/VolumeName/path/02_UserOOExample/database/oreallyoreilly.db"
	// private String databaseFile = "jdbc:sqlite:/Volumes/DataHD/Dropbox/_DEV2017/JAVA-BASIC/02_UserOOExample/database/oreallyoreilly.db";
	
	String databaseFile;
	
	// if in Eclipse User and Role classes appear as errors it means you need to do a Maven Update on the project
	// Right Click > Maven > Update Project as some times after a Maven clean 
	// eclipse looses track of the dependency of classes within the current package
	
	@SuppressWarnings("unused")
	private User user;
	private Role role;
	private ArrayList<User> userList;
	
	// CONSTRUCTORS
	//............................................................
		
	public App(String dbFile, Level logLevel)
	{		
		//associate logging with this class so know the messages that came from objects of this class
		LOG = LogManager.getLogger(App.class);
		Configurator.setLevel(LOG.getName(), logLevel);
		
		// Lookup an enviromental variable
		EnvironmentLookup lookup = new EnvironmentLookup();
		LOG.debug(lookup.lookup("JAVA_HOME"));

		// Check the log level requested
		LOG.info("Commandline requested log level:" + logLevel );		
		LOG.info("Application started with log level debug:" + LOG.isDebugEnabled());
		
		//database file to use
		this.databaseFile = dbFile;
		LOG.debug("Database file:" + dbFile);
		
		//log test
		//testLogOutput();
		
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
	
	public App(String dbFile)
	{
		this( dbFile, Level.INFO );
	}
	

	// METHODS
	//............................................................
	
	/**
	 * write out the users in a users table for the database specified
	 * 
	 */
	private void showListOfUsers()
	{

		this.today = new Date();
		
		// System.out.println( "Getting list of Users from Database as of " + today );
		LOG.debug("Getting list of Users from Database as of " + today);
		
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
        		// or see message: No suitable driver found for jdbc:sqlite:/xx.db
        		// No sutible driver also means URL into connection is wrong e.g you are missing jdbc:sqlite: in front of file name
            // System.err.println(e.getMessage());
            LOG.error(e.getMessage());
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
              //System.out.println(user);
              LOG.debug( "User object : " + user);
          }
        	  
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          // System.err.println(e.getMessage());
          LOG.error(e.getMessage());
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
            // System.err.println(e);
            LOG.error(e.getMessage());
          }
        }
		
	}//EOM
	
	/**
	 * Write help message to standard output using
	 * the provided instance of {@code OptionParser}.
	 */
	 private static void printUsage(final OptionParser parser)
	 {
	      try
	      {
	         parser.printHelpOn(System.out);  
	      }
	      catch (IOException ioEx)
	      {
	         // System.out.println("ERROR: Unable to print usage - " + ioEx);
	         LOG.error("ERROR: Unable to print usage - " + ioEx);
	      }
	 }//EOM
	 
	/**
	 * Test the Log4J2 logging
	 */
	 private static void testLogOutput()
	 {
		LOG.debug("Log test: Test printed on debug");
        LOG.info("Log test: Test printed on info");
        LOG.warn("Log test: Test printed on warn");
        LOG.error("Log test: Test printed on error");
        LOG.fatal("Log test: Test printed on fatal");

        LOG.info("Appending string: {}.", "Application log test message - Hi");
		 
	 }//EOM
	
}//EOC
