package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class ListProductsByBranchIdUseCase {
    private final IBranchRepository branchRepository;
    private final IProductRepository productRepository;

    public ListProductsByBranchIdUseCase(IBranchRepository branchRepository, IProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    public Flux<Product> execute(UUID branchId) {
        return branchRepository.findById(branchId)
                .flatMapMany(franchise -> productRepository.findAllByBranchId(branchId))
                .switchIfEmpty(Flux.error(new NotFoundException("Branch not found with id: " + branchId)));
    }
}
