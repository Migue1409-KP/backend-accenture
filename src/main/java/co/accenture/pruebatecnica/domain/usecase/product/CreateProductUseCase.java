package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateProductUseCase {
    private final IProductRepository repository;

    public CreateProductUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Product> execute(Product product) {
        // TODO: Add rules
        return repository.save(product);
    }
}
