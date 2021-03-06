package com.nebula.core.types.date.strategy;

import org.joda.time.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeMonthStrategyTest {

	@Test
    @DisplayName("getByDateAndIndexAndInterval should return the initial date for index 0")
	void getByDateAndIndexAndInterval_should_return_the_initial_date_for_index_0() {

		// GIVEN
		DateTimeStrategy adder = new DateTimeMonthStrategy();
		DateTime initialDate = new DateTime(2017, 1, 1, 0, 0, 0, 0);
		Long index = 0L;

		// WHEN
		DateTime result = adder.getByDateAndIndexAndInterval(initialDate, index);

		// THEN
		assertThat(result).isEqualTo(initialDate);
	}

	@Test
    @DisplayName("getByDateAndIndexAndInterval should return the initial plus 1 milisecond date for index 1")
	void getByDateAndIndexAndInterval_should_return_the_initial_plus_1_milisecond_date_for_index_1() {

		// GIVEN
		DateTimeStrategy adder = new DateTimeMonthStrategy();
		DateTime initialDate = new DateTime(2017, 1, 1, 0, 0, 0, 0);
		Long index = 1L;

		// WHEN
		DateTime result = adder.getByDateAndIndexAndInterval(initialDate, index);

		// THEN
		assertThat(result).isEqualTo(new DateTime(2017, 2, 1, 0, 0, 0, 0));
	}

	@Test
    @DisplayName("getMaxIndex should return 10")
	void getMaxIndex_should_return_10() {

		// GIVEN
		DateTimeStrategy strategy = new DateTimeMonthStrategy();
		DateTime min = new DateTime(2017, 1, 1, 0, 0, 0, 0);
		DateTime max = new DateTime(2017, 11, 1, 0, 0, 0, 0);

		// WHEN
		Long result = strategy.getMaxIndex(min, max);

		// THEN
		assertThat(result).isEqualTo(10L);
	}

	@Test
    @DisplayName("getMaxIndex should return 1")
	void getMaxIndex_should_return_1() {

		// GIVEN
		DateTimeStrategy strategy = new DateTimeMonthStrategy();
		DateTime min = new DateTime(2017, 1, 1, 0, 0, 0, 0);
		DateTime max = new DateTime(2017, 2, 1, 0, 0, 0, 0);

		// WHEN
		Long result = strategy.getMaxIndex(min, max);

		// THEN
		assertThat(result).isEqualTo(1L);
	}
}