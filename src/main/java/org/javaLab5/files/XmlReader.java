package org.javaLab5.files;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.model.Route;
import org.javaLab5.model.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A class responsible for reading XML files and converting them into a {@link CustomCollection} of {@link Route} objects.
 */
public class XmlReader extends ReadHandler {

    /**
     * Reads an XML file and parses its content into a {@link CustomCollection}.
     *
     * @param path the file path of the XML file to be read.
     * @return a {@link CustomCollection} containing parsed routes.
     * @throws Exception if the file cannot be read or contains invalid data.
     */
    @Override
    public CustomCollection readFile(String path) throws Exception {
        CustomCollection collection = new CustomCollection();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new File(path));

        doc.getDocumentElement().normalize();
        NodeList routes = doc.getElementsByTagName("route");

        for (int i = 0; i < routes.getLength(); i++) {
            Element routeElement = (Element) routes.item(i);
            try {
                Route route = parseRoute(routeElement);
                collection.addElement(route);
            } catch (Exception e) {
                System.err.println("Error parsing route: " + e.getMessage());
            }
        }
        return collection;
    }

    private Route parseRoute(Element routeElement) throws Exception {
        Route route = new Route();

        // Обязательные поля
        route.setId(Integer.parseInt(getTextContent(routeElement, "id")));
        route.setName(getTextContent(routeElement, "name"));
        route.setDistance(Double.parseDouble(getTextContent(routeElement, "distance")));

        // Координаты
        Element coordElement = (Element) routeElement.getElementsByTagName("coordinates").item(0);
        route.setCoordinates(
                Double.parseDouble(getTextContent(coordElement, "x")),
                Double.parseDouble(getTextContent(coordElement, "y"))
        );

        // Дата
        route.setCreationDate(new Date(Long.parseLong(getTextContent(routeElement, "creationDate"))));

        // Локации (опциональные)
        NodeList fromList = routeElement.getElementsByTagName("from");
        if (fromList.getLength() > 0 && !isNil((Element)fromList.item(0))) {
            route.setFrom(parseLocation((Element) fromList.item(0)));
        }

        NodeList toList = routeElement.getElementsByTagName("to");
        if (toList.getLength() > 0 && !isNil((Element)toList.item(0))) {
            route.setTo(parseLocation((Element) toList.item(0)));
        }

        return route;
    }

    private Location parseLocation(Element locElement) {
        Location loc = new Location();
        loc.setX(Long.parseLong(getTextContent(locElement, "x")));
        loc.setY(Float.parseFloat(getTextContent(locElement, "y")));
        loc.setZ(Float.parseFloat(getTextContent(locElement, "z")));
        loc.setName(getTextContent(locElement, "name"));
        return loc;
    }

    private String getTextContent(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private boolean isNil(Element element) {
        return element.hasAttribute("xsi:nil") &&
                "true".equals(element.getAttribute("xsi:nil"));
    }

    private boolean validateCoordinates(Element coordinates) {
        boolean valid = validateNumericAttribute(coordinates, "x", Double.class);
        return valid && validateNumericAttribute(coordinates, "y", Double.class);
    }

    private boolean validateLocation(Element location, String fieldName) {
        boolean valid = true;
        Element coordinates = (Element) location.getElementsByTagName("coordinates").item(0);

        valid = valid && validateNumericAttribute(coordinates, "x", Long.class);
        valid = valid && validateNumericAttribute(coordinates, "y", Float.class);
        valid = valid && validateNumericAttribute(coordinates, "z", Float.class);

        if (!location.hasAttribute("name") || location.getAttribute("name").trim().isEmpty()) {
            System.err.println("Route" + ": '" + fieldName + ".name' must be a non-empty string");
            valid = false;
        }

        return valid;
    }

    private boolean validateNumericAttribute(Element element, String attrName, Class<?> type) {
        if (!element.hasAttribute(attrName)) {
            System.err.println("Route" + ": missing required '" + attrName + "' attribute");
            return false;
        }

        try {
            String value = element.getAttribute(attrName);
            if (type == Double.class) {
                Double.parseDouble(value);
            } else if (type == Long.class) {
                Long.parseLong(value);
            } else if (type == Float.class) {
                Float.parseFloat(value);
            }
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Route" + ": '" + attrName + "' must be a " + type.getSimpleName());
            return false;
        }
    }

    private String elementToString(Element element) {
        return element.getTextContent();
    }
}