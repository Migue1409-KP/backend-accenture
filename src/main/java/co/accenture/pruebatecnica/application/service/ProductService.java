package co.accenture.pruebatecnica.application.service;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.usecase.product.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProductService {
    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final ListProductsByBranchIdUseCase listProductsByBranchIdUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductAmountUseCase updateProductAmountUseCase;
    private final GetTopProductsFranchiseUseCase getTopProductsFranchiseUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;

    public ProductService(CreateProductUseCase createProductUseCase,
                          GetProductByIdUseCase getProductByIdUseCase,
                          ListProductsUseCase listProductsUseCase,
                          ListProductsByBranchIdUseCase listProductsByBranchIdUseCase,
                          DeleteProductUseCase deleteProductUseCase,
                          UpdateProductAmountUseCase updateProductAmountUseCase,
                          GetTopProductsFranchiseUseCase getTopProductsFranchiseUseCase,
                          UpdateProductNameUseCase updateProductNameUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.listProductsByBranchIdUseCase = listProductsByBranchIdUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductAmountUseCase = updateProductAmountUseCase;
        this.getTopProductsFranchiseUseCase = getTopProductsFranchiseUseCase;
        this.updateProductNameUseCase = updateProductNameUseCase;
    }

    public Mono<Product> create(String name, Integer amount, UUID branchId) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setAmount(amount);
        Branch branch = new Branch();
        branch.setId(branchId);
        product.setBranch(branch);

        return createProductUseCase.execute(product);
    }

    public Mono<Product> getById(UUID id) {
        return getProductByIdUseCase.execute(id);
    }

    public Flux<Product> listByBranchId(UUID branchId) {
        return listProductsByBranchIdUseCase.execute(branchId);
    }

    public Flux<Product> listAll() {
        return listProductsUseCase.execute();
    }

    public Mono<Product> updateAmount(UUID id, Integer amount) {
        return updateProductAmountUseCase.execute(id, amount);
    }

    public Flux<Product> findTopByFranchise(UUID franchiseId) {
        return getTopProductsFranchiseUseCase.execute(franchiseId);
    }

    public Mono<Product> updateName(UUID id, String name) {
        return updateProductNameUseCase.execute(id, name);
    }

    public Mono<Void> delete(UUID id) {
        return deleteProductUseCase.execute(id);
    }
}
