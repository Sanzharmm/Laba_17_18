package com.company.xml;

import com.company.models.Client;
import com.company.models.Hotel;
import com.company.models.HotelRoom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    private Document doc;
    private Element main;

    public Writer() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.parse(new File("src/resourses/data.xml"));
        main = doc.getDocumentElement();
    }

    public void close() {
        doc.normalizeDocument();
        try (FileOutputStream output =
                     new FileOutputStream("src/resourses/data.xml")) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void addHotel(Hotel hotel) {
        Element el = doc.createElement("Hotel");
        el.setAttribute("name", hotel.getName());
        main.appendChild(el);
    }

    public void addRoom(HotelRoom room, String hotelName) {
        NodeList hotels = main.getChildNodes();
        Element el = doc.createElement("Room");
        el.setAttribute("number", String.valueOf(room.getNumber()));
        el.setAttribute("price", String.valueOf(room.getOneDayPrice()));
        for (int i = 0; i < hotels.getLength(); i++) {
            if (hotels.item(i) instanceof Element) {
                if (((Element) hotels.item(i)).getAttribute("name").equals(hotelName)) {
                    hotels.item(i).appendChild(el);
                }
            }
        }
    }

    public void addClient(Client client, int roomNumber, String hotelName) {
        Element el = doc.createElement("Client");
        el.setAttribute("days", String.valueOf(client.getDayCount()));
        NodeList hotels = main.getChildNodes();
        for (int i = 0; i < hotels.getLength(); i++) {
            if (hotels.item(i) instanceof Element) {
                if (((Element) hotels.item(i)).getAttribute("name").equals(hotelName)) {
                    NodeList rooms = hotels.item(i).getChildNodes();
                    for (int j = 0; j < rooms.getLength(); j++) {
                        if (rooms.item(j) instanceof Element) {
                            if (((Element) rooms.item(j)).getAttribute("number").equals("" + roomNumber)){
                                if (rooms.item(j).getFirstChild() != null) {
                                    System.out.println("Клиент уже существует!");
                                } else {
                                    rooms.item(j).appendChild(el);
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }


    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }


    public void printAll() {
        NodeList hotels = main.getChildNodes();
        NodeList rooms;
        NodeList clients;
        for (int i = 0; i < hotels.getLength(); i++) {
            if (hotels.item(i) instanceof Element) {
                System.out.println(((Element) hotels.item(i)).getAttribute("name"));
                rooms = hotels.item(i).getChildNodes();
                for (int j = 0; j < rooms.getLength(); j++) {
                    if (rooms.item(j) instanceof Element) {
                        System.out.println("\tRoom number " + ((Element) rooms.item(j)).getAttribute("number") + " | Price for one day " + ((Element) rooms.item(j)).getAttribute("price"));
                        clients = rooms.item(j).getChildNodes();
                        for (int k = 0; k < clients.getLength(); k++) {
                            if (clients.item(k) instanceof Element) {
                                System.out.println("\t\tClient number " + k + " | Day count " + ((Element) clients.item(k)).getAttribute("days"));
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Hotel> getHotels() {
        List<Hotel> hotelList = new ArrayList<>();
        int id = -1;
        int idRoom;
        NodeList hotels = main.getChildNodes();
        NodeList rooms;
        NodeList clients;
        for (int i = 0; i < hotels.getLength(); i++) {
            if (hotels.item(i) instanceof Element) {
                id++;
                hotelList.add(new Hotel(((Element) hotels.item(i)).getAttribute("name")));
                rooms = hotels.item(i).getChildNodes();
                for (int j = 0; j < rooms.getLength(); j++) {
                    if (rooms.item(j) instanceof Element) {
                        idRoom = Integer.valueOf(((Element) rooms.item(j)).getAttribute("number"));
                        hotelList.get(id).addRoom(Integer.valueOf(((Element) rooms.item(j)).getAttribute("price")), idRoom);
                        clients = rooms.item(j).getChildNodes();
                        for (int k = 0; k < clients.getLength(); k++) {
                            if (clients.item(k) instanceof Element) {
                                hotelList.get(id).getRoom(idRoom).addClient(Integer.valueOf(((Element) clients.item(k)).getAttribute("days")));
                            }
                        }
                    }
                }
            }
        }
        return hotelList;
    }

    public Hotel getHotel(String name) {
        for (Hotel i:getHotels()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
