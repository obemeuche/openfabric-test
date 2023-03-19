package ai.openfabric.api.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DockerManager {

    private static final DockerClient dockerClient = DockerClientBuilder.getInstance().build();

    public void pullImage(String imageName) throws InterruptedException {
        dockerClient.pullImageCmd(imageName).exec(new PullImageResultCallback()).awaitCompletion();
    }

    public static CreateContainerResponse createContainer(String imageName, String containerName, String[] exposedPorts) {
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
        dockerClient.startContainerCmd(containerId).exec();
    }

    public  void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public static List<Container> listContainers() {
        ListContainersCmd listCmd = dockerClient.listContainersCmd();
        return listCmd.exec();
    }
}
