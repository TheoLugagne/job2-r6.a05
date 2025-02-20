package obj;

import java.time.LocalDate;
import java.util.HashMap;

public class Zone {

	private String _name;
	private LocalDate _summerEnd;
	private LocalDate _summerStart;
	private double _winterRate;
	private double _summerRate;

	private static HashMap<String, Zone> Registry = new HashMap<String, Zone>();

	public Zone register() {
		Registry.put(_name, this);
		return this;
	}

	public static Zone get (String name) {
		return Registry.get(name);
	}

	public Zone (String name, double summerRate, double winterRate,
				 LocalDate summerStart, LocalDate summerEnd) {
		_name = name;
		_summerRate = summerRate;
		_winterRate = winterRate;
		_summerStart = summerStart;
		_summerEnd = summerEnd;
	}

	public LocalDate summerEnd() {
		return _summerEnd;
	}

	public LocalDate summerStart() {
		return _summerStart;
	}

	public double winterRate() {
		return _winterRate;
	}

	public double summerRate() {
		return _summerRate;
	}
}
