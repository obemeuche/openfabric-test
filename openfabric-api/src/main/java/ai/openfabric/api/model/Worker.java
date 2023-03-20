package ai.openfabric.api.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity()
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "worker")
public class Worker extends Datable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    @Getter
    @Setter
    private String id;

    private String workerName;

    private String imageName;

    private String imageId;

    public String port;

    public String status;

    public LocalDateTime workerCreateAt;

}
