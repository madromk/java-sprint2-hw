package test;

import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TypeOfTask;
import org.junit.jupiter.api.*;
import server.HttpTaskServer;
import server.KVServer;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {

    static KVServer kvServer;
    static HttpTaskServer httpTaskServer;
    static Task task1;
    static Task task2;
    static Epic epic1;
    static SubTask subTask1;


    @BeforeAll
    public static void prepareForTest() {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();

        task1 = new Task("Имя таски1", "Описание таски1", InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        task1.setStartDate(LocalDateTime.of(2022, 3, 17, 00, 00));
        task1.setDuration(Duration.ofDays(2));

        task2 = new Task("Имя таски2", "Описание таски2", InMemoryTaskManager.STATUS_NEW, TypeOfTask.TASK);
        task2.setStartDate(LocalDateTime.of(2022, 5, 22, 00, 00));
        task2.setDuration(Duration.ofDays(1));

        epic1 = new Epic("Имя эпика1", "Описание эпика1", TypeOfTask.EPIC);

        subTask1 = new SubTask("Имя сабтаски1", "Описание сабтаски1", InMemoryTaskManager.STATUS_NEW, TypeOfTask.SUBTASK);
        subTask1.setStartDate(LocalDateTime.of(2022, 6, 2, 00, 00));
        subTask1.setDuration(Duration.ofDays(8));
        subTask1.setBelongEpicId(3);
    }

    @BeforeEach
    public void checkMethodPost() {
        try {
            URI url = URI.create("http://localhost:8080/tasks/task/");
            Gson gson = new Gson();
            String json = gson.toJson(task1);
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            URI url2 = URI.create("http://localhost:8080/tasks/task/");
            Gson gson2 = new Gson();
            String json2 = gson2.toJson(task2);
            final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json2);
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();
            HttpClient client2 = HttpClient.newHttpClient();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

            URI url3 = URI.create("http://localhost:8080/tasks/epic/");
            Gson gson3 = new Gson();
            String json3 = gson3.toJson(epic1);
            final HttpRequest.BodyPublisher body3 = HttpRequest.BodyPublishers.ofString(json3);
            HttpRequest request3 = HttpRequest.newBuilder().uri(url3).POST(body3).build();
            HttpClient client3 = HttpClient.newHttpClient();
            HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());

            URI url4 = URI.create("http://localhost:8080/tasks/subtask/");
            Gson gson4 = new Gson();
            String json4 = gson4.toJson(subTask1);
            final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json4);
            HttpRequest request4 = HttpRequest.newBuilder().uri(url4).POST(body4).build();
            HttpClient client4 = HttpClient.newHttpClient();
            HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось отправить метод POST c задачей");
            e.printStackTrace();
        }
    }

    @Test
    public void checkMethodGet() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Task getTask = gson.fromJson(response.body(), Task.class);
            //Здесь присвоим задачи id, поскольку присвоение id происходит на сервере
            task1.setId(1);
            assertEquals(task1, getTask, "Задачи не совпадают");
        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось обработать метод GET");
            e.printStackTrace();
        }
    }

    @Test
    public void checkMethodDelete() {
        try {
            //Отправляем запрос на удаление задачи по id
            HttpClient client = HttpClient.newHttpClient();
            URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
            HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось отправить метод DELETE \n" + e.getMessage());
        }

        try {
            //Проверяем, что задача удалена. Отправляем GET запрос и проверяем что полученный объект = null
            HttpClient client2 = HttpClient.newHttpClient();
            URI url2 = URI.create("http://localhost:8080/tasks/task/?id=1");
            HttpRequest request2 = HttpRequest.newBuilder().uri(url2).GET().build();
            HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Task getTask = gson.fromJson(response2.body(), Task.class);
            assertNull(getTask);
        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось получить пустую задачу(null) \n" + e.getMessage());
        }
    }

    @Test
    public void checkMethodGetHistory() {
        try {
            HttpClient client61 = HttpClient.newHttpClient();
            URI url61 = URI.create("http://localhost:8080/tasks/history");
            HttpRequest request61 = HttpRequest.newBuilder().uri(url61).GET().build();
            HttpResponse<String> response61 = client61.send(request61, HttpResponse.BodyHandlers.ofString());
            Gson gson61 = new Gson();
            List history = gson61.fromJson(response61.body(), List.class);
            //Выведем историю на экран
            System.out.println(history);
        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось обработать метод GET");
            e.printStackTrace();
        }
    }

    @Test
    public void checkManagerSave() {
        //Здесь остановили старый сервер и запустили новый. В конструктор передали ключ от состояния старого менеджера
        httpTaskServer.stopServer();
        String key = "defaultKey";
        HttpTaskServer httpTaskServer2 = new HttpTaskServer(key);
        try {
            HttpClient client01 = HttpClient.newHttpClient();
            URI url01 = URI.create("http://localhost:8080/tasks/task/?id=2");
            HttpRequest request01 = HttpRequest.newBuilder().uri(url01).GET().build();
            HttpResponse<String> response01 = client01.send(request01, HttpResponse.BodyHandlers.ofString());
            Gson gson01 = new Gson();
            Task getTask01 = gson01.fromJson(response01.body(), Task.class);
            System.out.println(getTask01);
            task2.setId(2);
            assertEquals(task2, getTask01, "Задачи не совпадают");
        } catch (IOException | InterruptedException e) {
            System.out.println("Неудалось восстановить старое состояние менеджера");
            e.printStackTrace();
        }

    }

}