package server;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private String url;
    private String API_KEY;
    public KVTaskClient(String urlStr) {
        try {
            this.url = urlStr;
            URI urlRegister = URI.create(urlStr + "/register");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(urlRegister)
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            this.API_KEY = response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка в создание KVTaskClient");
            e.printStackTrace();
        }
    }

    public void put(String key, String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create(getUrl() + "/save/" + key + "?API_KEY=" + getAPI_KEY());
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .header("Accept", "application/json")
                    .POST(body)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка в сохранении состояния менеджера");
        }
    }

    public String load(String key) {
        String body = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create(getUrl() + "/load/" + key + "?API_KEY=" + getAPI_KEY());
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url)
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            body = response.body();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка в загрузки состояния менеджера");
        }
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
