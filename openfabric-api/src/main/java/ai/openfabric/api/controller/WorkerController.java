package ai.openfabric.api.controller;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.pagination_criteria.WorkerPages;
import ai.openfabric.api.request.WorkerRequest;
import ai.openfabric.api.response.WorkerResponse;
import ai.openfabric.api.service.WorkerService;
import com.github.dockerjava.api.model.Container;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "starts the worker", notes = "takes the imageName and containerName as its request")
    @PostMapping(path ="/start")
    public void startWorker(String imageName, String containerName) throws InterruptedException {
        workerService.startWorker(imageName, containerName);
    }

    @ApiOperation(value = "stops the worker", notes = "takes the imageId as its request")
    @PostMapping(path = "/stop")
    public void stopWorker(String containerId){
        workerService.stopWorker(containerId);
    }

    @ApiOperation(value = "lists all workers saved in the DB", notes = "It is paginated and takes in the no of pages")
    @GetMapping(path = "/paginated")
    public Page<Worker> listOfPaginatedWorkers(WorkerPages workerPages){
        return workerService.listOfPaginatedWorkers(workerPages);
    }

    @ApiOperation(value = "lists all workers in the pulled Image")
    @PostMapping(path = "/listWorkers")
    public List<Container> listWorkers(){
        return workerService.listWorkers();
    }

    @ApiOperation(value = "gets the information of a worker saved in the DB")
    @GetMapping(path = "/information")
    public WorkerResponse getWorkerInformation(@RequestBody WorkerRequest request){
        return workerService.getWorkerInformation(request);
    }

    @ApiOperation(value = "gets the statistics of a workers saved in the DB")
    @GetMapping(path = "/statistic")
    public Integer getWorkerStatistics(){
        return workerService.getWorkerStatistics();
    }

}
