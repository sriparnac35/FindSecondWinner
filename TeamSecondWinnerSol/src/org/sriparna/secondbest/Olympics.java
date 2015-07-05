package org.sriparna.secondbest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which is responsible to get a set of teams to play a particular match
 * and provides apis to get the first winner and the second winner in a 
 * given series
 * 
 * @author SriparnaChakraborty
 * */


public class Olympics {
	
	
	/**
	 * We store a map of @link Match objects with keys as the type of match.
	 * This is to ensure that we reuse @link Match objects if subsequent requests
	 * come for the same match type so as to reduce the number of objects 
	 * required to be garbage collected resulting in more memory available
	 * to the application at any time
	 **/
	
	private Map<Integer, Match> mMatches = null;
	private Match mMatch = null;
	
	
	public Olympics(){
		mMatches = new HashMap<Integer, Match>();
	}	
	
	/**
	 * Specify the exact match object to be used for conducting matches.
	 * When set it is given  preference over @link mMatches.
	 * 
	 * Should be flushed (set to null ) when no longer required.
	 * 
	 * Mostly relevant for testing purposes, but can also be used for customizing matches
	 * by passing as argument a subclass of @link Match
	 * */
	
	public Olympics(Match match){
		mMatch = match;
	}
	
	/**
	 * method to get a series played amongst a set of teams
	 * 
	 * @param teams set of teams to play in the series
	 * @param typeOfSport the type of sport to play
	 * 
	 * @return Result The result of the match
	 * @throws IllegalArgumentException In case unsupported match type is provided
	 * */
	
	public Result play(int[] teams, int typeOfSport) throws IllegalArgumentException{
		if(MatchFactory.LIST_OF_SUPPORTED_SPORTS.get(typeOfSport -1) == null){
			throw new IllegalArgumentException(Constants.ERROR_MESSAGE_UNSUPPORTED_SPORT);
		}
		
		Result result = null;
		
		if((teams == null) || teams.length == 0){
			
			/**
			 * No teams are provided to play the match. In that case we provide the 
			 * error code @link RESULT_INVALID_DATA_PROVIDED in the result.
			 * */
			
			Result.Winners winners = new Result.Winners(Result.Winners.INVALID_VALUE, Result.Winners.INVALID_VALUE);
			result = new Result(Result.RESULT_INVALID_DATA_PROVIDED, winners);
			
		}
		
		else if(teams.length == 1){
			/**
			 * Only one team is provided. In that case, there is only one winner 
			 * and no second winner.
			 * */
			
			
			Result.Winners winners = new Result.Winners(teams[0],Result.Winners.INVALID_VALUE);			 
			result =  new Result(Result.RESULT_INADEQUATE_DATA_PROVIDED, winners);
		}
		else{
			
			/**
			 * There are 2 or more participating teams
			 * */
			
			int overallWinner = Result.Winners.INVALID_VALUE;
			int secondBestTeam = Result.Winners.INVALID_VALUE;
			
			/**
			 * Stores the teams which still have to play in a queue.
			 * */
			
			ArrayDeque<Integer> teamsToPlay = new ArrayDeque<Integer>();
			
			/**
			 * Map which stores the list of teams defeated by a given team.
			 * So, key = team id 
			 *     values = list of teams defeated by team provided in the key.
			 * */
			
			Map<Integer,List<Integer>> teamsDefeatedByGivenTeam = new HashMap<>();
						
			/**
			 * Stores if a winner has been declared
			 * */
			
			boolean winnerDeclared = false;
			
			/*
			 * Initialize the dequeue with the original list of teams
			 * */		
			
			for(int i = 0; i < teams.length; i++){
				teamsToPlay.add(new Integer(teams[i]));
			}	
			
			
			while(teamsToPlay.size() >= 0){
				/**
				 * Only one team is left in the list of teams to play. This should either be
				 * the winner or the second winner. 
				 * */
				if(teamsToPlay.size() == 1){
					/**
					 * If the winner has not yet been declared, this is definitely the winner
					 * */
					
					if(!winnerDeclared){
						winnerDeclared = true;
						/**
						 * We have decided that the remaining element in the queue is the winner.
						 * We assign variables accordingly. so, overallWinner now holds the id of 
						 * the winning team which is extracted from the queue by @link pollFirst
						 * */
						overallWinner = teamsToPlay.pollFirst();
						
						/**
						 * We get the list of all teams which have been defeated by the winning team
						 * These teams must now compete for the second position.
						 * So, we add all of them to the queue of teams to play.
						 * */
						
						List<Integer> teamsToCompeteForSecondPosition = teamsDefeatedByGivenTeam.get(overallWinner);
						teamsToPlay.addAll(teamsToCompeteForSecondPosition);
					}
					
					/**
					 * If the winner has already been declared, this is the second winner.
					 * */
					else{
						
						/**
						 * extract the team which we have identified as the second winner and assign appropriate 
						 * variable. We use @link pollFirst because we know that now that is the only item left in the 
						 * queue.
						 * 
						 * We exit from the loop now because there are no other teams left to play.
						 * */
						secondBestTeam = teamsToPlay.pollFirst();
						break;
					}		
				}
				
				/**
				 * Extract the first two teams which should play. We now know that the number 
				 * of teams remaining to play is atleast 2 because of the condition above.
				 * */
				
				int team1 = teamsToPlay.pollFirst();
				int team2 = teamsToPlay.pollFirst();				
				
				/**
				 * play the match and evaluate the winner and the loser of the match
				 * 
				 * */
				
				int winner = play(team1, team2, typeOfSport);
				int loser = (winner == team1)? team2: team1;
				
				/**
				 * Add the loser team to the list of teams defeated by the winner.
				 * In case this winner becomes the overall winner, these defeated teams 
				 * must compete against each other to claim the second position.
				 * 
				 * */
				
				List<Integer> teamsDefeatedByWinner = teamsDefeatedByGivenTeam.get(winner);
				
				if(teamsDefeatedByWinner == null){
					teamsDefeatedByWinner = new ArrayList<>();				
				}
				
				teamsDefeatedByWinner.add(loser);
				teamsDefeatedByGivenTeam.put(winner, teamsDefeatedByWinner);
				
				/**
				 * Add the winning team at the end of the queue. This is because it might have to 
				 * compete again. 
				 * Once the current sequence of teams have competed against each other,
				 * these winners must again compete against each other. These winners 
				 * will again be pushed in the queue so that they compete again.
				 * 
				 * This will continue till there will be one team standing which is then declared 
				 * the winner (or the second winner in case the defeated teams are pushed)
				 * 
				 * Since we push at the end of the queue, and the queue is polled for teams in 
				 * sequence from the beginning, we ensure that winners only compete with other 
				 * winners at the same level (or one element in the immediate previous level which
				 * has not played a match in that level because there were odd number of teams)
				 * */
				
				
				teamsToPlay.addLast(winner);
			}		
			Result.Winners winners = new Result.Winners(overallWinner, secondBestTeam);
			result = new Result(Result.RESULT_OK, winners);
		}
		return result;
		
	}
	
