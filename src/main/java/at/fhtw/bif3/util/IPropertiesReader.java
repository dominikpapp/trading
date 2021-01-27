package at.fhtw.bif3.util;

import java.util.Properties;
import java.util.logging.Logger;

public interface IPropertiesReader {

    Logger LOGGER = Logger.getLogger(IPropertiesReader.class.getName());
    String PROPERTIES_FILE_ENDING = ".properties";

    Properties getProperties();

    Properties getProperties(String fileName);

    String getProperty(String property);

    String getPropertiesFileName();
}
