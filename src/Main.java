import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.*;
import server.HTTPTaskManager;
import server.HttpTaskServer;
import server.KVServer;
import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.JsonArray; // описывает JSON-массив
import com.google.gson.JsonElement; // описывает любой тип данных JSON
import com.google.gson.JsonObject; // описывает JSON-объект
import com.google.gson.JsonParser; // разбирает JSON-документ на элементы


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        //Создаем задачи для тестов
        Task task1 = new Task("Имя таски1", "Описание таски1",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        task1.setStartDate(LocalDateTime.of(2022, 3, 17, 00, 00));
        task1.setDuration(Duration.ofDays(2));

        Task task2 = new Task("Имя таски2", "Описание таски2",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        task2.setStartDate(LocalDateTime.of(2022, 5, 22, 00, 00));
        task2.setDuration(Duration.ofDays(1));

        Epic epic1 = new Epic("Имя эпика1", "Описание эпика1",TypeOfTask.EPIC);
        SubTask subTask1 = new SubTask("Имя сабтаски1", "Описание сабтаски1",
                InMemoryTaskManager.STATUS_NEW, TypeOfTask.SUBTASK);
        subTask1.setBelongEpicId(3);
        subTask1.setStartDate(LocalDateTime.of(2022, 6, 2, 00, 00));
        subTask1.setDuration(Duration.ofDays(8));

        //Отправляем задачу1 на сервер
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();
        String json = gson.toJson(task1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Отправляем задачу2 на сервер
        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(task2);
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(body1).build();
        HttpClient client1 = HttpClient.newHttpClient();
        HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

        //Отправляем эпик на сервер
        URI url31 = URI.create("http://localhost:8080/tasks/epic/");
        Gson gson31 = new Gson();
        String json31 = gson31.toJson(epic1);
        final HttpRequest.BodyPublisher body31 = HttpRequest.BodyPublishers.ofString(json31);
        HttpRequest request31 = HttpRequest.newBuilder().uri(url31).POST(body31).build();
        HttpClient client31 = HttpClient.newHttpClient();
        HttpResponse<String> response31 = client31.send(request31, HttpResponse.BodyHandlers.ofString());

        //Отправляем сабтаск на сервер
        URI url41 = URI.create("http://localhost:8080/tasks/subtask/");
        Gson gson41 = new Gson();
        String json41 = gson41.toJson(subTask1);
        final HttpRequest.BodyPublisher body41 = HttpRequest.BodyPublishers.ofString(json41);
        HttpRequest request41 = HttpRequest.newBuilder().uri(url41).POST(body41).build();
        HttpClient client41 = HttpClient.newHttpClient();
        HttpResponse<String> response41 = client41.send(request41, HttpResponse.BodyHandlers.ofString());

        //Получаем задачу1 с сервера
        HttpClient client2 = HttpClient.newHttpClient();
        URI url2 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();
        HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
        Gson gson2 = new Gson();
        Task getTask = gson2.fromJson(response2.body(), Task.class);
        System.out.println(getTask);
        System.out.println();

        //Получаем задачу2 с сервера
        HttpClient client3 = HttpClient.newHttpClient();
        URI url3 = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url3).GET().build();
        HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
        Gson gson3 = new Gson();
        Task getTask3 = gson3.fromJson(response3.body(), Task.class);
        System.out.println(getTask3);
        System.out.println();

//        Получаем эпик с сервера
        HttpClient client4 = HttpClient.newHttpClient();
        URI url4 = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest request4 = HttpRequest.newBuilder().uri(url4).GET().build();
        HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());
        Gson gson4 = new Gson();
        Epic getTask4 = gson4.fromJson(response4.body(), Epic.class);
        System.out.println(getTask4);
        System.out.println();

        //Получаем сабтаску с сервера
//        HttpClient client5 = HttpClient.newHttpClient();
//        URI url5 = URI.create("http://localhost:8080/tasks/subtask/?id=4");
//        HttpRequest request5 = HttpRequest.newBuilder().uri(url5).GET().build();
//        HttpResponse<String> response5 = client5.send(request5, HttpResponse.BodyHandlers.ofString());
//        Gson gson5 = new Gson();
//        SubTask getTask5 = gson5.fromJson(response5.body(), SubTask.class);
//        System.out.println("******SUBTASK******");
//        System.out.println(getTask5);
//        System.out.println();

        //Удаляем задачу1 с сервера. Ожидаем что полученное значение по id = null
        HttpClient client10 = HttpClient.newHttpClient();
        URI url10 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request10 = HttpRequest.newBuilder().uri(url10).DELETE().build();
        HttpResponse<String> response10 = client10.send(request10, HttpResponse.BodyHandlers.ofString());

        HttpClient client21 = HttpClient.newHttpClient();
        URI url21 = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request21 = HttpRequest.newBuilder().uri(url21).GET().build();
        HttpResponse<String> response21 = client21.send(request21, HttpResponse.BodyHandlers.ofString());
        Gson gson21 = new Gson();
        Task getTask21 = gson21.fromJson(response21.body(), Task.class);
        System.out.println(getTask21);
        System.out.println();

        //Получаем историю
        HttpClient client61 = HttpClient.newHttpClient();
        URI url61 = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request61 = HttpRequest.newBuilder().uri(url61).GET().build();
        HttpResponse<String> response61 = client61.send(request61, HttpResponse.BodyHandlers.ofString());
        Gson gson61 = new Gson();
        List history = gson61.fromJson(response61.body(), List.class);
        System.out.println();
        System.out.println("********История********");
        System.out.println(history);
        System.out.println();


        //Останавливаем старый сервер и запускаем новый
        httpTaskServer.stopServer();
        System.out.println("Сервер остановлен");
        String key = "defaultKey";

        HttpTaskServer httpTaskServer2 = new HttpTaskServer(key);

        HttpClient client01 = HttpClient.newHttpClient();
        URI url01 = URI.create("http://localhost:8080/tasks/task/?id=2");
        HttpRequest request01 = HttpRequest.newBuilder().uri(url01).GET().build();
        HttpResponse<String> response01 = client01.send(request01, HttpResponse.BodyHandlers.ofString());
        Gson gson01 = new Gson();
        Task getTask01 = gson01.fromJson(response01.body(), Task.class);
        System.out.println(getTask01);
        System.out.println();



    }
}
