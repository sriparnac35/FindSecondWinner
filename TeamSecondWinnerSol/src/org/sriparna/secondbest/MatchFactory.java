package org.sriparna.secondbest;

import java.util.ArrayList;
import java.util.List;

/**
 * A Factory class which returns the appropriate match object
 * depending on the type of match to be played.
 * 
 *  @author SriparnaChakraborty
 *  
 **/


public class MatchFactory {
	
	/*
	 * Constants for sports currently supported
	 * **/
	
	public static final int SPORT_TYPE_HOCKEY = 1;
	public static final int SPORT_TYPE_CRICKET = 2;
	public static final int SPORT_TYPE_FOOTBALL = 3;
	public static final int SPORT_TYPE_SWIMMING = 4;
	public static final int SPORT_TYPE_ARCHERY =  5;
	
	/*
	 * List of supported sports
	 * */
	
	public static final List<Integer> LIST_OF_SUPPORTED_SPORTS ;
	
	static{
		LIST_OF_SUPPORTED_SPORTS = new ArrayList<Integer>();
		LIST_OF_SUPPORTED_SPORTS.add(SPORT_TYPE_HOCKEY);
		LIST_OF_SUPPORTED_SPORTS.add(SPORT_TYPE_CRICKET);
		LIST_OF_SUPPORTED_SPORTS.add(SPORT_TYPE_FOOTBALL);
		LIST_OF_SUPPORTED_SPORTS.add(SPORT_TYPE_SWIMMING);
		LIST_OF_SUPPORTED_SPORTS.add(SPORT_TYPE_ARCHERY);
	}
	
	/**
	 * provides a human readable name for each supported sport or invalid if it is 
	 * an unsupported sport
	 * 
	 * @param sportId the id of the sport as specified by the constants above
	 * @return String a human readable name for the sport or "invalid" if an unsupported sport is 
	 * 			provided as input. Currently unsupported sport is any sport not present in the list
	 * 			@link LIST_OF_SUPPORTED_SPORTS
	 * 
	 */
	
	public static String getReadableNameForSport(int sportId){
		String name = "";
		
		switch(sportId){
		case SPORT_TYPE_HOCKEY:
			name = "hockey";
			break;
			
		case SPORT_TYPE_CRICKET:
			name = "cricket";
			break;
			
		case SPORT_TYPE_FOOTBALL:
			name = "football";
			break;
			
		case SPORT_TYPE_SWIMMING:
			name= "swimming";
			break;
			
		case SPORT_TYPE_ARCHERY:
			name = "archery";
			break;
			
		default:
			name = "invalid";
		}
		
		return name;
	}
	
	/*
	 *Returns the appropriate @link Match object (an object of type @link Match 
	 * or any of its subclasses) based on the type of match to be played.
	 * 
	 *  Currently it always returns a @link Match object
	 *  
	 *  TODO: create appropriate classes which extend @link Match
	 *  to handle different types of sports
	 *  
	 *  
	 *  @param type The type of match to be played
	 *  @return Match the @link Match object to handle the sport
	 **/
	
	public static Match getMatchInstance(int type){
		Match match = null;
		switch(type){
		case SPORT_TYPE_ARCHERY:
		case SPORT_TYPE_CRICKET:
		case SPORT_TYPE_FOOTBALL:
		case SPORT_TYPE_HOCKEY:
		case SPORT_TYPE_SWIMMING:
			default: 
				match = new Match();
		}
		
		return match;
	}

}
