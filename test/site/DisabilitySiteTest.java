package site;

import obj.Reading;
import obj.Zone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DisabilitySiteTest {

    DisabilitySite _subject;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp(){
        new Zone("A", 0.06, 0.07, new Date("15 May 2023"), new Date ("10 Sep 2023")).register();
        _subject = new DisabilitySite(Zone.get("A"));
    }

    @Test
    public void testZero() {
        _subject.addReading(new Reading(10, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (10, new Date ("1 Feb 2023")));
        assertEquals(0d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test100Winter() {
        _subject.addReading(new Reading (10, new Date ("1 Jan 2023")));
        _subject.addReading(new Reading (110, new Date ("1 Feb 2023")));
        assertEquals(9.19d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test100Summer() {
        _subject.addReading(new Reading (10, new Date ("1 Jul 2023")));
        _subject.addReading(new Reading (110, new Date ("1 Aug 2023")));
        assertEquals(8.14d, _subject.charge().amount());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test100MidSummerMidWinter() {
        _subject.addReading(new Reading (10, new Date ("1 Sept 2023")));
        _subject.addReading(new Reading (110, new Date ("1 Oct 2023")));
        assertEquals(8.88d, _subject.charge().amount());
    }
    @SuppressWarnings("deprecation")
    @Test
    public void test100MidWinterMidSummer() {
        _subject.addReading(new Reading (10, new Date ("1 May 2023")));
        _subject.addReading(new Reading (110, new Date ("1 Jun 2023")));
        assertEquals(8.58d, _subject.charge().amount());
    }

    @Test
    public void testCapSummer() {
        _subject.addReading(new Reading (10, new Date ("1 Jun 2023")));
        _subject.addReading(new Reading (310, new Date ("1 Jul 2023")));
        assertEquals(24.46d, _subject.charge().amount());
    }
}