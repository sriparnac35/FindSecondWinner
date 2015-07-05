package org.sriparna.secondbest;

/**
 * Class to store constant strings e.g. error messages used by application.
 * This will serve as a central location to access or modify these strings
 * 
 * @author SriparnaChakraborty
 * 
 * */

public class Constants {
	/**
	 * Constant to be used while throwing exception in case an unsupported sport type is passed as argument
	 * */	
	
	public static final String ERROR_MESSAGE_UNSUPPORTED_SPORT = "This sport is not currently supported";
	
	/**
	 * Constant to be used describing the error in case no teams have been specified for playing
	 * */
	
	public static final String RESULT_INVALID_DESC = "The data provided is invalid. This could be because"
			+ "you have provided no data at all";
	
	
	/**
	 * Constant to be used describing the error in case inadequate data is provided for playing.
	 * This could be the case when only one team is provided as input, in which case, 
	 * winner is the single team provided as input and there is no team in the second position
	 * Here only the value of the first winner should be used.
	 * */
	
	public static final String RESULT_INADEQUATE_DESC = "The data provided is inadequate."
			+ "This could be because you have provided only one time. In this case, "
			+ "there can be no second best team";
	
	/**
	 * Constant to be used specifying that a correct result specifying the first winner and second winner has
	 * been provided.
	 * */
	
	public static final String RESULT_OK_DESC = "The data provided is correct "
			+ "and the correct result is provided";
}
