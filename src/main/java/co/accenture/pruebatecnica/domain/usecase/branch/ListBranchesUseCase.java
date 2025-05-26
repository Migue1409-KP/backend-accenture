package co.accenture.pruebatecnica.domain.usecase.branch;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ListBranchesUseCase {
    private final IBranchRepository repository;

    public ListBranchesUseCase(IBranchRepository repository) {
        this.repository = repository;
    }

    public Flux<Branch> execute() {
        return repository.findAll();
    }
}
