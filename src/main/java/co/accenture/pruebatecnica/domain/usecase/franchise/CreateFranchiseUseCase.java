package co.accenture.pruebatecnica.domain.usecase.franchise;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateFranchiseUseCase {
    private final IFranchiseRepository repository;

    public CreateFranchiseUseCase(IFranchiseRepository repository) {
        this.repository = repository;
    }

    public Mono<Franchise> execute(Franchise franchise) {
        // TODO: Add rules
        return repository.save(franchise);
    }
}
