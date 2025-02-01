package org.example;

import java.time.YearMonth;
import java.util.List;

public enum CronFieldType {
    MINUTE(0,59),
    HOUR(0,23),
    DAY_OF_MONTH(1,31),
    MONTH(1,12),
    DAY_OF_WEEK(1,7);

    private  int minValue;
    private   int maxValue;

    CronFieldType(int minValue, int maxValue){
        this.minValue=minValue;
        this.maxValue=maxValue;
    }

    public int getMinValue(){
        return minValue;
    }

    public int getMaxValue(){
        return maxValue;
    }

    private void setMaxValue(int value){
        this.maxValue=value;
    }

    public int getMaxDaysForMonth(int year, int month) {
        if (this == DAY_OF_MONTH) {
            return YearMonth.of(year, month).lengthOfMonth(); // Gets correct max days
        }
        return maxValue;
    }
    public void updateMaxDay(List<Integer> month, int year) {
        if (this == DAY_OF_MONTH) {
            int maxDay=this.getMaxValue();
            if(month.size()!=0)
                maxDay= YearMonth.of(year, month.get(0)).lengthOfMonth(); // Gets correct max days
            setMaxValue(maxDay);
        }

    }
}
