package co.accenture.pruebatecnica.domain.usecase.franchise;

import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class DeleteFranchiseUseCase {
    private final IFranchiseRepository repository;

    public DeleteFranchiseUseCase(IFranchiseRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> execute(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(franchise -> repository.deleteById(id));
    }
}
