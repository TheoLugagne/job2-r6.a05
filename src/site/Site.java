package site;

import obj.Dollars;
import obj.Reading;
import obj.Zone;

import java.time.LocalDate;

public abstract class Site {

    protected Reading[] _readings = new Reading[1000];
    protected Zone _zone;

    public void addReading(Reading newReading) {
        // add reading to end of array
        _readings[firstUnsuedReadingIndex()] = newReading;
    }

    public int firstUnsuedReadingIndex(){
        int i = 0;
        while (_readings[i] != null) i++;
        return i;
    }

    private Reading lastReading() {
        return _readings[firstUnsuedReadingIndex()-1];
    }

    private Reading previousReading() {
        return _readings[firstUnsuedReadingIndex()-2];
    }

    private int lastUsage() {
        return lastReading().amount() - previousReading().amount();
    }

    private LocalDate nextDay(LocalDate date) {
        return date.plusDays(1);
    }

    public Dollars charge() {
        LocalDate end = lastReading().date();
        LocalDate start = nextDay(previousReading().date());
        return charge(lastUsage(), start, end);
    }

    protected abstract Dollars charge(int fullUsage, LocalDate start, LocalDate end);
}
