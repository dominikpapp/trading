package at.fhtw.bif3.http;

public enum Header {
    AUTHORIZATION("Authorization");

    private final String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
