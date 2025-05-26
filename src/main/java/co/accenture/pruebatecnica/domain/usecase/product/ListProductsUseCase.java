package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ListProductsUseCase {
    private final IProductRepository repository;

    public ListProductsUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Flux<Product> execute() {
        return repository.findAll();
    }
}
