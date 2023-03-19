package ai.openfabric.api.service;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.pagination_criteria.WorkerPages;
import ai.openfabric.api.request.WorkerRequest;
import ai.openfabric.api.response.WorkerResponse;
import org.springframework.data.domain.Page;

public interface WorkerService {

    Page<Worker> listOfWorkers(WorkerPages workerPages);

    WorkerResponse getWorkerInformation(WorkerRequest request);

    Integer getWorkerStatistics();

    void startWorker(String imageName, String containerName) throws InterruptedException;

    void stopWorker(String containerId);
}
