package co.accenture.pruebatecnica.domain.usecase;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.domain.usecase.branch.UpdateBranchNameUseCase;
import co.accenture.pruebatecnica.domain.usecase.franchise.UpdateFranchiseNameUseCase;
import co.accenture.pruebatecnica.domain.usecase.product.UpdateProductNameUseCase;
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
class UpdateNameUseCaseTests {

    @Mock
    IFranchiseRepository franchiseRepo;
    @Mock
    IBranchRepository branchRepo;
    @Mock
    IProductRepository productRepo;

    @InjectMocks
    UpdateFranchiseNameUseCase updateFranchiseName;
    @InjectMocks
    UpdateBranchNameUseCase updateBranchName;
    @InjectMocks
    UpdateProductNameUseCase updateProductName;

    @Test
    void updateFranchiseName_success() {
        UUID id = UUID.randomUUID();
        Franchise old = new Franchise(id, "Old");
        when(franchiseRepo.findById(id)).thenReturn(Mono.just(old));
        when(franchiseRepo.save(old)).thenReturn(Mono.just(old));

        StepVerifier.create(updateFranchiseName.execute(id, "New"))
                .expectNextMatches(f -> f.getName().equals("New"))
                .verifyComplete();
    }

    @Test
    void updateBranchName_notFound() {
        UUID id = UUID.randomUUID();
        when(branchRepo.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(updateBranchName.execute(id, "X"))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void updateProductName_success() {
        UUID id = UUID.randomUUID();
        Branch b = new Branch(UUID.randomUUID(), "B", null);
        Product old = new Product(id, "OldP", 10, b);
        when(productRepo.findById(id)).thenReturn(Mono.just(old));
        when(productRepo.save(old)).thenReturn(Mono.just(old));

        StepVerifier.create(updateProductName.execute(id, "NewP"))
                .expectNextMatches(p -> p.getName().equals("NewP"))
                .verifyComplete();
    }
}
