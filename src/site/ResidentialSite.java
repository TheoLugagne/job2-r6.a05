package site;

import obj.Dollars;
import obj.Reading;
import obj.Zone;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class ResidentialSite extends Site {


	private static final double TAX_RATE = 0.05;


	public ResidentialSite (Zone zone) {
		_zone = zone;
	}



	
	public Dollars charge() {
		// find last reading
		int i = 0;
		while (_readings[i] != null) i++;
		int usage = _readings[i-1].amount() - _readings[i-2].amount();
		LocalDate end = _readings[i-1].date();
		LocalDate start = _readings[i-2].date();
		//set to begining of period
		start = start.plusDays(1);
		return charge(usage, start, end);
	}

	private Dollars charge(int usage, LocalDate start, LocalDate end) {
		Dollars result;
		double summerFraction;

		// Find out how much of period is in the summer
		if (start.isAfter(_zone.summerEnd()) || end.isBefore(_zone.summerStart()))
			summerFraction = 0;
		else if (start.isAfter(_zone.summerStart()) && end.isBefore(_zone.summerEnd()))
			summerFraction = 1;
//		else if (!start.isBefore(_zone.summerStart()) && !start.isAfter(_zone.summerEnd()) &&
//				!end.isBefore(_zone.summerStart()) && !end.isAfter(_zone.summerEnd()))
//			summerFraction = 1;
		else { // part in summer part in winter
			double summerDays;
			if (start.isBefore(_zone.summerStart())) { // || start.isAfter(_zone.summerEnd())) {
				// end is in the summer
				summerDays = dayOfYear(end) - dayOfYear (_zone.summerStart()) + 1;
			} else {
				// start is in summer
				summerDays = dayOfYear(_zone.summerEnd()) - dayOfYear (start) + 1;
			};
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		};

		result = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars(result.times(TAX_RATE)));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars(result.plus(fuel.times(TAX_RATE)));
		return result;
	}

}
