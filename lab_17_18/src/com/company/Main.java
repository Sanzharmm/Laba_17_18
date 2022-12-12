package com.company;

import com.company.controller.Controller;
import com.company.models.Hotel;
import com.company.xml.Writer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Hotel hotel = new Hotel("Хан Тенгри");
        hotel.addRoom(1200, 101);
        hotel.addRoom(2300, 102);
        hotel.getRoom(101).addClient(20);
        hotel.getRoom(102).addClient(10);

        hotel.getRoom(102).deleteClient();


        Controller controller = new Controller();

        controller.start();
    }
}
