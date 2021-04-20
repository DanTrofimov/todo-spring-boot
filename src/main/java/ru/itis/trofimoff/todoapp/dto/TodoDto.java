package ru.itis.trofimoff.todoapp.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.trofimoff.todoapp.models.Todo;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    @NotBlank
    private String todoText;
    private int id = 0;
    private int group = 0;

    public TodoDto(String text) {
        this.todoText = text;
    }

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.todoText = todo.getText();
        this.group = todo.getGroup().getId();
    }

    public TodoDto(String text, Integer id) {
        this.id = id;
        this.todoText = text;
    }
}
