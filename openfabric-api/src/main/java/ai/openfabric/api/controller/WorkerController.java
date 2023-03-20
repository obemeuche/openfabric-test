package ai.openfabric.api.controller;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.pagination_criteria.WorkerPages;
import ai.openfabric.api.request.WorkerRequest;
import ai.openfabric.api.response.WorkerResponse;
import ai.openfabric.api.service.WorkerService;
import com.github.dockerjava.api.model.Container;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${node.api.path}/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;


    @PostMapping(path = "/hello")
    public @ResponseBody String hello(@RequestBody String name) {
        return "Hello!" + name;
    }

    @GetMapping(path = "/paginated")
    public Page<Worker> listOfPaginatedWorkers(WorkerPages workerPages){
        return workerService.listOfPaginatedWorkers(workerPages);
    }

    @GetMapping(path = "/information")
    public WorkerResponse getWorkerInformation(@RequestBody WorkerRequest request){
        return workerService.getWorkerInformation(request);
    }

    @GetMapping(path = "/statistic")
    public Integer getWorkerStatistics(){
        return workerService.getWorkerStatistics();
    }

    @PostMapping(path ="/start")
    public void startWorker(String imageName, String containerName) throws InterruptedException {
        workerService.startWorker(imageName, containerName);
    }

    @PostMapping(path = "/stop")
    public void stopWorker(String containerId){
        workerService.stopWorker(containerId);
    }

    @PostMapping(path = "/listWorkers")
    public List<Container> listWorkers(){
        return workerService.listWorkers();
    }

}
