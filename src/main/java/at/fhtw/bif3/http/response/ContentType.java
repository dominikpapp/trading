package at.fhtw.bif3.http.response;


public enum ContentType {
    APPLICATION_JSON("application/json"),
    TEXT_HTML("text/html");

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
