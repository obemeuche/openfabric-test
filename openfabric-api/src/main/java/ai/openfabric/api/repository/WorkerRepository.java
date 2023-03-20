package ai.openfabric.api.repository;

import ai.openfabric.api.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, String> {

    List<Worker> findAll();

    @Query("SELECT c FROM Worker c WHERE c.workerName = ?1")
    Worker findWorkerInfo(String workerName);

    @Query("SELECT c FROM Worker c")
    Page<Worker> findWorkerByPagination(Pageable pageable);

}
