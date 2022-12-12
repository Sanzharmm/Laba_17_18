package com.company.models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<HotelRoom> rooms;
    private int count;

    public Hotel(String name) {
        rooms = new ArrayList<>();
        this.name = name;
    }

    public void addRoom(int price, int number) {
        rooms.add(new HotelRoom(price, number));
    }

    public HotelRoom getRoom(int number) {
        for (HotelRoom room : rooms) {
            if (room.isNumber(number)) {
                return room;
            }
        }

        return null;
    }

    public int clientCounts() {
        count = 0;
        for (HotelRoom hotelRoom: rooms) {
            if (hotelRoom.haveClient()) {
                count++;
            }
        }

        return count;
    }

    public String getName() {
        return name;
    }
}
