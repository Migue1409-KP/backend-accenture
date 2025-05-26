package co.accenture.pruebatecnica.domain.usecase.franchise;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class GetFranchiseByIdUseCase {
    private final IFranchiseRepository franchiseRepository;

    public GetFranchiseByIdUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(UUID id) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")));
    }
}
