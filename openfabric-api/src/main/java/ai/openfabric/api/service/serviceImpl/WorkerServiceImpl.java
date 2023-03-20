package ai.openfabric.api.service.serviceImpl;

import ai.openfabric.api.exception.DataBaseException;
import ai.openfabric.api.exception.GeneralException;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.pagination_criteria.WorkerPages;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.request.WorkerRequest;
import ai.openfabric.api.response.WorkerResponse;
import ai.openfabric.api.service.DockerManager;
import ai.openfabric.api.service.WorkerService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    private final DockerManager dockerManager;
    private final WorkerRepository workerRepository;

    @Value("${port-1}")
    private String PORT_1;

    @Value("${port-2}")
    private String PORT_2;

    @Value("${port-3}")
    private String PORT_3;

    @Value("${port-4}")
    private String PORT_4;

    @Override
    // paginates all workers on database
    public Page<Worker> listOfPaginatedWorkers(WorkerPages workerPages) {

        Sort sort = Sort.by(workerPages.getSortDirection(), workerPages.getSortBy());

        Pageable pageable = PageRequest.of(workerPages.getPageNumber(), workerPages.getPageSize(), sort);

        Page<Worker> allWorkers = Page.empty();
        try {
            allWorkers = workerRepository.findWorkerByPagination(pageable);
        } catch (Exception e) {
            System.out.println("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getCause());
        }
        List<Worker> allPaginatedWorkers = new ArrayList<>();

        for(Worker worker: allWorkers) {
            allPaginatedWorkers.add(worker);
        }

        PageImpl<Worker> workerPage = new PageImpl<>(allPaginatedWorkers, pageable, allWorkers.getTotalElements());

        return workerPage;
    }

    @Override
    // gets all worker information from database
    public WorkerResponse getWorkerInformation(WorkerRequest request) {
        Worker worker = new Worker();
        try {
            worker = workerRepository.findWorkerInfo(request.getName());
        } catch(Exception e) {
            System.out.println("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getCause());

            WorkerResponse failedWorkerResponse = WorkerResponse
                    .builder()
                    .workerName(request.getName())
                    .msg("FAILED")
                    .statusCode("99")
                    .build();

            return failedWorkerResponse;
        }

        WorkerResponse workerResponse = WorkerResponse
                .builder()
                .statusCode("00")
                .msg("SUCCESSFUL")
                .status(worker.getStatus())
                .port(worker.getPort())
                .createdDate(LocalDateTime.now())
                .build();

        return workerResponse;
    }

    @Override
    // gets worker statistics from database
    public Integer getWorkerStatistics() {
        List<Worker> allWorkers = new ArrayList<>();
        try {
            allWorkers = workerRepository.findAll();
        } catch (Exception e){
            log.debug("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getMessage());
            log.error("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getCause());
        }
        return allWorkers.size();
    }

    @Override
    public void startWorker(String imageName, String containerName) {

        try {
            // Pull Image
            dockerManager.pullImage(imageName);

            String[] exposedPorts = {PORT_1, PORT_2, PORT_3, PORT_4};
            CreateContainerResponse containerResponse = dockerManager.createContainer(imageName, containerName, exposedPorts);

            // Start the worker (container)
            String containerId = containerResponse.getId();
            dockerManager.startContainer(containerId);
            System.out.println("Started worker with Id " + containerId);

        } catch(Exception e){
            System.out.println("AN EXCEPTION OCCURRED WHILE STARTING THE WORKER. REASON: " + e);
            throw new GeneralException("AN EXCEPTION OCCURRED WHILE STARTING THE WORKER");
        }

        // saving worker information tho the database
        Worker worker = Worker
                .builder()
                .workerName(containerName)
                .imageName(imageName)
                .workerCreateAt(LocalDateTime.now())
                .build();

        try {
            workerRepository.save(worker);
        }catch (Exception ex){
            System.out.println("UNABLE TO SAVE INTO THE DATABASE " + ex.getCause());
            throw new DataBaseException("UNABLE TO SAVE INTO THE DATABASE");
        }

    }


    @Override
    public void stopWorker(String containerId) {
        try {
            // method to stop worker
            dockerManager.stopContainer(containerId);
        } catch (Exception e) {
            System.out.println("AN EXCEPTION OCCURRED WHILE STOPPING THE WORKER. REASON " + e);
            throw new GeneralException("AN EXCEPTION OCCURRED WHILE STOPPING THE WORKER");
        }
    }

    @Override
    public List<Container> listWorkers() {
        try {
            // method to list all worker
            return dockerManager.listContainers();
        } catch (Exception e){
            System.out.println("AN EXCEPTION OCCURRED WHILE LISTING THE WORKERS. REASON " + e);
            throw new GeneralException("AN EXCEPTION OCCURRED WHILE LISTING THE WORKERS");
        }
    }


}
