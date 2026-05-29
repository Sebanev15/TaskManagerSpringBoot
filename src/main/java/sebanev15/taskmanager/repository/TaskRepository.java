package sebanev15.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
     List<Task> findByUser(User user);
     List<Task> findByUserAndStatus(User user, TaskStatus status);
}
