import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.BusinessSite;
import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {

	BusinessSite _subject;
	DateTimeFormatter formatter;
	
	
	@BeforeEach
	public void setUp() {
		formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

		new Zone("A", 0.06, 0.07,LocalDate.parse("15 May 2023", formatter), LocalDate.parse("10 Sep 2023", formatter)).register();
		new Zone("B", 0.07, 0.06, LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		new Zone("C", 0.065, 0.065,LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		_subject = new BusinessSite();
	}

	
	@Test
	public void testZero() {

		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023",formatter)));
		_subject.addReading(new Reading(10, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(0d, _subject.charge().amount());
	}
	
	
	@Test
	public void testChargeLessThan50Dollars() {
		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading(710, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(50.57d, _subject.charge().amount());
	}
	
	
	@Test
	public void testChargeBetween50And75Dollars() {
		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading(810, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(57.75d, _subject.charge().amount());
	}
	
	
	@Test
	public void testChargeGreaterThan75Dollars() {
		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading(1210, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(86.3d, _subject.charge().amount());
	}
	
	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (NullPointerException ignored) {}
	}
}
