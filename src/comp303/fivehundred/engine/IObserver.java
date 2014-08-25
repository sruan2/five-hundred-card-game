package comp303.fivehundred.engine;

import comp303.fivehundred.engine.GameEngine.Message;

/**
 * @author Ioannis Fytilis 260482744
 * Defines an observer, which receives updates.
 */
public interface IObserver
{

	/**
	 * When called with a message, the observer acts as specified.
	 * @param pMessage
	 * 		What the observer needs to do
	 */
	void update(Message pMessage);

}
