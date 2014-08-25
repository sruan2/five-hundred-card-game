package comp303.fivehundred.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author Ioannis Fytilis 260482744
 * The main GUI window.
 */
@SuppressWarnings("serial")
public class FHGUI extends JFrame
{

	private static final int FRAME_X = 1200;
	private static final int FRAME_Y = 720;
	
	/**
	 * Start the GUI.
	 */
	public FHGUI()
	{	
		
		// Initialize the engine
		GameRunner.createEngine();
		
		// Adjust our frame
		setSize(FRAME_X, FRAME_Y);
		setTitle("Play 500 with me!");
		setPreferredSize(new Dimension(FRAME_X, FRAME_Y));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Add each major component
		setJMenuBar(new TopMenu());
		add(new ScoringPanel(), BorderLayout.EAST);
		add(new BoardPanel(), BorderLayout.CENTER);

		// Show everything
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Runs the GUI.
	 * @param pArgs
	 * 	Command-line arguments
	 */
	public static void main(String[] pArgs)
	{
		new FHGUI();
	}
	
	
}
