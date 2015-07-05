package org.sriparna.secondbest;

import java.util.Scanner;

import org.sriparna.secondbest.Olympics.Result;

/**
 * Main class to run the application
 * */

public class Main {

	/**
	 * main method. This takes the list of the teams which should play
	 * and the type of sport to play from the user as input 
	 * and outputs the second best team
	 * 
	 * @param args unused
	 * */
	
	public static void main(String[] args){
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter the number type of sport which should be one of  : ");
		for(int sport : MatchFactory.LIST_OF_SUPPORTED_SPORTS){
			String nameOfSport = MatchFactory.getReadableNameForSport(sport);
			System.out.println(sport + ".  "+nameOfSport);
		}
		
		System.out.println();
		
		/**
		 * Get the type of sport
		 * */
		
		int sportType = scanner.nextInt();
		
		System.out.println("Enter the number of participating teams :");
		int numOfTeams = scanner.nextInt();
		
		int[] participatingTeams = new int[numOfTeams]; 
		
		for(int i = 0; i < numOfTeams; i++){
			participatingTeams[i] = scanner.nextInt();
		}
		
		Olympics.Result result = null;
		
		Olympics olympics = new Olympics();
		try{
			result = olympics.play(participatingTeams, sportType);			
		}
		catch(IllegalArgumentException e){
			System.out.println("please specify at least 1 team ");
		}
		
		switch(result.mErrorCode){
		case Result.RESULT_OK:
			int secondWinner = result.mResult.mSecondWinner;
			System.out.println("The second winner is team : "+secondWinner);
			break;
			
			
		case Result.RESULT_INADEQUATE_DATA_PROVIDED:
			System.out.println(result.toString());
			break;
			
		case Result.RESULT_INVALID_DATA_PROVIDED:
			System.out.println(result.toString());
			break;
		}		
		
	}
}
