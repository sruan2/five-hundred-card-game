package comp303.fivehundred.util;

/**
 * @author Stephanie Pataracchia 260407002
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import comp303.fivehundred.util.TestComparators;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestBySuitComparator.class,
	TestByRankComparator.class,
	TestBySuitNoTrumpComparator.class
	})

public class TestComparators
{

}
