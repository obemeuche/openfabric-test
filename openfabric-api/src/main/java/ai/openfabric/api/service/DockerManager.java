package ai.openfabric.api.service;

import ai.openfabric.api.config.DockerConfig;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DockerManager {

    private final DockerConfig config;


    public void pullImage(String imageName) throws InterruptedException {
        DockerClient dockerClient = config.dockerLogIn();
        dockerClient.pullImageCmd(imageName).exec(new PullImageResultCallback());
    }

    public CreateContainerResponse createContainer(String imageName, String containerName, String[] exposedPorts) {

        DockerClient dockerClient = config.dockerLogIn();
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(imageName);

        containerCmd.withName(containerName);

        for (String port : exposedPorts) {
            ExposedPort exposedPort = ExposedPort.tcp(Integer.parseInt(port));
            Ports portBindings = new Ports();
            portBindings.bind(exposedPort, Ports.Binding.empty());
            containerCmd.withExposedPorts(exposedPort);
            containerCmd.withPortBindings(portBindings);
        }

        return containerCmd.exec();
    }

    public void startContainer(String containerId) {
        DockerClient dockerClient = config.dockerLogIn();
        dockerClient.startContainerCmd(containerId).exec();
    }

    public  void stopContainer(String containerId) {
        DockerClient dockerClient = config.dockerLogIn();
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public List<Container> listContainers() {
        DockerClient dockerClient = config.dockerLogIn();
        ListContainersCmd listCmd = dockerClient.listContainersCmd();
        return listCmd.exec();
    }
}
