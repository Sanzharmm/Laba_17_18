package com.company.controller;

import com.company.models.Client;
import com.company.models.Hotel;
import com.company.models.HotelRoom;
import com.company.xml.Writer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Scanner in = new Scanner(System.in);
    private int command;
    private String commands = "Список команд:\n" +
            "1. Добавить новый отель\n" +
            "2. Добавить новый номер\n" +
            "3. Добавить нового клиента\n" +
            "4. Показать все записи\n" +
            "5. Общее количество клиентов\n" +
            "6. Суммарная плата за проживание\n" +
            "7. Остановить программу";
    private Writer writer;

    public Controller() {
        try {
            writer = new Writer();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        String hotelName;
        int number;
        int price;
        boolean work = true;
        Client client;
        Hotel hotel;

        System.out.println(commands);
        while (work) {
            System.out.println("------------------------------------------");
            System.out.print("Введите номер команды: ");
            command = in.nextInt();
            switch (command) {
                case 1:
                    System.out.print("Введите название отеля: ");
                    in.nextLine();
                    hotel = new Hotel(in.nextLine());
                    writer.addHotel(hotel);
                    break;
                case 2:
                    System.out.print("Введите название отеля: ");
                    in.nextLine();
                    hotelName = in.nextLine();
                    System.out.print("Введите номер комнаты: ");
                    number = in.nextInt();
                    System.out.print("Введите цену за один день: ");
                    price = in.nextInt();
                    HotelRoom room = new HotelRoom(price, number);
                    writer.addRoom(room, hotelName);
                    break;
                case 3:
                    System.out.print("Введите название отеля: ");
                    in.nextLine();
                    hotelName = in.nextLine();
                    System.out.print("Введите номер комнаты: ");
                    number = in.nextInt();
                    System.out.print("Введите количество дней: ");
                    client = new Client(in.nextInt());
                    writer.addClient(client, number, hotelName);
                    break;
                case 4:
                    writer.printAll();
                    break;
                case 5:
                    System.out.print("Введите название отеля: ");
                    in.nextLine();
                    System.out.println("Общее число клиентов - " + writer.getHotel(in.nextLine()).clientCounts());
                    break;
                case 6:
                    System.out.print("Введите название отеля: ");
                    in.nextLine();
                    hotel = writer.getHotel(in.nextLine());
                    System.out.print("Введите номер комнаты: ");
                    System.out.println("Общая сумма за проживание: " + hotel.getRoom(in.nextInt()).totalPriceCalculator());
                    break;
                case 7:
                    work = false;
                    break;
            }
            writer.close();
        }
    }

}
