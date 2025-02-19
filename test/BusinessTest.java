import java.util.Date;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.BusinessSite;
import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {

	BusinessSite _subject;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		new Zone("A", 0.06, 0.07, new Date ("15 May 2023"), new Date ("10 Sep 2023")).register();
		new Zone("B", 0.07, 0.06, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		new Zone("C", 0.065, 0.065, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		_subject = new BusinessSite();
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
	public void testChargeLessThan50Dollars() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading (710, new Date ("1 Feb 2023")));
		assertEquals(50.57d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testChargeBetween50And75Dollars() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading (810, new Date ("1 Feb 2023")));
		assertEquals(57.75d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testChargeGreaterThan75Dollars() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading (1210, new Date ("1 Feb 2023")));
		assertEquals(86.3d, _subject.charge().amount());
	}
	
	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (NullPointerException e) {}
	}
}
