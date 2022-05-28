package com.maznichko.services.manager;

public class ReportEntity implements Comparable{
    String masterLogin;
    int takeOrders;
    int doneOrders;
    int amountOfOrders;
    float earnings;
    double rate;

    public int getTakeOrders() {
        return takeOrders;
    }

    public void setTakeOrders(int takeOrders) {
        this.takeOrders = takeOrders;
    }

    public int getDoneOrders() {
        return doneOrders;
    }

    public void setDoneOrders(int doneOrders) {
        this.doneOrders = doneOrders;
    }

    public String getMasterLogin() {
        return masterLogin;
    }

    public void setMasterLogin(String masterLogin) {
        this.masterLogin = masterLogin;
    }

    public int getAmountOfOrders() {
        return amountOfOrders;
    }

    public void setAmountOfOrders(int amountOfOrders) {
        this.amountOfOrders = amountOfOrders;
    }

    public float getEarnings() {
        return earnings;
    }

    public void setEarnings(float earnings) {
        this.earnings = earnings;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public int compareTo(Object o) {
        ReportEntity report = (ReportEntity) o;
        return Integer.compare(report.getAmountOfOrders(), this.getAmountOfOrders());
    }
}
