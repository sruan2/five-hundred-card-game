package comp303.fivehundred.gui;

/**
 * @exception Thrown when illegal action is attempted
 */
@SuppressWarnings("serial")
public class GUIException extends RuntimeException
{
	/**
	 * @param pMessage Message
	 * @param pException Exception
	 */
	public GUIException(String pMessage, Throwable pException)
	{
		super(pMessage, pException);
	}

	/**
	 * @param pMessage Message
	 */
	public GUIException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * @param pException Exception
	 */
	public GUIException(Throwable pException)
	{
		super(pException);
	}

}