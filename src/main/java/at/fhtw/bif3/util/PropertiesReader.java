package at.fhtw.bif3.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader implements IPropertiesReader {
    private String prefix;

    public PropertiesReader() {
    }

    public PropertiesReader(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Properties getProperties() {
        return this.getProperties(this.getPropertiesFileName());
    }

    @Override
    public Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = ApplicationPropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                LOGGER.severe(fileName + " not found on classpath");
                throw new FileNotFoundException();
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public String getProperty(String property) {
        return this.getProperties().getProperty(property);
    }

    @Override
    public String getPropertiesFileName() {
        return this.prefix + PROPERTIES_FILE_ENDING;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PropertiesReader{");
        sb.append("prefix='").append(prefix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
