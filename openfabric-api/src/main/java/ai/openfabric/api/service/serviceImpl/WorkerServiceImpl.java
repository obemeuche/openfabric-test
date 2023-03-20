package ai.openfabric.api.service.serviceImpl;

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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    private final DockerManager dockerManager;
    private final WorkerRepository workerRepository;

    @Override
    public Page<Worker> listOfWorkers(WorkerPages workerPages) {

        Sort sort = Sort.by(workerPages.getSortDirection(), workerPages.getSortBy());

        Pageable pageable = PageRequest.of(workerPages.getPageNumber(), workerPages.getPageSize(), sort);

        Page<Worker> allWorkers = Page.empty();
        try {
            allWorkers = workerRepository.findWorkerByPagination(pageable);
        } catch (Exception e) {
            log.debug("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getMessage());
            log.error("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getCause());
        }
        List<Worker> allPaginatedWorkers = new ArrayList<>();

        for(Worker worker: allWorkers) {
            allPaginatedWorkers.add(worker);
        }

        PageImpl<Worker> workerPage = new PageImpl<>(allPaginatedWorkers, pageable, allWorkers.getTotalElements());

        return workerPage;
    }

    @Override
    public WorkerResponse getWorkerInformation(WorkerRequest request) {
        Worker worker = new Worker();
        try {
            worker = workerRepository.findWorkerInfo(request.getName());
        } catch(Exception e) {
            log.debug("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getMessage());
            log.error("UNABLE TO RETRIEVE RECORD FROM THE DATABASE. REASON " + e.getCause());

            WorkerResponse failedWorkerResponse = WorkerResponse
                    .builder()
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

            String[] exposedPorts = {"8080", "8081", "5433"};
            CreateContainerResponse containerResponse = dockerManager.createContainer(imageName, containerName, exposedPorts);

            // Start the worker (container)
            String containerId = containerResponse.getId();
            dockerManager.startContainer(containerId);
            System.out.println("Started worker with Id " + containerId);

            // saving worker information tho the database
            Worker worker = Worker
                    .builder()
                    .workerName(containerName)
                    .imageName(imageName)
                    .port(exposedPorts[1])
                    .imageId(containerId)
                    .workerCreateAt(LocalDateTime.now())
                    .build();

            try {
                workerRepository.save(worker);
            }catch (Exception ex){
                System.out.println("UNABLE TO SAVE INTO THE DATABASE " + ex.getCause());
                throw new DataFormatException("UNABLE TO SAVE INTO THE DATABASE");
            }

        } catch(Exception e){
            System.out.println("AN EXCEPTION OCCURRED WHILE IMPLEMENTING THIS METHOD " + e.getCause());
            throw new GeneralException("AN EXCEPTION OCCURRED WHILE IMPLEMENTING THIS METHOD");
        }

    }


    @Override
    public void stopWorker(String containerId) {
        dockerManager.stopContainer(containerId);
    }

    @Override
    public List<Container> listWorkers() {
       return dockerManager.listContainers();
    }


}
