package co.accenture.pruebatecnica.application.service;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.usecase.branch.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class BranchService {
    private final CreateBranchUseCase createBranchUseCase;
    private final ListBranchesByFranchiseIdUseCase listBranchesByFranchiseIdUseCase;
    private final GetBranchByIdUseCase getBranchByIdUseCase;
    private final ListBranchesUseCase listBranchesUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final DeleteBranchUseCase deleteBranchUseCase;

    public BranchService(CreateBranchUseCase createBranchUseCase,
                        ListBranchesByFranchiseIdUseCase listBranchesByFranchiseIdUseCase,
                        GetBranchByIdUseCase getBranchByIdUseCase,
                        ListBranchesUseCase listBranchesUseCase,
                        DeleteBranchUseCase deleteBranchUseCase,
                        UpdateBranchNameUseCase updateBranchNameUseCase) {
        this.createBranchUseCase = createBranchUseCase;
        this.listBranchesByFranchiseIdUseCase = listBranchesByFranchiseIdUseCase;
        this.getBranchByIdUseCase = getBranchByIdUseCase;
        this.listBranchesUseCase = listBranchesUseCase;
        this.deleteBranchUseCase = deleteBranchUseCase;
        this.updateBranchNameUseCase = updateBranchNameUseCase;
    }

    public Mono<Branch> create(String name, UUID franchiseId) {
        Branch branch = new Branch();
        branch.setId(UUID.randomUUID());
        branch.setName(name);
        Franchise franchise = new Franchise();
        franchise.setId(franchiseId);
        branch.setFranchise(franchise);

        return createBranchUseCase.execute(branch);
    }

    public Mono<Branch> getById(UUID id) {
        return getBranchByIdUseCase.execute(id);
    }

    public Flux<Branch> listByFranchiseId(UUID franchiseId) {
        return listBranchesByFranchiseIdUseCase.execute(franchiseId);
    }

    public Flux<Branch> listAll() {
        return listBranchesUseCase.execute();
    }

    public Mono<Branch> updateName(UUID id, String name) {
        return updateBranchNameUseCase.execute(id, name);
    }

    public Mono<Void> delete(UUID id) {
        return deleteBranchUseCase.execute(id);
    }
}
