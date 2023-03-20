package ai.openfabric.api.service;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.pagination_criteria.WorkerPages;
import ai.openfabric.api.request.WorkerRequest;
import ai.openfabric.api.response.WorkerResponse;
import com.github.dockerjava.api.model.Container;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkerService {

    Page<Worker> listOfPaginatedWorkers(WorkerPages workerPages);

    WorkerResponse getWorkerInformation(WorkerRequest request);

    Integer getWorkerStatistics();

    void startWorker(String imageName, String containerName) throws InterruptedException;

    void stopWorker(String containerId);

    List<Container> listWorkers();
}
