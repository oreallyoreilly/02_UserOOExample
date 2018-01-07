package com.oreallyoreilly.UserOOExample;

import java.util.Date;
import java.util.Scanner;

/*****************************************************************
 *
 *	Date: 2017
 *	@author COR
 *  
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
	private	Scanner someInput;
	private Date today;
	
	// CONSTRUCTORS
	//............................................................
	public App()
	{
		//initialise variables
		
        //create objects 
		this.someInput = new Scanner(System.in);
		
		//Starting method
        sayHi();		
		
		//pause before exit (this is only useful if an error occurs)
        System.out.println(" \n Press enter to exit the program");
		this.someInput.nextLine();

		//close the program without error
		System.exit(0);
	}
	
	// METHODS
	//............................................................
	
	private void sayHi()
	{

		this.today = new Date();
		System.out.println( "App says Hi at " + today );
		
	}//EOM
	
}//EOC
