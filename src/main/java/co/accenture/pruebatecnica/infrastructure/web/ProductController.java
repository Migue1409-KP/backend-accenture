package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.ProductRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.ProductService;
import co.accenture.pruebatecnica.domain.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/rest/products")
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<ResponseEntity<Response<Product>>> create(@Valid @RequestBody ProductRequest product) {
        return productService.create(product.getName(), product.getAmount(), product.getBranchId())
                .map(createdProduct -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.CREATED);
                    resp.setData(List.of(createdProduct));
                    resp.setMessage("Product created successfully");
                    return ResponseEntity
                            .created(URI.create("/v1/rest/products/" + createdProduct.getId()))
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.BAD_REQUEST);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .badRequest()
                            .body(resp));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Response<Product>>> listAll() {
        return productService.listAll()
                .collectList()
                .map(products -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(products);
                    resp.setMessage("Products retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(resp));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response<Product>>> getById(@PathVariable UUID id) {
        return productService.getById(id)
                .map(product -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(product));
                    resp.setMessage("Product retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @GetMapping("/branch/{branchId}")
    public Mono<ResponseEntity<Response<Product>>> getByBranchId(@PathVariable UUID branchId) {
        return productService.listByBranchId(branchId)
                .collectList()
                .map(products -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(products);
                    resp.setMessage("Products retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(resp));
                });
    }

    @GetMapping("/top-stock/franchise/{franchiseId}")
    public Flux<Product> topStockByFranchise(@PathVariable UUID franchiseId) {
        return productService.findTopByFranchise(franchiseId);
    }

    @PutMapping("amount/{id}")
    public Mono<ResponseEntity<Response<Product>>> updateAmount(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        return productService.updateAmount(id, productRequest.getAmount())
                .map(updatedProduct -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(updatedProduct));
                    resp.setMessage("Product amount updated successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @PutMapping("name/{id}")
    public Mono<ResponseEntity<Response<Product>>> updateName(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        return productService.updateName(id, productRequest.getName())
                .map(updatedProduct -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(updatedProduct));
                    resp.setMessage("Product name updated successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response<Product>>> delete(@PathVariable UUID id) {
        return productService.delete(id)
                .then(Mono.fromCallable(() -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setMessage("Product deleted successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                }))
                .onErrorResume(e -> {
                    Response<Product> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }
}
