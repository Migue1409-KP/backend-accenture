package co.accenture.pruebatecnica.domain.usecase.branch;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateBranchUseCase {
    private final IBranchRepository repository;

    public CreateBranchUseCase(IBranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Branch> execute(Branch branch){
        // TODO: Add rules
        return repository.save(branch);
    }
}
