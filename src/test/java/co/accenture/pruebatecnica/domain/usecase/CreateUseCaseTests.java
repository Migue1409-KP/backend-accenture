package co.accenture.pruebatecnica.domain.usecase;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.domain.usecase.branch.CreateBranchUseCase;
import co.accenture.pruebatecnica.domain.usecase.franchise.CreateFranchiseUseCase;
import co.accenture.pruebatecnica.domain.usecase.product.CreateProductUseCase;
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
class CreateUseCaseTests {

    @Mock
    IFranchiseRepository franchiseRepo;
    @Mock
    IBranchRepository branchRepo;
    @Mock
    IProductRepository productRepo;

    @InjectMocks
    CreateFranchiseUseCase createFranchise;
    @InjectMocks
    CreateBranchUseCase createBranch;
    @InjectMocks
    CreateProductUseCase createProduct;

    @Test
    void createFranchise_returnsSaved() {
        UUID id = UUID.randomUUID();
        Franchise input = new Franchise(id, "F1");
        when(franchiseRepo.save(input)).thenReturn(Mono.just(input));

        StepVerifier.create(createFranchise.execute(input))
                .expectNextMatches(f -> f.getId().equals(id) && f.getName().equals("F1"))
                .verifyComplete();
    }

    @Test
    void createBranch_returnsSaved() {
        UUID bid = UUID.randomUUID(), fid = UUID.randomUUID();
        Branch input = new Branch(bid, "B1", new Franchise(fid, null));
        when(branchRepo.save(input)).thenReturn(Mono.just(input));

        StepVerifier.create(createBranch.execute(input))
                .expectNextMatches(b -> b.getId().equals(bid)
                        && b.getName().equals("B1")
                        && b.getFranchise().getId().equals(fid))
                .verifyComplete();
    }

    @Test
    void createProduct_returnsSaved() {
        UUID pid = UUID.randomUUID(), bid = UUID.randomUUID();
        Branch b = new Branch(bid, "B1", new Franchise(UUID.randomUUID(), null));
        Product input = new Product(pid, "P1", 5, b);
        when(productRepo.save(input)).thenReturn(Mono.just(input));

        StepVerifier.create(createProduct.execute(input))
                .expectNextMatches(p -> p.getId().equals(pid)
                        && p.getName().equals("P1")
                        && p.getAmount()==5
                        && p.getBranch().getId().equals(bid))
                .verifyComplete();
    }
}
