package at.fhtw.bif3.util;


public class ApplicationPropertiesReader extends PropertiesReader {

    private static final String PREFIX = "application";
    private static final PropertiesReader INSTANCE = new PropertiesReader(PREFIX);

    private ApplicationPropertiesReader() {

    }

    public static PropertiesReader getINSTANCE() {
        return ApplicationPropertiesReader.INSTANCE;
    }
}