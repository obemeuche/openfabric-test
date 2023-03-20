package ai.openfabric.api.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.stereotype.Component;

@Component
public class DockerConfig {


    public DockerClient dockerLogIn(){

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .withDockerTlsVerify(false)
                .withRegistryUsername("obemeuche")
                .withRegistryPassword("UdochiObeme@95")
                .withRegistryEmail("obemeuchechi@gmail.com")
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        return dockerClient;
    }
}
