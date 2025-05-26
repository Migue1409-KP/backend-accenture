package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class GetProductByIdUseCase {
    private final IProductRepository repository;

    public GetProductByIdUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Product> execute(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }
}
