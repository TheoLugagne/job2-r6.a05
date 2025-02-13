package site;

import obj.Dollars;
import obj.Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BusinessSiteTest {
    private BusinessSite _subject;
    @BeforeEach
    public void setUp() {
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
    public void test100() {
        _subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (110, new Date ("1 Feb 2023")));
        assertEquals(7.27d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test99() {
        _subject.addReading(new Reading (100, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (199, new Date ("1 Feb 2023")));
        assertEquals(7.19d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test101() {
        _subject.addReading(new Reading (1000, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (1101, new Date ("1 Feb 2023")));
        assertEquals(7.34d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test199() {
        _subject.addReading(new Reading (10000, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (10199, new Date ("1 Feb 2023")));
        assertEquals(14.4d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test200() {
        _subject.addReading(new Reading (0, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (200, new Date ("1 Feb 2023")));
        assertEquals(14.48d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test201() {
        _subject.addReading(new Reading (50, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (251, new Date ("1 Feb 2023")));
        assertEquals(14.55d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMax() {
        _subject.addReading(new Reading (0, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (Integer.MAX_VALUE, new Date ("1 Feb 2023")));
        assertEquals (1.5220290473E8, _subject.charge().amount());
    }

    @Test
    public void testNoReadings() {
        try {
            _subject.charge();
            assert(false);
        } catch (NullPointerException e) {}
    }
}