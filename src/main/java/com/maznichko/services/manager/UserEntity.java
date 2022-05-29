package com.maznichko.services.manager;

public class UserEntity {
    private String login;
    private int amountOfOrdersDone;
    private int amountOfOrders;
    private int bank;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAmountOfOrdersDone() {
        return amountOfOrdersDone;
    }

    public void setAmountOfOrdersDone(int amountOfOrdersDone) {
        this.amountOfOrdersDone = amountOfOrdersDone;
    }

    public int getAmountOfOrders() {
        return amountOfOrders;
    }

    public void setAmountOfOrders(int amountOfOrders) {
        this.amountOfOrders = amountOfOrders;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }
}
