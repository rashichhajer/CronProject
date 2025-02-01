package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Mock
    private CronFieldParser cronFieldParser;

    @Test
    void testValidCronExpression() {
        String cronExpression = "0 0 1 1 * /usr/bin/find";

        String[] parts = cronExpression.split("\\s+");
        assertEquals(6, parts.length, "Valid cron expressions must have 6 fields.");
    }

    @Test
    void testInvalidCronExpression_FewerFields() {
        String cronExpression = "0 0 1 1 *"; // Missing command field
        String[] parts = cronExpression.split("\\s+");
        assertTrue(parts.length < 6, "Invalid cron expressions must have at least 6 fields.");
    }

    @Test
    void testParsedCronFields() {
        String[] args={"*/15 0 1,15 * 1-5 /usr/bin/find"};
        String cronExpression = args[0];
        String[] parts = cronExpression.split("\\s+");
        Map<CronFieldType, List<Integer>> parsedFields = Main.parseCronExpression(parts);
        Map<CronFieldType, List<Integer>> expectedValues = new HashMap<>();

        expectedValues.put(CronFieldType.MINUTE, Arrays.asList(0, 15, 30, 45));
        expectedValues.put(CronFieldType.HOUR, Arrays.asList(0));
        expectedValues.put(CronFieldType.DAY_OF_MONTH, Arrays.asList(1, 15));
        expectedValues.put(CronFieldType.MONTH, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        expectedValues.put(CronFieldType.DAY_OF_WEEK, Arrays.asList(1, 2, 3, 4, 5));
        for (Map.Entry<CronFieldType, List<Integer>> entry : expectedValues.entrySet()) {
            assertEquals(entry.getValue(), parsedFields.get(entry.getKey()));
        }

        printTable(parsedFields, parts[parts.length-1]);

    }

    private static void printTable(Map<CronFieldType, List<Integer>> parsedFields, String command) {
        List<CronFieldType> fieldOrder = Arrays.asList(
                CronFieldType.MINUTE, CronFieldType.HOUR,
                CronFieldType.DAY_OF_MONTH, CronFieldType.MONTH,
                CronFieldType.DAY_OF_WEEK);

        System.out.println("\nParsed Cron Expression:");
        for (CronFieldType type : fieldOrder) {
            System.out.printf("%-14s: %s%n", type.name(), listToString(parsedFields.get(type)));
        }

        System.out.printf("%-14s: %s%n", "COMMAND", command);
    }

    private static String listToString(List<Integer> list) {
        return list.stream().map(String::valueOf).reduce((a, b) -> a + " " + b).orElse("");
    }


}
