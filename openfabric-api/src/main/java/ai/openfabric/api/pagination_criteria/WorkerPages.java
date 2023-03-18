package ai.openfabric.api.pagination_criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class WorkerPages {
    private int pageNumber = 0;
    private int pageSize = 4;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "createdDate";
}
