package ru.itis.trofimoff.todoapp.controllers.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.trofimoff.todoapp.dto.TodoDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.services.todo.TodoService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HandleTodoController {

    @Autowired
    public TodoService todoService;

    @GetMapping(value = "/handle-todo")
    public String getHandleTodo(HttpServletRequest request) {
        return "redirect:/main";
    }

    @PostMapping(value = "/handle-todo")
    public String postHandleTodo(HttpServletRequest request) {
        String text = request.getParameter("change-todo-text");
        int todoId = Integer.parseInt(request.getParameter("todo-id"));
        UserDto currentUser = (UserDto) request.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();
        switch (request.getParameter("todo-action")) {
            case "change":
                if (!text.trim().equals("")) {
                    TodoDto todoDto = new TodoDto(text);
                    todoDto.setId(todoId);
                    todoService.updateTodo(todoDto);
                }
                break;
            case "remove":
                todoService.deleteTodo(todoId, userId);
                break;
            default:
                return "redirect:/main";
        }
        return "redirect:/main";
    }
}
