package org.javaLab5.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.javaLab5.collection.CustomCollection;
import org.javaLab5.model.Route;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * The {@code XmlWriter} class extends the {@code WriteHandler} class
 * and provides functionality for writing a collection of {@link Route} objects
 * to an XML file. The class uses Jackson's {@link ObjectMapper} to
 * serialize a {@link CustomCollection} of {@link Route} objects into XML
 * format and write them to a specified file path.
 * <p>
 * This class generates a structured XML document where each {@link Route}
 * object is represented as an XML element with appropriate child elements
 * for its properties.
 * </p>
 *
 * @see WriteHandler
 * @see CustomCollection
 * @see Route
 */
public class XmlWriter extends WriteHandler {

    /**
     * Serializes a collection of {@link Route} objects and writes them to
     * a specified file path in XML format.
     *
     * @param Path the path of the file where the XML data will be written.
     * @param collection the {@link CustomCollection} containing the {@link Route} objects to be serialized.
     * @throws Exception if an I/O error occurs while writing to the file or
     *         if the file is corrupted or does not exist.
     */
    @Override
    public void writeFile(String Path, CustomCollection collection) throws Exception {
        Set<Route> objectRoutes = new TreeSet<>(collection.getCollection());

        try {
            ObjectMapper xmlMapper = new ObjectMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(new File(Path), objectRoutes);
        } catch (IOException e) {
            throw new IOException("Check the file: '" + Path + "'. It may be corrupted or may not exist", e);
        }
    }
}