package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class GetTopProductsFranchiseUseCase {
    private final IProductRepository repository;

    public GetTopProductsFranchiseUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Flux<Product> execute(UUID franchiseId) {
        return repository.findTopProductByFranchise(franchiseId);
    }
}
