package site;

import obj.Reading;
import obj.Zone;

public class Site {

    protected Reading[] _readings = new Reading[1000];
    protected Zone _zone;

    public void addReading(Reading newReading) {
        // add reading to end of array
        int i = 0;
        while (_readings[i] != null) i++;
        _readings[i] = newReading;
    }

    public int firstUnsuedReadingIndex(){
        return _readings.
    }
}
