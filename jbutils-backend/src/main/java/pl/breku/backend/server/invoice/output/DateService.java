package pl.breku.backend.server.invoice.output;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Created by brekol on 16.02.16.
 */
@Service
public class DateService {

	public String getLastDayOfPreviousMonth() {
		final LocalDate today = new LocalDate();
		final LocalDate shiftedDate = today.minusMonths(1);
		return shiftedDate.getYear() + "/" + shiftedDate.getMonthOfYear() + "/" + shiftedDate.dayOfMonth().getMaximumValue();
	}

	public String getFirstDayOfPreviousMonth() {
		final LocalDate today = new LocalDate();
		final LocalDate shiftedDate = today.minusMonths(1);
		return shiftedDate.getYear() + "/" + shiftedDate.getMonthOfYear() + "/" + shiftedDate.dayOfMonth().getMinimumValue();
	}

	public String getPreviousMonth() {
		final LocalDate today = new LocalDate();
		final LocalDate shiftedDate = today.minusMonths(1);
		return "" + shiftedDate.getMonthOfYear();
	}

	public String getPreviousMonthsYear() {
		final LocalDate today = new LocalDate();
		final LocalDate shiftedDate = today.minusMonths(1);
		return "" + shiftedDate.getYear();
	}
}
