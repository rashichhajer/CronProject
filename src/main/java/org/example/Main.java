package org.example;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static CronFieldType[] FIELD_TYPES={CronFieldType.MINUTE,
            CronFieldType.HOUR,
            CronFieldType.DAY_OF_MONTH,
            CronFieldType.MONTH,
            CronFieldType.DAY_OF_WEEK};
    public static void main(String[] args) {

        //String[] args={"0 0 28 2 * /usr/bin/find"};
        if (args.length != 1) {
            System.out.println("Usage: java ArgumentExample \"*/15 0 1,15 * 1-5 /usr/bin/find\"");
            return;
        }
        String cronExpression = args[0];
        String[] parts = cronExpression.split("\\s+");
        if (parts.length < 6  ||  parts.length>6) {
            System.out.println("Invalid cron expression. Must contain 6 fields - min hour day_of_month month day_of_week cmd");
            return;
        }

        try{

            Map<CronFieldType, List<Integer>>  parsedFields=parseCronExpression(parts);
            String command = parts[parts.length-1];
            printTable(parsedFields, command);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public  static Map<CronFieldType, List<Integer>> parseCronExpression(String[] parts) {

        Map<CronFieldType, List<Integer>> parsedFields = new HashMap<>();
        for (int i = FIELD_TYPES.length - 1; i >= 0; i--) {
            if (FIELD_TYPES[i] == CronFieldType.MONTH) {
                List<Integer> months = new CronFieldParser(parts[i], FIELD_TYPES[i]).parse();
                CronFieldType.DAY_OF_MONTH.updateMaxDay(months, LocalDate.now().getYear());
                parsedFields.put(FIELD_TYPES[i], months);
            } else parsedFields.put(FIELD_TYPES[i], new CronFieldParser(parts[i], FIELD_TYPES[i]).parse());
        }
        return parsedFields;

    }

    private static void printTable(Map<CronFieldType, List<Integer>> parsedFields, String command) {
        for (CronFieldType type : FIELD_TYPES) {
            System.out.printf("%-14s%s%n", type.name(), listToString(parsedFields.get(type)));
        }

        System.out.printf("%-14s%s%n", "COMMAND", command);
    }

    private static String listToString(List<Integer> list) {
        return list.stream().map(String::valueOf).reduce((a, b) -> a + " " + b).orElse("");
    }
}