package mistareas.repositorios;

import mistareas.entidades.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MisTareasRepositoryImpl implements MisTareasRepository {

    private final JdbcTemplate jdbcTemplate;

    public MisTareasRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> getMisTareas() {
        String sql = "SELECT id, description, status FROM task";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Task(
                        resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status")
                )
        );
    }

    @Override
    public Task getMiTarea(Long id) {
        String sql = "SELECT id, description, status FROM task WHERE id = ?";
        List<Task> tareas = jdbcTemplate.query(sql, new Object[]{id}, (resultSet, rowNum) ->
                new Task(
                        resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status")
                )
        );

        return tareas.isEmpty() ? null : tareas.get(0);
    }

    @Override
    public int save(Task task) {
        String sql = "INSERT INTO task (description, status) VALUES (?, ?)";
        // Devuelve el número de filas insertadas (normalmente 1).
        return jdbcTemplate.update(sql, task.getDescription(), task.getStatus());
    }

    @Override
    public int update(Task task) {
        String sql = "UPDATE task SET description = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getDescription(), task.getStatus(), task.getId());
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}