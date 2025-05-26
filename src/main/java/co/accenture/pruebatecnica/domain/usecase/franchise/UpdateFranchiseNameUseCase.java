package co.accenture.pruebatecnica.domain.usecase.franchise;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UpdateFranchiseNameUseCase {
    private final IFranchiseRepository repository;

    public UpdateFranchiseNameUseCase(IFranchiseRepository repository) {
        this.repository = repository;
    }

    public Mono<Franchise> execute(UUID id, String newName) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(f -> {
                    f.setName(newName);
                    return repository.save(f);
                });
    }
}
