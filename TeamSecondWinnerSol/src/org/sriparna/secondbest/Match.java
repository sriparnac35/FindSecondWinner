package org.sriparna.secondbest;

/**
 * A  class which is responsible to get a match played between the teams specified
 * 
 * @author SriparnaChakraborty
 * */


public class Match {	
	
	/*
	 * The teams among which the match was played
	 * */
	
	protected int mTeam1 = -1;
	protected int mTeam2 = -1;
	
	/*
	 * Stores the winner of the last played match
	 * */
	
	protected int mWinner = -1;
	
	private int mTypeOfMatchBeingPlayed = -1;
	
	/*
	 * variable to determine if a match has already been played.
	 * */
	private boolean mHasPlayed = false;
	
	private static final String ERROR_STRING_PLAY_NOT_CALLED = "Please call play before a call to getWinner";
	
	/*
	 * This method gets a match played between teams team1 and team2.
	 * The match to be played is specified by matchType.
	 * Currently it has an dummy implementation.
	 *  
	 *  @param team1 the first team to play
	 *  @param team2 the second team to play
	 *  @param matchType the type of match to play
	 *  
	 *  @return nothing
	 *  
	 * */
	
	public void play(int team1, int team2, int matchType){
		
		mTeam1 = team1;
		mTeam2 = team2;
		mTypeOfMatchBeingPlayed = matchType;
		
		/*
		 * This updates the state of the object specifying that a match has 
		 * been played. This is to ensure that a call to @link getWinner
		 * occurs only after a call to it.
		 * 
		 * */
		
		mHasPlayed = true;
		
		/**
		 * Stores the winner of the current match. Currently this has a dummy implementation
		 * which gives the winner based on a random number.
		 * If the random number is even, team1 is the winner else 
		 * team2 is the winner.
		 * 
		 * This should be replaced by an actual implementation
		 * **/
		
		double randomNumber = Math.random();
		mWinner = (randomNumber % 2 == 0)? team1 : team2;		
	}	
	
	
	/*
	 * This method returns the winner amongst the two teams after 
	 * a match is played. Should be called only after a call to 
	 * @link play.
	 * 
	 * @return int the winner after the match is played. 
	 * @throws IllegalStateException in case this method is called
	 *         before a match is actually played.
	 * 
	 * */	
	
	public int getWinner() throws IllegalStateException{
		/*
		 * A dummy code which gives the winner based on a random number.
		 * If the random number is even, team1 is the winner else 
		 * team2 is the winner.
		 * 
		 * This should be replaced by an actual implementation
		 * **/
		
		if(!mHasPlayed){
			throw new IllegalStateException(ERROR_STRING_PLAY_NOT_CALLED);
		}
		return mWinner;		
	}
	
	/**
	 * This is equivalent to a factory reset being performed on the object
	 * so that any state is flushed and the same object can be reused again.
	 * 
	 * Generally calls to an object of this class should be of the form :
	 * @link play -> @link getWinner -> @link recycle
	 * 
	 * 
	 * @param nothing
	 * @return nothing
	 * */
	
	public void recycle(){
		mTeam1 = -1;
		mTeam2 = -1;
		mWinner = -1;
		mTypeOfMatchBeingPlayed = -1;
		mHasPlayed = false;
	}

}
