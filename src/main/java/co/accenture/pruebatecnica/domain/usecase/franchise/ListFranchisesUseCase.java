package co.accenture.pruebatecnica.domain.usecase.franchise;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ListFranchisesUseCase {
    private final IFranchiseRepository franchiseRepository;

    public ListFranchisesUseCase(IFranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Flux<Franchise> execute() {
        return franchiseRepository.findAll();
    }
}