	/**
	 * method to get a match of a particular type to be played between two teams.
	 * 
	 * @param team1 the first team to play
	 * @param team2 the second team to play
	 * @param team3 the third team to play
	 * 
	 * @return int the winner of the match
	 * 
	 * */
	
	
	private int play(int team1, int team2, int typeOfSport){
		/**
		 * if there already exists a @link Match instance use it, else create a new one and 
		 * use it to play the match.
		 * 
		 * Check @link Match and @link MatchFactory for more details
		 * */
				
		Match match = mMatch;
		if(match == null){
			mMatch =  mMatches.get(typeOfSport);
		}				
				
		if(match == null){			
			match = MatchFactory.getMatchInstance(typeOfSport);
		}		
		match.play(team1, team2, typeOfSport);
		int winner = match.getWinner();
		match.recycle();
		
		return winner;
	}
	
	/**
	 * nested class holding the result of the current match series
	 * 
	 * @author SriparnaChakraborty
	 * */
	
	
	public static class Result{
		
		/**
		 * Constants specifying what to expect in the result object i.e. whether :
		 * 
		 *  1. No error has occurred and they contain completely correct data (RESULT_OK)
		 *  2. The result provided is unusable because invalid data is provided (RESULT_INVALID_DATA_PROVIDED);
		 *  3. Data may be inadequate, so one or more fields may not be usable (RESULT_INADEQUATE_DATA_PROVIDED).
		 *  	This is the case  when for e.g. only one team has been provided as input.
		 *  	So, first winner is the sole team provided
		 *  		There is no second winner. So the corresponding field is unusable
		 * */
		
		public static final int RESULT_INVALID_DATA_PROVIDED = -2;
		public static final int RESULT_INADEQUATE_DATA_PROVIDED = -3;
		public static final int RESULT_OK = 0;	
		
		/*
		 * Holds the error code. This is a final field since it holds a result value and does not 
		 * expect to be modified or recycled or rewritten after creation.
		 * 
		 * public field because : it is final, so cannot be modified and accessing it does not 
		 * lead to increase in function calls being made
		 * */
		
		public final int mErrorCode ;
		
		/*
		 * Holds the result. This is a final field since it holds a result value and does not 
		 * expect to be modified or recycled or rewritten after creation.
		 * 
		 * public field because : it is final, so cannot be modified and accessing it does not 
		 * lead to increase in function calls being made
		 * */
		
		
		public final Winners mResult;
		
		public Result(int errorCode, Winners result){
			mErrorCode = errorCode;
			mResult = result;
		}
		
		/**
		 * Overrides the super method to describe the contents of the object and winners if any
		 * */
		
		@Override
		public String toString(){
			String desc = "";
			
			switch(mErrorCode){
			case  RESULT_INVALID_DATA_PROVIDED:
				desc = Constants.RESULT_INVALID_DESC;
				break;
				
			case RESULT_INADEQUATE_DATA_PROVIDED:
				desc = Constants.RESULT_INADEQUATE_DESC;
				break;
				
			case RESULT_OK:
				desc = Constants.RESULT_OK_DESC;
				desc.concat("  winner = "+mResult.mFirstWinner + "  second winner = "+mResult.mSecondWinner);
				break;
				
			}			
			return desc;
		}
		
		
		
		/**
		 * Class containing the first and second winners of the match
		 * 
		 * @author SriparnaChakraborty
		 * */
		
		
		public static class Winners{
			
			/*
			 * Specifies an invalid value for a winner
			 * **/
			
			public static final int INVALID_VALUE = Integer.MAX_VALUE;
			
			
			/**
			 * First winner of the match. Field is final since it is not expected to change after creation, The same 
			 * object also should not be used to store another result
			 * 
			 * public since it cannot be modified (as it is final) and no of function calls can be decreased.
			 * */
			
			public final int mFirstWinner;
			
			/**
			 * Second winner of the match. Field is final since it is not expected to change after creation, The same 
			 * object also should not be used to store another result
			 * 
			 * public since it cannot be modified (as it is final) and no of function calls can be decreased.
			 * */
			
			public final int mSecondWinner;
			
			public Winners(int firstWinner, int secondWinner){
				mFirstWinner = firstWinner;
				mSecondWinner = secondWinner;
			}
		}
	}	

}
