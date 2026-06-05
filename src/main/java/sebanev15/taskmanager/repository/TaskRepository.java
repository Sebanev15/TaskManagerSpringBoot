package sebanev15.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sebanev15.taskmanager.dto.TaskResponseDto;
import sebanev15.taskmanager.model.Task;
import sebanev15.taskmanager.model.TaskPriority;
import sebanev15.taskmanager.model.TaskStatus;
import sebanev15.taskmanager.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
     List<Task> findByUser(User user);
     List<Task> findByUserAndStatus(User user, TaskStatus status);

     @Query("SELECT t FROM Task t WHERE t.user = :user " +
             "AND (:status IS NULL OR t.status = :status) " +
             "AND (:priority IS NULL OR t.priority = :priority)")
     Page<Task> findByUserWithFilters(
             @Param("user") User user,
             @Param("status") TaskStatus status,
             @Param("priority") TaskPriority priority,
             Pageable pageable
     );
}
