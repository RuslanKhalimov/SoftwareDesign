package mvc.controller;

import mvc.dao.TodoDao;
import mvc.model.Todo;
import mvc.model.TodoList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoListController {

    private final TodoDao todoDao;

    public TodoListController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @RequestMapping(value = "/add-todo-list", method = RequestMethod.POST)
    public String addTodoList(@ModelAttribute("new_todo_list") TodoList todoList) {
        todoDao.addTodoList(todoList);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/remove-todo-list", method = RequestMethod.POST)
    public String removeTodoList(@RequestParam("list_id") int listId) {
        todoDao.removeTodoList(listId);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(@ModelAttribute("todo") Todo todo,
                          @RequestParam("list_id") int listId) {
        todoDao.addTodo(listId, todo);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/change-todo-status", method = RequestMethod.POST)
    public String changeTodoStatus(@RequestParam("todo_id") int todoId) {
        todoDao.changeStatus(todoId);
        return "redirect:/show-all";
    }

    @RequestMapping(value = "/show-all", method = RequestMethod.GET)
    public String showTodoLists(Model model) {
        prepareModel(model);
        return "index";
    }

    private void prepareModel(Model model) {
        model.addAttribute("todo_lists", todoDao.getTodoLists());
        model.addAttribute("new_todo_list", new TodoList());
        model.addAttribute("todo_list", new TodoList());
        model.addAttribute("todo", new Todo());
    }

}
