package co.accenture.pruebatecnica.domain.usecase;

import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.domain.usecase.product.GetTopProductsFranchiseUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTopProductsByFranchiseUseCaseTest {

    @Mock
    IProductRepository productRepo;
    @InjectMocks
    GetTopProductsFranchiseUseCase useCase;

    @Test
    void execute_returnsFluxOfTopByBranch() {
        UUID fid = UUID.randomUUID();
        Product p1 = new Product(UUID.randomUUID(), "P1", 5, null);
        Product p2 = new Product(UUID.randomUUID(), "P2", 8, null);
        when(productRepo.findTopProductByFranchise(fid)).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(useCase.execute(fid))
                .expectNext(p1, p2)
                .verifyComplete();
    }
}
