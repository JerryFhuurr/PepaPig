package com.and.pepapig;

import java.util.ArrayList;

public class ResultList {
    private static ArrayList<CalculateResult> results = new ArrayList<>();

    public static void addResult(CalculateResult result) {
        results.add(result);
    }

    public static ArrayList<CalculateResult> getResults(){
        return results;
    }
}
