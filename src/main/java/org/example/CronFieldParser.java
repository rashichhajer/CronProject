package org.example;

import java.time.LocalDate;
import java.util.*;

public class CronFieldParser {

    String part;
    CronFieldType fieldType;

    public CronFieldParser(String part, CronFieldType fieldType) {
        this.part=part;
        this.fieldType=fieldType;
    }

    public List<Integer> parse(){
        Set<Integer> values = new TreeSet<>();

        for (String splitPart : part.split(",")) {
            parsePart(splitPart, values);
        }

        return values.stream().toList();

    }

    private void parsePart(String splitPart, Set<Integer> values) {
        //order of statements is important
        if(splitPart.equals("*")) {
            processAsterick(values);
        }
        else if(splitPart.contains("/")){
            processStep(splitPart,values);
        }

        else if(splitPart.contains("-")){
            processRange(splitPart,values);
        }
        else{
            processSingleValue(splitPart,values);
        }
    }

    private void processAsterick(Set<Integer> values) {
        for(int i=this.fieldType.getMinValue();i<=this.fieldType.getMaxValue();i++){
            values.add(i);
        }
    }

    private void processSingleValue(String splitPart, Set<Integer> values) {
        int value = Integer.parseInt(splitPart);
        validateValue(value, splitPart);
        values.add(value);
    }

    private void processStep(String splitPart, Set<Integer> values) {
        String[] stepParts=splitPart.split("/");
        int step = Integer.parseInt(stepParts[1]);
        String stringPart = stepParts[0];
        Set<Integer> stepValues=new TreeSet<>();

        //ignore step if comma separated
        if(stringPart.contains(",")){
            for (String subPart : stringPart.split(",")) {
                parsePart(subPart, stepValues);
            }
            values.addAll(stepValues);
        }

        else{
            parsePart(stringPart,stepValues);
            int index = 0;
            for (int value : stepValues) {
                if (index % step == 0) {
                    values.add(value);
                }
                index++;
            }
        }
    }

    private void processRange(String splitPart, Set<Integer> values) {
        String[] rangeParts=splitPart.split("-");
        int start = Integer.parseInt(rangeParts[0]);
        int end = Integer.parseInt(rangeParts[1]);
        validateRange(start, end, splitPart);
        for(int i = start; i <= end; i++) {
            values.add(i);
        }
    }

    private void validateRange(int start, int end, String part) {
        if (start > end || start < this.fieldType.getMinValue() || end > this.fieldType.getMaxValue()) {
            throw new IllegalArgumentException("Invalid range: " + part);
        }

    }

    private void validateValue(int value, String part) {
        if (value < this.fieldType.getMinValue() || value > this.fieldType.getMaxValue()) {
            throw new IllegalArgumentException("Value out of range: " + part);
        }
    }


}
