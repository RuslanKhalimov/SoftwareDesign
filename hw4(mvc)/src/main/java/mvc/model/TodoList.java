package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

    private int id;
    private String name;
    private List<Todo> todoList;

    public TodoList() {
        todoList = new ArrayList<>();
    }

    public TodoList(String name) {
        this();
        setName(name);
    }

    public TodoList(int id, String name, List<Todo> todoList) {
        this.id = id;
        this.name = name;
        this.todoList = todoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

}
