package co.accenture.pruebatecnica.domain.usecase.branch;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class GetBranchByIdUseCase {
    private final IBranchRepository repository;

    public GetBranchByIdUseCase(IBranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Branch> execute(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found")));
    }
}
