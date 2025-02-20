import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.DisabilitySite;

import static org.junit.jupiter.api.Assertions.*;

class DisabillityTest {

	DisabilitySite _subject;
	DateTimeFormatter formatter;
	
	@BeforeEach
	public void setUp() {
		formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

		new Zone("A", 0.06, 0.07, LocalDate.parse("15 May 2023", formatter), LocalDate.parse("10 Sep 2023", formatter)).register();
		new Zone("B", 0.07, 0.06, LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		new Zone("C", 0.065, 0.065,LocalDate.parse("5 Jun 2023",formatter), LocalDate.parse ("31 Aug 2023",formatter)).register();
		_subject = new DisabilitySite(Zone.get("A"));
	}
	
	
	@Test
	public void testZero() {
		_subject.addReading(new Reading(10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (10, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(0d, _subject.charge().amount());
	}
	
	
	@Test
	public void testSummerFractionIsEqualsTo1() {
		_subject.addReading(new Reading (10, LocalDate.parse("16 May 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("9 Sep 2023", formatter)));
		assertEquals(8.14d, _subject.charge().amount());
	}
	
	
	@DisplayName("Présence d'un potentiel bug, ce cas est non traité par le code")
	@Test
	public void testSummerFractionIsEqualsTo1withPartOfWinter() {
		_subject.addReading(new Reading (10, LocalDate.parse("1 May 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("30 Sep 2023", formatter)));
		assertEquals(8.23d, _subject.charge().amount());
	}
	
	
	@Test
	public void testSummerFractionIsEqualsTo0BeforeTheSummer() {
		_subject.addReading(new Reading (10, LocalDate.parse("1 Jan 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("1 Feb 2023", formatter)));
		assertEquals(9.19d, _subject.charge().amount());
	}
	
	
	@Test
	public void testSummerFractionIsEqualsTo0AfterTheSummer() {
		_subject.addReading(new Reading (10, LocalDate.parse("1 Oct 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("1 Nov 2023", formatter)));
		assertEquals(9.19d, _subject.charge().amount());
	}
	
	
	@Test
	public void testSummerFractionEndIsInTheSummer() {
		_subject.addReading(new Reading (10, LocalDate.parse("1 May 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("5 Sep 2023", formatter)));
		assertEquals(8.25d, _subject.charge().amount());
	}
	
	
	@Test
	public void testSummerFractionStartIsInTheSummer() {
		_subject.addReading(new Reading (10, LocalDate.parse("16 May 2023", formatter)));
		_subject.addReading(new Reading (110, LocalDate.parse("7 Oct 2023", formatter)));
		assertEquals(8.34d, _subject.charge().amount());
	}
	
	
	@Test
	@DisplayName("Doit être inférieur que our les résidents, application de CAP à 200")
	public void testSummerFractionStartIsInTheSummerWithUsageMoreThan200() {
		_subject.addReading(new Reading (10, LocalDate.parse("16 May 2023", formatter)));
		_subject.addReading(new Reading (310, LocalDate.parse("7 Oct 2023", formatter)));
		assertEquals(24.86d, _subject.charge().amount());
	}

	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (ArrayIndexOutOfBoundsException ignored) {}
	}
}
