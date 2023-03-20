//package ai.openfabric.api.service;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.command.CreateContainerResponse;
//import com.github.dockerjava.api.model.AuthConfig;
//import com.github.dockerjava.api.model.Container;
//import com.github.dockerjava.core.DefaultDockerClientConfig;
//import com.github.dockerjava.core.DockerClientBuilder;
//import com.github.dockerjava.core.DockerClientConfig;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DockerApp {
//
//    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//            .withDockerHost("unix:///var/run/docker.sock")
//            .build();
//    DockerClient docker = DockerClientBuilder.getInstance(config).build();
//
//    // Authenticate with Docker Hub
//    AuthConfig authConfig = AuthConfig.builder()
//            .username("obemeuche")
//            .password("UdochiObeme@95")
//            .build();
//        docker.auth(authConfig);
//
//    // List all containers
//    List<Container> containers = docker.listContainersCmd().exec();
////        System.out.println("Containers: " + containers);
//
//    // Create a container from an image
//    String imageName = "hello-world";
//    CreateContainerResponse containerResponse = docker.createContainerCmd(imageName).exec();
//    String containerId = containerResponse.getId();
//
//    // Start the container
//        docker.startContainerCmd(containerId).exec();
//
//}
