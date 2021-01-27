package at.fhtw.bif3.http.url;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlParserTest {

    @Test
    void getRawUrl() {
        Url url = UrlParser.valueOf("POST /packages HTTP/1.1");
        assertEquals("/packages", url.getRawUrl());
    }

    @Test
    void getPath() {
        Url url = UrlParser.valueOf("POST /packages HTTP/1.1");
        assertEquals("/packages", url.getPath());

        url = UrlParser.valueOf("POST /packages?id=1&name=dominik HTTP/1.1");
        assertEquals("/packages", url.getPath());
    }

    @Test
    void getParameter() {
        Url url = UrlParser.valueOf("POST /packages?id=1&name=dominik HTTP/1.1");
        assertEquals(2, url.getParameter().keySet().size());
        assertEquals("1", url.getParameter().get("id"));
        assertEquals("dominik", url.getParameter().get("name"));
    }

    @Test
    void getParameterCount() {
        Url url = UrlParser.valueOf("POST /packages?id=1&name=dominik HTTP/1.1");
        assertEquals(2, url.getParameterCount());

        url = UrlParser.valueOf("POST /packages?id=1 HTTP/1.1");
        assertEquals(1, url.getParameterCount());

        url = UrlParser.valueOf("POST /packages HTTP/1.1");
        assertEquals(0, url.getParameterCount());
    }

    @Test
    void getSegments() {
        Url url = UrlParser.valueOf("POST /packages?id=1&name=dominik HTTP/1.1");
        assertEquals(1, url.getSegments().length);
        assertEquals("packages", url.getSegments()[0]);

        url = UrlParser.valueOf("GET /users/user/1/profile HTTP/1.1");
        assertEquals(4, url.getSegments().length);
        assertTrue(Arrays.asList(url.getSegments()).contains("users"));
        assertTrue(Arrays.asList(url.getSegments()).contains("user"));
        assertTrue(Arrays.asList(url.getSegments()).contains("1"));
        assertTrue(Arrays.asList(url.getSegments()).contains("profile"));
    }

    @Test
    void getFileName() {
        Url url = UrlParser.valueOf("GET /index.html HTTP/1.1");
        assertEquals("index.html", url.getFileName());
    }

    @Test
    void getExtension() {
        Url url = UrlParser.valueOf("GET /index.html HTTP/1.1");
        assertEquals(".html", url.getExtension());
    }

    @Test
    void getFragment() {
        Url url = UrlParser.valueOf("GET /index#second_paragraph HTTP/1.1");
        assertEquals("second_paragraph", url.getFragment());
    }
}