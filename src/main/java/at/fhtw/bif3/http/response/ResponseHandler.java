package at.fhtw.bif3.http.response;


import at.fhtw.bif3.http.HttpStatusCode;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class ResponseHandler implements Response {

    private final Map<String, String> headers = new HashMap<>();
    private HttpStatusCode status = HttpStatusCode.OK;
    private String content = "";
    private ContentType contentType;
    private String serverHeader = "My MTCG-Server";

    public ResponseHandler() {
    }

    public ResponseHandler(HttpStatusCode status, String content, ContentType contentType, String serverHeader) {
        this.status = status;
        this.content = content;
        this.contentType = contentType;
        this.serverHeader = serverHeader;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getContentLength() {
        return content.length();
    }

    @Override
    public String getContentType() {
        return contentType.getName();
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = Arrays.stream(ContentType.values()).filter(type -> type.getName().equals(contentType)).findFirst().orElseThrow();
    }

    @Override
    public int getStatusCode() {
        return status.getCode();
    }

    @Override
    public void setStatusCode(int status) {
        this.status = Arrays.stream(HttpStatusCode.values()).filter(httpStatus -> httpStatus.getCode() == status).findFirst().orElseThrow();
    }

    @Override
    public String getStatus() {
        return this.status.getMessage();
    }

    @Override
    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    @Override
    public String getServerHeader() {
        return this.serverHeader;
    }

    @Override
    public void setServerHeader(String server) {
        this.serverHeader = server;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setContent(byte[] content) {
        setContent(new ByteArrayInputStream(content));
    }

    @Override
    public void setContent(InputStream stream) {
        this.content = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
    }

    @Override
    public void send(OutputStream network) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("HTTP/1.1 ")
                .append(status.getCode()).append(" ")
                .append(status.getMessage()).append(lineSeparator());

        headers.keySet().forEach(key -> messageBuilder.append(key).append(": ").append(headers.get(key)).append(lineSeparator()));

        messageBuilder.append(lineSeparator());
        messageBuilder.append(content);

        try {
            network.write(messageBuilder.toString().getBytes());
            network.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ResponseHandler{" +
                "status=" + status +
                ", headers=" + headers +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                ", serverHeader='" + serverHeader + '\'' +
                '}';
    }
}
