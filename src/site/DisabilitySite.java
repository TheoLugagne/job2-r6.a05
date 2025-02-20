package site;

import obj.Dollars;
import obj.Zone;
import java.time.LocalDate;

public class DisabilitySite extends Site{


	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private static final int CAP = 200;
	
	public DisabilitySite (Zone zone) {
		_zone = zone;
	}


	@Override
	protected Dollars charge(int fullUsage, LocalDate start, LocalDate end) {
		Dollars result;
		double summerFraction;
		int usage = Math.min(fullUsage, CAP);
		if (start.isAfter(_zone.summerEnd()) || end.isBefore(_zone.summerStart()))
			summerFraction = 0;
		else if (start.isAfter(_zone.summerStart()) && end.isBefore(_zone.summerEnd()))
			summerFraction = 1;
		else {
			double summerDays;
			if (start.isBefore(_zone.summerStart())) { //|| start.isAfter(_zone.summerEnd())) {
				// end is in the summer
				summerDays = end.getDayOfYear() - _zone.summerStart().getDayOfYear() + 1;
			} else {
				// start is in summer
				summerDays = _zone.summerEnd().getDayOfYear() - start.getDayOfYear() + 1;
			};
			summerFraction = summerDays / (end.getDayOfYear() - start.getDayOfYear() + 1);
		};
		result = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars(Math.max(fullUsage - usage, 0) * 0.062));
		result = result.plus(new Dollars(result.times(TAX_RATE)));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars(result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP)));
		return result;
	}
	



}
