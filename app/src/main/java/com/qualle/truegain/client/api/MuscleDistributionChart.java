package com.qualle.truegain.client.api;


import java.io.Serializable;
import java.util.Map;

public class MuscleDistributionChart implements Serializable {

    private Map<String, Float> thisMonthData;

    public MuscleDistributionChart() {
    }

    public MuscleDistributionChart(Map<String, Float> thisMonthData) {
        this.thisMonthData = thisMonthData;
    }

    public Map<String, Float> getThisMonthData() {
        return thisMonthData;
    }

    public void setThisMonthData(Map<String, Float> thisMonthData) {
        this.thisMonthData = thisMonthData;
    }

}
