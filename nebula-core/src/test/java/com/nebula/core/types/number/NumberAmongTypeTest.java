package com.nebula.core.types.number;

import com.nebula.core.GeneratedObject;
import com.nebula.core.NebulaException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

class NumberAmongTypeTest {

	@Test
	void newNumberAmongType_should_set_items_in_fields() {

		// GIVEN
		NumberAmongType numberAmongType;
		List<BigDecimal> expectedItems = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);

		// WHEN
		numberAmongType = new NumberAmongType(expectedItems);

		// THEN
		assertThat(numberAmongType).hasFieldOrPropertyWithValue("items", expectedItems);
	}

	@Test
	void generateObject_should_return_0() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
        GeneratedObject result = numberAmongType.generateObject(Collections.emptyList(), 0L);

		// THEN
		assertThat(result.getObject()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	void generateObject_should_return_1() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
        GeneratedObject result = numberAmongType.generateObject(Collections.emptyList(), 1L);

		// THEN
		assertThat(result.getObject()).isEqualTo(BigDecimal.ONE);
	}

	@Test
	void generateObject_should_throw_exception_when_object_index_is_out_of_range() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
        catchException(numberAmongType).generateObject(Collections.emptyList(), 10L);

		// THEN
		assertThat((Exception) caughtException()).isInstanceOf(NebulaException.class)
				.hasMessage("requested object is out of range");
	}

	@Test
	void generateObject_should_throw_exception_when_object_index_is_last_index_plus_one() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
        catchException(numberAmongType).generateObject(Collections.emptyList(), 3L);

		// THEN
		assertThat((Exception) caughtException()).isInstanceOf(NebulaException.class)
				.hasMessage("requested object is out of range");
	}

	@Test
	void generateObject_should_throw_exception_when_object_index_negative() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
        catchException(numberAmongType).generateObject(Collections.emptyList(), -1L);

		// THEN
		assertThat((Exception) caughtException()).isInstanceOf(NebulaException.class)
				.hasMessage("requested object is out of range");
	}

	@Test
	void getMinRange_should_return_0() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
		Long result = numberAmongType.getMinRange();

		// THEN
		assertThat(result).isEqualTo(0L);
	}

	@Test
	void getMaxRange_should_return_0() {

		// GIVEN
		List<BigDecimal> items = Collections.singletonList(BigDecimal.ZERO);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
		Long result = numberAmongType.getMaxRange();

		// THEN
		assertThat(result).isEqualTo(0L);
	}

	@Test
	void getMaxRange_should_return_1() {

		// GIVEN
		List<BigDecimal> items = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE);
		NumberAmongType numberAmongType = new NumberAmongType(items);

		// WHEN
		Long result = numberAmongType.getMaxRange();

		// THEN
		assertThat(result).isEqualTo(1L);
	}
}
