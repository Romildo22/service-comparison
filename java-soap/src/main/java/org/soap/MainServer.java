package org.soap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

public class MainServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users", new UsersHandler());
        server.createContext("/musics", new MusicsHandler());
        server.createContext("/userPlaylists", new UserPlaylistsHandler());
        server.createContext("/playlistMusics", new PlaylistMusicsHandler());
        server.createContext("/musicPlaylists", new MusicPlaylistsHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    static abstract class BaseHandler implements HttpHandler {
        public abstract void handleRequest(HttpExchange exchange) throws IOException;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                handleRequest(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                exchange.close();
            }
        }
    }

    static class UsersHandler extends BaseHandler {
        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            SoapData soapData = JsonXmlConverter.readSoapData();
            String responseBody = JsonXmlConverter.convertToXml(soapData.getUsers());
            sendResponse(exchange, responseBody);
        }
    }

    static class MusicsHandler extends BaseHandler {
        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            SoapData soapData = JsonXmlConverter.readSoapData();
            String responseBody = JsonXmlConverter.convertToXml(soapData.getMusics());
            sendResponse(exchange, responseBody);
        }
    }

    static class UserPlaylistsHandler extends BaseHandler {
        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            String userId = getQueryParameter(exchange, "userId");
            SoapData soapData = JsonXmlConverter.readSoapData();
            String responseBody = JsonXmlConverter.convertToXml(
                    soapData.getPlaylists().stream()
                            .filter(playlist -> playlist.getUserId().equals(userId))
                            .collect(Collectors.toList())
            );
            sendResponse(exchange, responseBody);
        }
    }

    static class PlaylistMusicsHandler extends BaseHandler {
        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            String playlistId = getQueryParameter(exchange, "playlistId");
            SoapData soapData = JsonXmlConverter.readSoapData();
            String responseBody = JsonXmlConverter.convertToXml(
                    soapData.getMusics().stream()
                            .filter(music -> music.getPlaylistId().equals(playlistId))
                            .collect(Collectors.toList())
            );
            sendResponse(exchange, responseBody);
        }
    }

    static class MusicPlaylistsHandler extends BaseHandler {
        @Override
        public void handleRequest(HttpExchange exchange) throws IOException {
            String musicId = getQueryParameter(exchange, "musicId");
            SoapData soapData = JsonXmlConverter.readSoapData();
            String responseBody = JsonXmlConverter.convertToXml(
                    soapData.getPlaylists().stream()
                            .filter(playlist -> playlist.getId().equals(musicId))
                            .collect(Collectors.toList())
            );
            sendResponse(exchange, responseBody);
        }
    }

    private static String getQueryParameter(HttpExchange exchange, String paramName) {
        if (exchange.getRequestURI().getQuery() == null) return "";
        return java.net.URLDecoder.decode(exchange.getRequestURI().getQuery().split(paramName + "=")[1].split("&")[0], java.nio.charset.StandardCharsets.UTF_8);
    }

    private static void sendResponse(HttpExchange exchange, String responseBody) throws IOException {
        exchange.sendResponseHeaders(200, responseBody.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();
    }
}
