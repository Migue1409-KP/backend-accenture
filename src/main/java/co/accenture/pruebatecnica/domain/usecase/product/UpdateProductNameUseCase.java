package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UpdateProductNameUseCase {
    private final IProductRepository repository;

    public UpdateProductNameUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Product> execute(UUID id, String newName) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                .flatMap(f -> {
                    f.setName(newName);
                    return repository.save(f);
                });
    }
}
