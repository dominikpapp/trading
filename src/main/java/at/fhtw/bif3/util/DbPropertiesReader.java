package at.fhtw.bif3.util;

public class DbPropertiesReader extends PropertiesReader {

    private static final String PREFIX = "db";
    private static final PropertiesReader INSTANCE = new PropertiesReader(PREFIX);

    private DbPropertiesReader() {

    }

    public static PropertiesReader getINSTANCE() {
        return DbPropertiesReader.INSTANCE;
    }
}
