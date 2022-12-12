package com.company.models;

import com.company.models.Client;

public class HotelRoom {
    private Client client;
    private int oneDayPrice;
    private int number;

    public HotelRoom (int oneDayPrice, int number) {
        this.oneDayPrice = oneDayPrice;
        this.number = number;
    }

    public void addClient(int days) {
        client = new Client(days);
    }

    public void deleteClient() {
        this.client = null;
    }

    public boolean haveClient() {
        return client != null;
    }

    public boolean isNumber(int number) {
        return this.number == number;
    }

    public int totalPriceCalculator() {
        if (haveClient()) {
            return oneDayPrice * client.getDayCount();
        }

        return 0;
    }

    public Client getClient() {
        return client;
    }

    public int getNumber() {
        return number;
    }

    public int getOneDayPrice() {
        return oneDayPrice;
    }
}
