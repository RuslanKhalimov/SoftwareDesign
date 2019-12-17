package mvc.dao;

import mvc.model.Todo;
import mvc.model.TodoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoInMemoryDao implements TodoDao {

    private final AtomicInteger todoLastId = new AtomicInteger(0);
    private final AtomicInteger todoListLastId = new AtomicInteger(0);
    private final List<TodoList> todoLists = new CopyOnWriteArrayList<>();
    private final Map<Integer, TodoList> todoListById = new ConcurrentHashMap<>();
    private final Map<Integer, Todo> todoById = new ConcurrentHashMap<>();

    @Override
    public int addTodo(int listId, Todo todo) {
        int id = todoLastId.getAndIncrement();
        todo.setId(id);
        todoListById.get(listId).addTodo(todo);
        todoById.put(id, todo);
        return id;
    }

    @Override
    public int addTodoList(TodoList todoList) {
        int id = todoListLastId.getAndIncrement();
        todoList.setId(id);
        todoLists.add(todoList);
        todoListById.put(id, todoList);
        return id;
    }

    @Override
    public List<TodoList> getTodoLists() {
        return new ArrayList<>(todoLists);
    }

    @Override
    public void removeTodoList(int id) {
        todoLists.removeIf(todoList -> todoList.getId() == id);
        todoListById.remove(id);
    }

    @Override
    public void changeStatus(int todoId) {
        todoById.get(todoId).changeStatus();
    }

}
