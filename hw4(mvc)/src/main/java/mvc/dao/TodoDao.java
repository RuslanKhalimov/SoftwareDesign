package mvc.dao;

import mvc.model.Todo;
import mvc.model.TodoList;

import java.util.List;

public interface TodoDao {

    int addTodo(int listId, Todo todo);

    int addTodoList(TodoList todoList);

    List<TodoList> getTodoLists();

    void removeTodoList(int id);

    void changeStatus(int todoId);

}
