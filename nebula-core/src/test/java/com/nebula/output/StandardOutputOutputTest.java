package com.nebula.output;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.PrintStream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class StandardOutputOutputTest {

    private PrintStream printStream;

    @Before
    public void setUp() throws Exception {
        printStream = mock(PrintStream.class);
        System.setOut(printStream);
    }

    @Test
    public void write_should_write_test_in_stdout() throws Exception {

        // GIVEN
        StandardOutputOutput output = new StandardOutputOutput();
        String formattedObject = "test";

        // WHEN
        output.write(formattedObject);

        // THEN
        verify(printStream, times(1)).print(formattedObject);
    }

    @Test
    public void write_should_write_test_2_times_in_stdout() throws Exception {

        // GIVEN
        StandardOutputOutput output = new StandardOutputOutput();
        String formattedObject = "test";

        // WHEN
        output.write(formattedObject, formattedObject);

        // THEN
        verify(printStream, times(2)).print(formattedObject);
    }

    @Test
    public void write_should_flush_stdout_after_writing_object() throws Exception {

        // GIVEN
        StandardOutputOutput output = new StandardOutputOutput();

        // WHEN
        output.write("test");

        // THEN
        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream, times(1)).print(anyString());
        inOrder.verify(printStream, times(1)).flush();
    }

    @Test
    public void write_should_flush_stdout_after_writing_objects() throws Exception {

        // GIVEN
        StandardOutputOutput output = new StandardOutputOutput();

        // WHEN
        output.write("test", "test");

        // THEN
        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream, times(2)).print(anyString());
        inOrder.verify(printStream, times(1)).flush();
    }
}
