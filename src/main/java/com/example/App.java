package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Minimal Java HTTP server that returns a Hello World HTML page.
 * For demo use only (not for production traffic).
 */
public class App {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", (HttpExchange exchange) -> {
            String response = "<!doctype html>"
                + "<html lang='en'><head>"
                + "<meta charset='utf-8'>"
                + "<meta http-equiv='Content-Security-Policy' content=\"default-src 'self'; frame-ancestors 'none'; base-uri 'none'\">"
                + "<meta name='viewport' content='width=device-width, initial-scale=1'>"
                + "<title>Hello World</title>"
                + "<style>body { font-family: system-ui, -apple-system, 'Segoe UI', Roboto, Ubuntu, Cantarell, 'Helvetica Neue', Arial; padding: 2rem; }"
                + ".card { max-width: 560px; margin: 5rem auto; padding: 2rem; border-radius: 16px; box-shadow: 0 6px 24px rgba(0,0,0,0.1); }"
                + "h1 { margin-top: 0; } small { color: #666; }</style>"
                + "</head><body><div class='card'>"
                + "<h1>Hello, World! 👋</h1>"
                + "<p>This Java app is running inside a container and provisioned via Terraform (Docker provider).</p>"
                + "<small>DevSecOps demo • Minimal attack surface • CSP enabled</small>"
                + "</div></body></html>";
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
        System.out.println("Server listening on http://localhost:" + port);
    }
}
