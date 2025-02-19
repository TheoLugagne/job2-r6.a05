import java.util.Date;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.ResidentialSite;
import static org.junit.jupiter.api.Assertions.*;

class ResidentialTest {

	ResidentialSite _subject;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		new Zone("A", 0.06, 0.07, new Date ("15 May 2023"), new Date ("10 Sep 2023")).register();
		new Zone ("B", 0.07, 0.06, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		new Zone ("C", 0.065, 0.065, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		_subject = new ResidentialSite(Zone.get("A"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testZero() {
		_subject.addReading(new Reading(10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading (10, new Date ("1 Feb 2023")));
		assertEquals(0d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionIsEqualsTo1() {
		_subject.addReading(new Reading (10, new Date ("16 May 2023")));
		_subject.addReading(new Reading (110, new Date ("9 Sep 2023")));
		assertEquals(8.14d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@DisplayName("Présence d'un potentiel bug, ce cas est non traité par le code")
	@Test
	public void testSummerFractionIsEqualsTo1withPartOfWinter() {
		_subject.addReading(new Reading (10, new Date ("1 May 2023")));
		_subject.addReading(new Reading (110, new Date ("30 Sep 2023")));
		assertEquals(8.23d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionIsEqualsTo0BeforeTheSummer() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading (110, new Date ("1 Feb 2023")));
		assertEquals(9.19d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionIsEqualsTo0AfterTheSummer() {
		_subject.addReading(new Reading (10, new Date ("1 Oct 2023")));
		_subject.addReading(new Reading (110, new Date ("1 Nov 2023")));
		assertEquals(9.19d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionEndIsInTheSummer() {
		_subject.addReading(new Reading (10, new Date ("1 May 2023")));
		_subject.addReading(new Reading (110, new Date ("5 Sep 2023")));
		assertEquals(8.25d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionStartIsInTheSummer() {
		_subject.addReading(new Reading (10, new Date ("16 May 2023")));
		_subject.addReading(new Reading (110, new Date ("7 Oct 2023")));
		assertEquals(8.34d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSummerFractionStartIsInTheSummerWithUsageMoreThan200() {
		_subject.addReading(new Reading (10, new Date ("16 May 2023")));
		_subject.addReading(new Reading (310, new Date ("7 Oct 2023")));
		assertEquals(25d, _subject.charge().amount());
	}

	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (ArrayIndexOutOfBoundsException e) {}
	}

}
