package ai.openfabric.api.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    public String id;

    public String name;

    private String imageId;

    public String port;

    public String status;

    public Date createdDate;

}
