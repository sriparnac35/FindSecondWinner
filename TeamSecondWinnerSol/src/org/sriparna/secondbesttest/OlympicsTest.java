package org.sriparna.secondbesttest;



import org.junit.Assert;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sriparna.secondbest.Match;
import org.sriparna.secondbest.MatchFactory;
import org.sriparna.secondbest.Olympics;

/**
 * Class to test Olympics
 * 
 * @author SriparnaChakraborty
 * 
 * */


public class OlympicsTest{
	
	private int winner = -1;	
		
	/**
	 * Method to verify the sequence of calls to the @link Match object
	 * 
	 * @param nothing
	 * @return nothing
	 * 
	 * */
	
	@Test
	public void testOlympicsOrderWithValidInput(){
		Match match = Mockito.mock(Match.class);
		Olympics olympics = new Olympics(match);
		olympics.play(Inputs.validInput, MatchFactory.SPORT_TYPE_ARCHERY);
		
		InOrder inorder = Mockito.inOrder(match);
		
		inorder.verify(match).play(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		inorder.verify(match).getWinner();
		inorder.verify(match).recycle();
	}
	
	/**
	 * Method to verify behavior of Olympics class with null input.
	 * It is expected that it will return a result with error code 
	 * set to @link RESULT_INVALID_DATA_PROVIDED
	 * And no calls will be made to @link Match object
	 * 
	 * @param nothing
	 * @return nothing
	 * 
	 * */
	
	
	@Test
	public void testOlympicsWithNullInput(){
		Match match = Mockito.mock(Match.class);
		Olympics olympics = new Olympics(match);
		Olympics.Result result =  olympics.play(null, MatchFactory.SPORT_TYPE_ARCHERY);
		
		Assert.assertEquals(result.mErrorCode, Olympics.Result.RESULT_INVALID_DATA_PROVIDED);
		
		Mockito.verify(match, Mockito.times(0)).play(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.verify(match, Mockito.times(0)).getWinner();
		Mockito.verify(match, Mockito.times(0)).recycle();
	}
	
	
	/**
	 * Method to verify behavior of Olympics class with null input.
	 * It is expected that it will return a result with error code 
	 * set to @link RESULT_INADEQUATE_DATA_PROVIDED
	 * And no calls will be made to @link Match object
	 * 
	 * @param nothing
	 * @return nothing
	 * 
	 * */
	
	
	@Test
	public void testOlympicsWithOneInput(){
		Match match = Mockito.mock(Match.class);
		Olympics olympics = new Olympics(match);
		Olympics.Result result =  olympics.play(new int[]{4}, MatchFactory.SPORT_TYPE_ARCHERY);
		
		Assert.assertEquals(result.mErrorCode, Olympics.Result.RESULT_INADEQUATE_DATA_PROVIDED);
		
		Mockito.verify(match, Mockito.times(0)).play(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.verify(match, Mockito.times(0)).getWinner();
		Mockito.verify(match, Mockito.times(0)).recycle();
	}
	
	/**
	 * Method to verify the behaviour of Olympics with an even list of teams.
	 * Here we mock the @link Match object to always return the first 
	 * team of the two teams provided as input, as the winner and checks 
	 * the error code in the returned object is @link RESULT_OK
	 * and the first and second winners are as per requirement
	 * 
	 * @param nothing
	 * @return nothing
	 * 
	 * **/
	
	
	@Test
	public void testOlympicsResultsWithValidInputEven(){
		Match match = Mockito.mock(Match.class);		
			
		
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments = invocation.getArguments();
				winner = (int)arguments[0];
				return null;
			}
		}).when(match).play(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());	
		
		
		Mockito.when(match.getWinner()).thenReturn(winner);
		
		Olympics olympics = new Olympics(match);
		Olympics.Result result = olympics.play(Inputs.validInputEven1, MatchFactory.SPORT_TYPE_ARCHERY);
			
		
		
		Assert.assertEquals(result.mErrorCode, Olympics.Result.RESULT_OK);
		Assert.assertEquals(result.mResult.mSecondWinner, Inputs.validInputEven1Second);
		Assert.assertEquals(result.mResult.mFirstWinner, Inputs.validInputEven1First);
	}
	
	
	/**
	 * Method to verify the behaviour of Olympics with an odd list of teams.
	 * Here we mock the @link Match object to always return the first 
	 * team of the two teams provided as input, as the winner and checks 
	 * the error code in the returned object is @link RESULT_OK
	 * and the first and second winners are as per requirement
	 * 
	 * @param nothing
	 * @return nothing
	 * 
	 * **/
	
	
	@Test
	public void testOlympicsResultsWithValidInputOdd(){
		Match match = Mockito.mock(Match.class);			
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				winner = (int)invocation.getArguments()[0];
				return null;
			}
		}).when(match).play(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		Mockito.when(match.getWinner()).thenReturn(winner);
		
		Olympics olympics = new Olympics(match);
		Olympics.Result result = olympics.play(Inputs.validInputOdd1, MatchFactory.SPORT_TYPE_ARCHERY);
		
		Assert.assertEquals(result.mErrorCode, Olympics.Result.RESULT_OK);
		Assert.assertEquals(result.mResult.mSecondWinner, Inputs.validInputOdd1Second);
		Assert.assertEquals(result.mResult.mFirstWinner, Inputs.validInputOdd1First);
	}
}
