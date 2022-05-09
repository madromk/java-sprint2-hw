package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.TypeOfTask;
import tasks.BaseTask;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskServer {
    private HttpServer httpServer;
    private static final int PORT = 8080;

    public HttpTaskServer() {
        try {
            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            HTTPTaskManager httpTaskManager = new HTTPTaskManager(KVServer.getUrl());
            httpServer.createContext("/tasks", new TasksHandler(httpTaskManager));
            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        } catch (IOException e) {
            System.out.println("Неудаётся запустить сервер");
            throw new RuntimeException(e);
        }
    }
    //Конструктор для загрузки состояния с сервера
    public HttpTaskServer(String key) {
        try {
            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(PORT), 0);
            HTTPTaskManager httpTaskManager = new HTTPTaskManager(KVServer.getUrl()).load(key);
            httpServer.createContext("/tasks", new TasksHandler(httpTaskManager));
            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        } catch (IOException e) {
            System.out.println("Неудаётся запустить сервер");
            throw new RuntimeException(e);
        }
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public void stopServer() {
        httpServer.stop(1);
    }

    static class TasksHandler implements HttpHandler {

        HTTPTaskManager httpTaskManager;


        public TasksHandler(HTTPTaskManager httpTaskManager) {
            this.httpTaskManager = httpTaskManager;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            int responseCode = 200;
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String uri = httpExchange.getRequestURI().toString();
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "GET":
                    System.out.println("Началась обработка метода GET");
                    response = methodGet(uri);
                    break;
                case "POST":
                    System.out.println("Началась обработка метода POST");
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes());
                    try {
                        methodPost(body);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "DELETE":
                    System.out.println("Началась обработка метода DELETE");
                    methodDelete(path);
                    break;
                default:
                    System.out.println("Ошибка в вызове метода или в обработке ответа.");
                    responseCode = 404;
            }
            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        public String methodGet(String uri) {
            if (uri.contains("/task/")) {
                return treatmentTask(uri);
            } else if (uri.contains("/epic/")) {
                return treatmentEpic(uri);
            } else if (uri.contains("/subtask/")) {
                return treatmentSubtask(uri);
            } else if (uri.contains("/history")) {
                System.out.println("Получение истории...");
                Gson gson = new Gson();
                List<BaseTask> history = httpTaskManager.history();
                List<String> historyStr = new ArrayList<>();
                for(BaseTask task : history) {
                    historyStr.add(task.toString());
                }
                return gson.toJson(historyStr);
            } else {
                System.out.println("Неудалось обработать метод GET");
                return "";
            }
        }

        public void methodDelete(String path) {
            if (path.contains("/task/")) {
                removeTask(path);
            } else if (path.contains("/epic/")) {
                removeEpic(path);
            } else if (path.contains("/subtask/")) {
                removeSubTask(path);
            } else {
                System.out.println("Неудалось обработать метод DELETE");
            }
        }

        public void methodPost(String body) throws IOException, InterruptedException {
            Gson gson = new Gson();
            BaseTask baseTask = gson.fromJson(body, BaseTask.class);
            TypeOfTask type = baseTask.getType();
            if (type.equals(TypeOfTask.TASK)) {
                System.out.println("Тип задачи определен как TASK");
                saveTask(body);
            } else if (type.equals(TypeOfTask.EPIC)) {
                System.out.println("Тип задачи определен как EPIC");
                saveEpic(body);
            } else if (type.equals(TypeOfTask.SUBTASK)) {
                System.out.println("Тип задачи определен как SUBTASK");
                saveSubTask(body);
            } else {
                System.out.println("Ошибка в методе POST. Тип задачи неопределён.");
            }
        }

        public String treatmentTask(String uri) {
            if (uri.endsWith("/task/")) {
                System.out.println("Получение всех задач типа task");
                Gson gson = new Gson();
                return gson.toJson(httpTaskManager.allTasksHashMap());
            } else if (uri.contains("id")) {
                int id = getIdFromPath(uri);
                System.out.println("Началась обработка запроса задачи с id=" + id);
                if(httpTaskManager.allTasksHashMap().containsKey(id)) {
                    Gson gson = new Gson();
                    return gson.toJson(httpTaskManager.getTaskOnIdAndSaveInHistory(id));
                } else {
                    return new Gson().toJson(null);
                }
            } else {
                return "Объект task не обработан";
            }
        }

        public String treatmentEpic(String path) {
            System.out.println(path);
            if (path.endsWith("/epic/")) {
                System.out.println("Получение всех задач типа epic");
                Gson gson = new Gson();
                return gson.toJson(httpTaskManager.allEpicsHashMap());
            } else if (path.contains("id")) {
                int id = getIdFromPath(path);
                System.out.println("Началась обработка запроса эпика с id=" + id);
                Gson gson = new Gson();
                return gson.toJson(httpTaskManager.getEpicOnIdAndSaveInHistory(id));
            } else {
                return "Объект epic не обработан";
            }
        }

        public String treatmentSubtask(String path) {
            System.out.println(path);
            if (path.endsWith("/subtask/")) {
                System.out.println("Получение всех задач типа subtask");
                Gson gson = new Gson();
                return gson.toJson(httpTaskManager.allSubTasksHashMap());
            } else if (path.contains("id")) {
                int id = getIdFromPath(path);
                System.out.println("Началась обработка запроса сабтаски с id=" + id);
                Gson gson = new Gson();
                return gson.toJson(httpTaskManager.getSubTaskOnIdAndSaveInHistory(id));
            } else {
                return "Объект subtask не обработан";
            }
        }

        public int getIdFromPath(String path) {
            String[] ArrayPath = path.split("/");
            String lastElement = ArrayPath[ArrayPath.length - 1].substring(4);
            return Integer.parseInt(lastElement);
        }

        public void removeTask(String path) {
            if (path.endsWith("/task/")) {
                httpTaskManager.removeAllTask();
            } else if (path.contains("id")) {
                int id = getIdFromPath(path);
                httpTaskManager.removeTask(id);
            } else {
                System.out.println("Задачи, которые Вы пытаетесь удалить - отсутствуют");
            }
        }

        public void removeEpic(String path) {
            if (path.endsWith("/epic/")) {
                httpTaskManager.removeAllEpic();
            } else if (path.contains("id")) {
                int id = getIdFromPath(path);
                httpTaskManager.removeEpic(id);
            } else {
                System.out.println("Задачи, которые Вы пытаетесь удалить - отсутствуют");
            }
        }

        public void removeSubTask(String path) {
            if (path.endsWith("/subtask/")) {
                httpTaskManager.removeAllSubTask();
            } else if (path.contains("id")) {
                int id = getIdFromPath(path);
                httpTaskManager.removeSubTaskById(id);
            } else {
                System.out.println("Задачи, которые Вы пытаетесь удалить - отсутствуют");
            }
        }

        public void saveTask(String body) {
            System.out.println("Началось чтение данных из тела...");
            Gson gson = new Gson();
            Task task = gson.fromJson(body, Task.class);
            if (httpTaskManager.allTasksHashMap().containsKey(task.getId())) {
                httpTaskManager.updateTask(task.getId(), task);
                System.out.println("Задач обновлена.");
            } else {
                httpTaskManager.setTask(task, task.getStartDate(), task.getDuration());
                System.out.println("Задач сохранена.");
            }
        }

        public void saveEpic(String body) {
            Gson gson = new Gson();
            Epic epic = gson.fromJson(body, Epic.class);
            if (httpTaskManager.allEpicsHashMap().containsKey(epic.getId())) {
                httpTaskManager.updateEpic(epic.getId(), epic);
            } else {
                httpTaskManager.setEpic(epic);
            }
        }

        public void saveSubTask(String body) {
            Gson gson = new Gson();
            SubTask subTask = gson.fromJson(body, SubTask.class);
            int idEpic = subTask.getBelongEpicId();
            if (httpTaskManager.allEpicsHashMap().containsKey(idEpic)) {
                Epic epic = httpTaskManager.getEpicOnId(idEpic);
                if (epic.getAllSubTask().containsKey(subTask.getId())) {
                    httpTaskManager.updateSubTask(idEpic, subTask.getId(), subTask);
                } else {
                    httpTaskManager.setSubTask(subTask, idEpic, subTask.getStartDate(), subTask.getDuration());
                }
            }
        }

    }
}


