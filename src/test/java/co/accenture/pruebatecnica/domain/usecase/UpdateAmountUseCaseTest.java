package co.accenture.pruebatecnica.domain.usecase;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.domain.usecase.product.UpdateProductAmountUseCase;
import co.accenture.pruebatecnica.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAmountUseCaseTest {

    @Mock
    IProductRepository productRepo;
    @InjectMocks
    UpdateProductAmountUseCase updateAmount;

    @Test
    void execute_success() {
        UUID id = UUID.randomUUID();
        Branch b = new Branch(UUID.randomUUID(), "B", new Franchise(UUID.randomUUID(), null));
        Product old = new Product(id, "P", 3, b);
        when(productRepo.findById(id)).thenReturn(Mono.just(old));
        when(productRepo.save(old)).thenReturn(Mono.just(old));

        StepVerifier.create(updateAmount.execute(id, 10))
                .expectNextMatches(p -> p.getAmount() == 10)
                .verifyComplete();
    }

    @Test
    void execute_whenNotFound_shouldError() {
        UUID id = UUID.randomUUID();
        when(productRepo.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(updateAmount.execute(id, 10))
                .expectError(NotFoundException.class)
                .verify();
    }
}
