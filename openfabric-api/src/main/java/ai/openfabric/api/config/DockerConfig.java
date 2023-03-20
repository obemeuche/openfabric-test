package ai.openfabric.api.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConfig {

    @Value("${username}")
     String USERNAME;

    @Value("${password")
     String PASSWORD;

    @Value("${email")
     String EMAIL;


    @Bean
    public DockerClient dockerLogIn(){

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .withDockerTlsVerify(false)
                .withRegistryUsername(USERNAME)
                .withRegistryPassword(PASSWORD)
                .withRegistryEmail(EMAIL)
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        return dockerClient;
    }
}
