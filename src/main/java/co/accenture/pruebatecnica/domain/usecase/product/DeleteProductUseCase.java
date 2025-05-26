package co.accenture.pruebatecnica.domain.usecase.product;

import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class DeleteProductUseCase {
    private final IProductRepository repository;

    public DeleteProductUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> execute(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                .flatMap(franchise -> repository.deleteById(id));
    }
}
