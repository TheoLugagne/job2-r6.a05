import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Locale;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.LifelineSite;
import static org.junit.Assert.*;


class LifelineTest { 
	
	LifelineSite _subject;
	DateTimeFormatter formatter;
	
	@BeforeEach
	public void setUp() {
		formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

		new Zone("A", 0.06, 0.07,LocalDate.parse("15 May 2023", formatter), LocalDate.parse("10 Sep 2023", formatter)).register();
		new Zone("B", 0.07, 0.06, LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		new Zone("C", 0.065, 0.065,LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		_subject = new LifelineSite();
	}

	
	@Test
	public void testZero() {
		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (10, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(0d, _subject.charge().amount(), 0.01d);
	}
	
	
	@Test
	public void test100() {
		_subject.addReading(new Reading (10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(4.84d, _subject.charge().amount(), 0.01d);
	}
	
	
	@Test
	public void test99() {
		_subject.addReading(new Reading (100, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (199, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(4.79d, _subject.charge().amount(), 0.01d);
	}
	
	
	@Test
	public void test101() {
		_subject.addReading(new Reading (1000, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (1101, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(4.91d, _subject.charge().amount(), 0.01d);
	}
	
	
	@Test
	public void test199() {
		_subject.addReading(new Reading (10000, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (10199, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(11.6d, _subject.charge().amount(),0.01d);
	}
	
	
	@Test
	public void test200() {
		_subject.addReading(new Reading (0, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (200, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(11.68d, _subject.charge().amount(),0.01d);
	}
	
	
	@Test
	public void test201() {
		_subject.addReading(new Reading (50, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (251, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(11.77d, _subject.charge().amount(),0.01d);
	}
	
	
	@Test
	public void testMax() {
		_subject.addReading(new Reading (0, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (Integer.MAX_VALUE, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals (1.9730005336E8, _subject.charge().amount(), 0.01d);
	}
	
	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (NullPointerException ignored) {}
	}

}
