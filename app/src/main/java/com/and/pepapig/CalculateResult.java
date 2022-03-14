package com.and.pepapig;

public class CalculateResult {
    private String expression;
    private int no;

    public CalculateResult(String expression){
        this.expression = expression;
        this.no = 0;
    }

    public String getExpression() {
        return expression;
    }

    public void setNo(int no){
        this.no = no;
    }
}
