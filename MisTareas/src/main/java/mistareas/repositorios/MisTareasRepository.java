package mistareas.repositorios;

import mistareas.entidades.Task;
import java.util.List;

public interface MisTareasRepository {

    List<Task> getMisTareas();

    Task getMiTarea(Long id);

    int save(Task task);

    int update(Task task);

    int deleteById(Long id);
}
