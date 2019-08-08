package com.delta;

public class EchartResult {

    private String  name;
    private int value;

    public EchartResult() {
    }

    public EchartResult(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EchartResult{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
