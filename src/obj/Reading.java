package obj;

import java.time.LocalDate;

public class Reading {

	private LocalDate _date;
	private int _amount;

	public Reading(int amount, LocalDate date) {
		_amount = amount;
		_date = date;
	}

	public int amount() {
		return _amount;
	}

	public LocalDate date() {
		return _date;
	}

}
