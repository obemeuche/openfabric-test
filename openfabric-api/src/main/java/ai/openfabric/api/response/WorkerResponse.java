package ai.openfabric.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class WorkerResponse {
    private String statusCode;
    private String msg;
    private String status;
    private String port;
    private LocalDateTime createdDate;
}
