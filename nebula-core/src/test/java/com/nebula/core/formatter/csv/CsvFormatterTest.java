package com.nebula.core.formatter.csv;

import com.nebula.core.Model;
import com.nebula.core.ModelBuilder;
import com.nebula.core.Entity;
import com.nebula.core.GeneratedObject;
import com.nebula.core.GeneratedProperty;
import com.nebula.core.formatter.ValueFormatter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nebula.core.types.NebulaTypes.string;
import static com.nebula.core.generators.NebulaGenerators.random;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvFormatterTest {

    private ValueFormatter valueFormatter;

    @Before
    public void setUp() throws Exception {
        valueFormatter = new ValueFormatter("dd/MM/yyyy", '.', ',');
    }

    @Test
    public void formatGeneratedObject_should_return_durant() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("lastName"), valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();
        generatedProperties.add(new GeneratedProperty("lastName", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("durant");
    }

    @Test
    public void formatGeneratedObject_should_return_jean_durant() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();
        generatedProperties.add(new GeneratedProperty("firstName", new GeneratedObject("jean"), string().build(model)));
        generatedProperties.add(new GeneratedProperty("lastName", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("jean;durant");
    }

    @Test
    public void formatGeneratedObject_should_return_jean_durant_if_no_coluns_are_specified() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, columnNames, valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();
        generatedProperties.add(new GeneratedProperty("firstName", new GeneratedObject("jean"), string().build(model)));
        generatedProperties.add(new GeneratedProperty("lastName", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("jean;durant");
    }

    @Test
    public void formatGeneratedObject_should_return_quotted_values() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "\"";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();
        generatedProperties.add(new GeneratedProperty("firstName", new GeneratedObject("jean"), string().build(model)));
        generatedProperties.add(new GeneratedProperty("lastName", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("\"jean\";\"durant\"");
    }

    @Test
    public void formatGeneratedObject_should_return_only_value_of_col1() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("col1"), valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();
        generatedProperties.add(new GeneratedProperty("col1", new GeneratedObject("jean"), string().build(model)));
        generatedProperties.add(new GeneratedProperty("col2", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("jean");
    }

    @Test
    public void formatGeneratedObject_should_return_all_columns_when_empty_column_list_is_passed() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, new ArrayList<>(), valueFormatter, propertiesToExclude);
        List<GeneratedProperty> generatedProperties = new ArrayList<>();

        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("col1", random(), string());
        entity.addProperty("col2", random(), string());

        generatedProperties.add(new GeneratedProperty("col1", new GeneratedObject("jean"), string().build(model)));
        generatedProperties.add(new GeneratedProperty("col2", new GeneratedObject("durant"), string().build(model)));
        GeneratedObject generatedObject = new GeneratedObject(generatedProperties);
        formatter.formatHeader(entity);

        // WHEN
        String formattedObject = formatter.formatGeneratedObject(generatedObject);

        // THEN
        assertThat(formattedObject).isEqualTo("jean;durant");
    }

    @Test
    public void formatHeader_should_return_firstName_lastName() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = true;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("firstName", random(), string());
        entity.addProperty("lastName", random(), string());

        // WHEN
        String formattedHeader = formatter.formatHeader(entity);

        // THEN
        assertThat(formattedHeader).isEqualTo("firstName;lastName");
    }

    @Test
    public void formatHeader_should_return_col1_col2() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = true;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("col1", "col2"), valueFormatter, propertiesToExclude);
        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("col1", random(), string());
        entity.addProperty("col2", random(), string());

        // WHEN
        String formattedHeader = formatter.formatHeader(entity);

        // THEN
        assertThat(formattedHeader).isEqualTo("col1;col2");
    }

    @Test
    public void formatHeader_should_return_empty_string() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("firstName", random(), string());
        entity.addProperty("lastName", random(), string());

        // WHEN
        String formattedHeader = formatter.formatHeader(entity);

        // THEN
        assertThat(formattedHeader).isEmpty();
    }

    @Test
    public void formatHeader_should_return_quoted_strings() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = true;
        String quote = "\"";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("firstName", random(), string());
        entity.addProperty("lastName", random(), string());

        // WHEN
        String formattedHeader = formatter.formatHeader(entity);

        // THEN
        assertThat(formattedHeader).isEqualTo("\"firstName\";\"lastName\"");
    }

    @Test
    public void formatFooter_should_return_empty_string() throws Exception {

        // GIVEN
        Model model = new ModelBuilder().build();
        String separator = ";";
        boolean header = false;
        String quote = "";
        List<String> propertiesToExclude = new ArrayList<>();
        CsvFormatter formatter = new CsvFormatter(separator, quote, header, Arrays.asList("firstName", "lastName"), valueFormatter, propertiesToExclude);
        Entity entity = model.newEntity("test", Long.MAX_VALUE);
        entity.addProperty("firstName", random(), string());
        entity.addProperty("lastName", random(), string());

        // WHEN
        String formattedHeader = formatter.formatFooter(entity);

        // THEN
        assertThat(formattedHeader).isEmpty();
    }
}