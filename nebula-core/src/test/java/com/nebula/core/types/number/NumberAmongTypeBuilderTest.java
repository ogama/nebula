package com.nebula.core.types.number;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.nebula.core.NebulaException;
import com.nebula.core.types.AmongTypeBuilder;
import com.nebula.core.types.Type;

public class NumberAmongTypeBuilderTest {

	@Test
	public void build_should_return_a_new_instance_of_NumberAmongType_with_defaults() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();

		// WHEN
		Type result = builder.build();

		// THEN
		assertThat(result).isInstanceOf(NumberAmongType.class);
	}

	@Test
	public void items_should_return_the_builder_with_given_item_as_collection() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();
		BigDecimal item = BigDecimal.ZERO;

		// WHEN
		AmongTypeBuilder<BigDecimal> result = builder.items(item);

		// THEN
		assertThat(result).hasFieldOrPropertyWithValue("items", Arrays.asList(item));
	}

	@Test
	public void items_should_return_the_builder_with_given_items_as_collection() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();
		BigDecimal item1 = BigDecimal.ZERO;
		BigDecimal item2 = BigDecimal.ONE;

		// WHEN
		AmongTypeBuilder<BigDecimal> result = builder.items(item1, item2);

		// THEN
		assertThat(result).hasFieldOrPropertyWithValue("items", Arrays.asList(item1, item2));
	}

	@Test
	public void items_should_return_the_builder_with_null_as_collection() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();

		// WHEN
		AmongTypeBuilder<BigDecimal> result = builder.items(new BigDecimal[] { null });

		// THEN
		assertThat(result).hasFieldOrPropertyWithValue("items", Arrays.asList(new BigDecimal[] { null }));
	}

	@Test
	public void items_should_throw_exception_when_null_items_is_passed() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();

		// WHEN
		catchException(builder).items(null);

		// THEN
		assertThat(caughtException()).isInstanceOf(NebulaException.class).hasMessage("items is null");
	}

	@Test
	public void items_should_throw_exception_when_empty_items_is_passed() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();

		// WHEN
		catchException(builder).items(new BigDecimal[] {});

		// THEN
		assertThat(caughtException()).isInstanceOf(NebulaException.class).hasMessage("items is empty");
	}

	@Test
	public void build_should_NumberAmongType_with_items() {

		// GIVEN
		NumberAmongTypeBuilder builder = new NumberAmongTypeBuilder();
		BigDecimal item1 = BigDecimal.ZERO;
		BigDecimal item2 = BigDecimal.ONE;

		// WHEN
		Type result = builder.items(item1, item2).build();

		// THEN
		assertThat(result).hasFieldOrPropertyWithValue("items", Arrays.asList(item1, item2));
	}
}