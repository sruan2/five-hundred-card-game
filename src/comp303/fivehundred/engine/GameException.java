package comp303.fivehundred.engine;

/**
 * @exception Thrown when illegal action is attempted
 */
@SuppressWarnings("serial")
public class GameException extends RuntimeException
{
	/**
	 * @param pMessage Message
	 * @param pException Exception
	 */
	public GameException(String pMessage, Throwable pException)
	{
		super(pMessage, pException);
	}

	/**
	 * @param pMessage Message
	 */
	public GameException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * @param pException Exception
	 */
	public GameException(Throwable pException)
	{
		super(pException);
	}

}