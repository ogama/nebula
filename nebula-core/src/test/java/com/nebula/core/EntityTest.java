package com.nebula.core;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;

import com.nebula.core.generators.Generator;
import com.nebula.core.generators.NebulaRandom;
import com.nebula.core.types.TypeBuilder;
import com.nebula.core.types.number.NumberRangeType;

public class EntityTest {

	@Test
	public void addProperty_should_add_new_property_in_entity_properties() {
		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);
		String propertyName = "name";
		TypeBuilder propertyType = NebulaGenerationTypes.number().range();
		Generator propertyGenerator = NebulaGenerators.random();

		// WHEN
		entity.addProperty(propertyName, propertyType, propertyGenerator);

		// THEN
		assertThat(entity.getProperties()).hasSize(1);
		assertThat(entity.getProperties().get(0).getName()).isEqualTo(propertyName);
		assertThat(entity.getProperties().get(0).getType()).isInstanceOf(NumberRangeType.class);
		assertThat(entity.getProperties().get(0).getGenerator()).isEqualTo(propertyGenerator);
	}

	@Test
	public void addProperty_should_throw_exception_when_duplicate_property_name_is_added() {

		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);
		Generator propertyGenerator = NebulaGenerators.random();
		TypeBuilder propertyType = NebulaGenerationTypes.number().range();
		String propertyName = "property name test";
		entity.addProperty(propertyName, propertyType, propertyGenerator);

		// WHEN
		catchException(entity).addProperty(propertyName, propertyType, propertyGenerator);

		// THEN
		assertThat(caughtException()).isInstanceOf(NebulaException.class)
				.hasMessage("duplicate property 'property name test' in entity 'test'");
	}

	@Test
	public void generateObject_should_generate_a_non_null_object() {

		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);

		// WHEN
		GeneratedObject result = entity.generateObject(1l);

		// THEN
		assertThat(result).isNotNull();
	}

	@Test
	public void generateObject_should_return_the_correct_amount_of_properties() {

		Entity entity = Nebula.newEntity("test", 1);
		String propertyName = "property";
		entity.addProperty(propertyName, NebulaGenerationTypes.number().range(), NebulaGenerators.random());
		entity.init(new NebulaRandom(1l));

		// WHEN
		GeneratedObject result = entity.generateObject(1l);

		// THEN
		assertThat(result.getGeneratedProperties()).hasSize(1);
	}

	@Test
	public void generateObject_should_return_a_generated_object_with_one_generated_property_equal_to_1() {

		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);
		String propertyName = "property";
		entity.addProperty(propertyName,
				NebulaGenerationTypes.number().range().withMin(BigDecimal.ONE).withMax(BigDecimal.ONE),
				NebulaGenerators.random());
		entity.init(new NebulaRandom(1l));

		// WHEN
		GeneratedObject result = entity.generateObject(1l);

		// THEN
		assertThat(result.getGeneratedProperties()).hasSize(1);
		assertThat(result.getGeneratedProperties().get(0).getPropertyName()).isEqualTo(propertyName);
		assertThat(result.getGeneratedProperties().get(0).getPropertyValue().getObject()).isEqualTo(BigDecimal.ONE);
	}

	@Test
	public void generateObject_should_return_a_generated_object_with_two_properties_first_equal_to_1_and_second_equal_to_5() {

		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);
		String property1Name = "property1";
		String property2Name = "property2";
		entity.addProperty(property1Name,
				NebulaGenerationTypes.number().range().withMin(BigDecimal.ONE).withMax(BigDecimal.ONE),
				NebulaGenerators.random());
		entity.addProperty(property2Name,
				NebulaGenerationTypes.number().range().withMin(BigDecimal.valueOf(5)).withMax(BigDecimal.valueOf(5)),
				NebulaGenerators.random());
		entity.init(new NebulaRandom(1l));

		// WHEN
		GeneratedObject result = entity.generateObject(1l);

		// THEN
		assertThat(result.getGeneratedProperties()).hasSize(2);
		assertThat(result.getGeneratedProperties().get(0).getPropertyName()).isEqualTo(property1Name);
		assertThat(result.getGeneratedProperties().get(0).getPropertyValue().getObject()).isEqualTo(BigDecimal.ONE);
		assertThat(result.getGeneratedProperties().get(1).getPropertyName()).isEqualTo(property2Name);
		assertThat(result.getGeneratedProperties().get(1).getPropertyValue().getObject())
				.isEqualTo(BigDecimal.valueOf(5));
	}

	@Test
	public void init_should_init_property_generators() {

		// GIVEN
		PropertyBuilder propertyBuilder = mock(PropertyBuilder.class);
		Property property = mock(Property.class);
		Generator generator = mock(Generator.class);
		when(property.getGenerator()).thenReturn(generator);
		when(propertyBuilder.newProperty(anyString(), any(TypeBuilder.class), any(Generator.class)))
				.thenReturn(property);
		Entity entity = new Entity("test", 1l, propertyBuilder);
		NebulaRandom nebulaRandom = mock(NebulaRandom.class);
		entity.addProperty(null, null, null);

		// WHEN
		entity.init(nebulaRandom);

		// THEN
		verify(generator, times(1)).init(eq(nebulaRandom));
	}

	@Test
	public void setPropertyBuilder_should_set_propertyBuilder_filed_in_given_entity() {

		// GIVEN
		Entity entity = Nebula.newEntity("test", 1);
		PropertyBuilder propertyBuilder = new PropertyBuilder();

		// WHEN
		entity.setPropertyBuilder(propertyBuilder);

		// THEN
		assertThat(entity).hasFieldOrPropertyWithValue("propertyBuilder", propertyBuilder);
	}
}
