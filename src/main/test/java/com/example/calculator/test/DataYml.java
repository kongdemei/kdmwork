package com.example.calculator.test;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/4/8
 */
public class DataYml {
    Integer as;
    Integer bs;
    Integer result;

    public Integer getAs() {
        return as;
    }

    public void setAs(Integer as) {
        this.as = as;
    }

    public Integer getBs() {
        return bs;
    }

    public void setBs(Integer bs) {
        this.bs = bs;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DataYml{" +
                "as=" + as +
                ", bs=" + bs +
                ", result=" + result +
                '}';
    }
}
