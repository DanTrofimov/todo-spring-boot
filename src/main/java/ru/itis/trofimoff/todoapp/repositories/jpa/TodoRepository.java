package ru.itis.trofimoff.todoapp.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.trofimoff.todoapp.models.Todo;

import java.util.List;

@Transactional
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    // updating todos by users id
    @Modifying
    @Query("UPDATE Todo todo SET todo.text = :text WHERE id = :id")
    void update(@Param("text") String text, @Param("id") int id);

    // deleting todo_ by id
    @Modifying
    @Query(value = "DELETE FROM todos WHERE id = ?1", nativeQuery = true)
    void removeById(int id);

    @Modifying
    @Query(value = "DELETE FROM users_todos WHERE users_id = ?1 and todos_id = ?2", nativeQuery = true)
    void removeUserBinding(int userId, int todoId);

    // saving todo_ into todo_ table, returns filled todo_
    Todo save(Todo todo);

    @Query(value = "SELECT users_todos.users_id, todos.text, todos.id, todos.group_id FROM users_todos JOIN todos ON users_todos.todos_id = todos.id WHERE users_todos.users_id = ?1", nativeQuery = true)
    List<Todo> getUsersTodo(int userId);

    @Query(value = "SELECT users_todos.users_id, todos.text, todos.id, todos.group_id FROM users_todos JOIN todos ON users_todos.todos_id = todos.id WHERE users_todos.users_id = ?1 AND todos.group_id = ?2", nativeQuery = true)
    List<Todo> getUsersTodoByGroup(int userId, int groupId);

    // inserting todo_'s & user's data into users_todo
    @Modifying
    @Query(value = "INSERT INTO users_todos(users_id, todos_id) VALUES(?1, ?2)", nativeQuery = true)
    void insertTodoIntoUsersTodo(int userId, int todoId);

    @Query(value = "SELECT users_todos.users_id, todos.text, todos.id, todos.group_id FROM users_todos JOIN todos ON users_todos.todos_id = todos.id WHERE users_todos.users_id = ?1 limit ?2 offset ?3", nativeQuery = true)
    List<Todo> getUsersTodoWithPagination(int userId, int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM (SELECT users_todos.users_id, todos.text, todos.id, todos.group_id FROM users_todos JOIN todos ON users_todos.todos_id = todos.id WHERE users_todos.users_id = ?1) as count", nativeQuery = true)
    int getUsersTodosAmount(int userId);
}
