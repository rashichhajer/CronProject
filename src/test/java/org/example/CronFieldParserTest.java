package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CronFieldParserTest {

    private CronFieldType minuteType;
    private CronFieldType hourType;
    private CronFieldType dayOfMonthType;
    private CronFieldType monthType;
    private CronFieldType dayOfWeekType;

    @BeforeEach
    void setUp() {
        minuteType = CronFieldType.MINUTE;
        hourType = CronFieldType.HOUR;
        dayOfMonthType = CronFieldType.DAY_OF_MONTH;
        monthType = CronFieldType.MONTH;
        dayOfWeekType = CronFieldType.DAY_OF_WEEK;
    }

    @Test
    void testWildcard_AllValues() {
        CronFieldParser parser = new CronFieldParser("*", minuteType);
        List<Integer> values = parser.parse();
        assertEquals(60, values.size());
        assertEquals(0, values.get(0));
        assertEquals(59, values.get(59));
    }

    @Test
    void testSingleValue() {
        CronFieldParser parser = new CronFieldParser("15", minuteType);
        List<Integer> values = parser.parse();
        assertEquals(List.of(15), values);
    }

    @Test
    void testRange() {
        CronFieldParser parser = new CronFieldParser("10-15", hourType);
        List<Integer> values = parser.parse();
        assertEquals(6, values.size());
        assertEquals(List.of(10,11,12,13,14,15),values);
    }

    @Test
    void testStepValues() {
        CronFieldParser parser = new CronFieldParser("0-30/5", minuteType);
        List<Integer> values = parser.parse();
        assertEquals(List.of(0, 5, 10, 15, 20, 25, 30), values);
    }

    @Test
    void testCommaSeparatedValues() {
        CronFieldParser parser = new CronFieldParser("10,20,30", minuteType);
        List<Integer> values = parser.parse();
        assertEquals(List.of(10, 20, 30), values);
    }

    @Test
    void testInvalidRange_StartGreaterThanEnd() {
        CronFieldParser parser = new CronFieldParser("30-10", minuteType);
        Exception exception = assertThrows(IllegalArgumentException.class, parser::parse);
        assertTrue(exception.getMessage().contains("Invalid range"));
    }

    @Test
    void testInvalidValue_OutOfRange() {
        CronFieldParser parser = new CronFieldParser("61", minuteType);
        Exception exception = assertThrows(IllegalArgumentException.class, parser::parse);
        assertTrue(exception.getMessage().contains("Value out of range"));
    }
}
