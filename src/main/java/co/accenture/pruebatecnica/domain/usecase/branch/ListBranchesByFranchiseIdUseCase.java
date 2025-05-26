package co.accenture.pruebatecnica.domain.usecase.branch;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class ListBranchesByFranchiseIdUseCase {
    private final IBranchRepository branchRepository;
    private final IFranchiseRepository franchiseRepository;

    public ListBranchesByFranchiseIdUseCase(IBranchRepository branchRepository, IFranchiseRepository franchiseRepository) {
        this.branchRepository = branchRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public Flux<Branch> execute(UUID franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .flatMapMany(franchise -> branchRepository.findAllByFranchiseId(franchiseId))
                .switchIfEmpty(Flux.error(new NotFoundException("Franchise not found with id: " + franchiseId)));
    }
}
