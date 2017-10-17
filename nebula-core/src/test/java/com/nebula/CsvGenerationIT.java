package com.nebula;

import com.nebula.core.Entity;
import com.nebula.generationconstraint.NebulaConstraints;
import com.nebula.generationrule.GenerationRuleBuilder;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static com.nebula.Nebula.*;
import static com.nebula.Nebula.newGenerationRule;
import static com.nebula.core.NebulaGenerationTypes.*;
import static com.nebula.core.NebulaGenerators.*;
import static com.nebula.formatter.NebulaFormatters.*;
import static com.nebula.output.NebulaOutputs.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CsvGenerationIT {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        temporaryFolder.create();
    }

    @After
    public void tearDown() throws Exception {
        temporaryFolder.delete();
    }

    @Test
    public void generate_should_generate_a_correct_csv_file() throws Exception {

        // GIVEN
        Model model = newModel();
        model.setSeed(1l);
        model.setDateFormat("dd/MM/yyyy");
        Entity users = newEntity("users", 100l);
        users.addProperty("firstName", random(), string().withPattern("[A-Z]{1}[a-z]{3,25}"));
        users.addProperty("lastName", random(), string().withPattern("[A-Z]{1}[a-z]{3,25}"));
        DateTime minDayOfBirth = new DateTime(1950, 1, 1, 0, 0);
        DateTime maxDayOfBirth = new DateTime(2000, 1, 1, 0, 0);
        users.addProperty("dayOfBirth", random(), dateTime().range().withMin(minDayOfBirth).withMax(maxDayOfBirth));
        model.addEntity(users);

        File fileToGenerate = new File(temporaryFolder.getRoot(), "users.csv");

        GenerationRuleBuilder generationRule = newGenerationRule()
                .withEntity(users)
                .withFormatter(csv().withSeparator(";").withColumns("lastName", "firstName", "dayOfBirth"))
                .addOutput(file(fileToGenerate.getPath()))
                //.addOutput(stdout())
                .addGenerationConstraint(NebulaConstraints.amount(10));

        model.addGenerationRule(generationRule);

        // WHEN

        model.generate();

        // THEN
        assertThat(fileToGenerate).hasContent(
                "lastName;firstName;dayOfBirth\n" +
                "Iouh;Zfjjm;22/03/1966\n" +
                "Ocjrj;Ftwsy;08/09/1990\n" +
                "Wrfhvvv;Nfftlu;23/08/1996\n" +
                "Cftojvj;Ttuc;28/06/1951\n" +
                "Irbqwqsdz;Aupwkvnlk;12/04/1986\n" +
                "Odqxhsh;Gicd;29/11/1981\n" +
                "Wsknwaazc;Mvlgltk;13/09/1966\n" +
                "Cgzu;Sianxvyif;25/10/1961\n" +
                "Isix;Aywd;09/08/1996\n" +
                "Ogxeiymwx;Gjjmw;25/01/1971\n" +
                "");
    }
}