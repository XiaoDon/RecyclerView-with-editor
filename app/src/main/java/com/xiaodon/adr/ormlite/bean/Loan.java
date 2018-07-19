package com.xiaodon.adr.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "table_loans")
public class Loan {
    //主键

    @DatabaseField(generatedId = true)
    private int loan_id;//id
    @DatabaseField(useGetSet = true,columnName = "load_way")
    private String loan_way;//贷款方式
    @DatabaseField(useGetSet = true,columnName = "repay_way")
    private  String repay_way;//还款方式
    @DatabaseField(useGetSet = true,columnName = "totalmomey")
    private int totalmomey;//总额
    @DatabaseField(useGetSet = true,columnName = "rate")
    private int rate;//利率
    @DatabaseField(useGetSet = true,columnName = "term")
    private int load_term;//年限
    @DatabaseField(useGetSet = true,columnName = "advance_time")
    private String advance_time;//提前还款日期
    @DatabaseField(useGetSet = true,columnName = "first_time")
    private String frist_time;//首次还款日期
    @DatabaseField(useGetSet = true,columnName = "reg_time")
    private String reg_time;

    //无参构造
    public Loan() {
    }

    public Loan( String loan_way, String repay_way, int totalmomey, int rate, int load_term, String advance_time, String frist_time,String reg_time) {
        this.loan_way = loan_way;
        this.repay_way = repay_way;
        this.totalmomey = totalmomey;
        this.rate = rate;
        this.load_term = load_term;
        this.advance_time = advance_time;
        this.frist_time = frist_time;
        this.reg_time = reg_time;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public String getLoan_way() {
        return loan_way;
    }

    public void setLoan_way(String loan_way) {
        this.loan_way = loan_way;
    }


    public String getRepay_way() {
        return repay_way;
    }

    public void setRepay_way(String repay_way) {
        this.repay_way = repay_way;
    }

    public int getTotalmomey() {
        return totalmomey;
    }

    public void setTotalmomey(int totalmomey) {
        this.totalmomey = totalmomey;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getLoad_term() {
        return load_term;
    }

    public void setLoad_term(int load_term) {
        this.load_term = load_term;
    }

    public String getAdvance_time() {
        return advance_time;
    }

    public void setAdvance_time(String advance_time) {
        this.advance_time = advance_time;
    }

    public String getFrist_time() {
        return frist_time;
    }

    public void setFrist_time(String frist_time) {
        this.frist_time = frist_time;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    @Override
    public String toString() {
        return "loan{" +
                "name='" + loan_way + '\'' +
                ", sex='" + repay_way + '\'' +
                ", sex='" + totalmomey + '\'' +
                ", sex='" + rate +
                '}';
    }

}
