package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.FranchiseRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.FranchiseService;
import co.accenture.pruebatecnica.domain.model.Franchise;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/rest/franchises")
@Validated
public class FranchiseController {
    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @PostMapping
    public Mono<ResponseEntity<Response<Franchise>>> create(@Valid @RequestBody FranchiseRequest franchise) {
        return franchiseService.create(franchise.getName())
                .map(createdFranchise -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.CREATED);
                    resp.setData(List.of(createdFranchise));
                    resp.setMessage("Franchise created successfully");
                    return ResponseEntity
                            .created(URI.create("/v1/rest/franchises/" + createdFranchise.getId()))
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.BAD_REQUEST);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .badRequest()
                            .body(resp));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Response<Franchise>>> listAll() {
        return franchiseService.listAll()
                .collectList()
                .map(franchises -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(franchises);
                    resp.setMessage("Franchises retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(resp));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response<Franchise>>> getById(@PathVariable UUID id) {
        return franchiseService.getById(id)
                .map(franchise -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(franchise));
                    resp.setMessage("Franchise retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response<Franchise>>> delete(@PathVariable UUID id) {
        return franchiseService.delete(id)
                .then(Mono.fromCallable(() -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setMessage("Franchise deleted successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                }))
                .onErrorResume(e -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @PutMapping("name/{id}")
    public Mono<ResponseEntity<Response<Franchise>>> updateName(@PathVariable UUID id, @Valid @RequestBody FranchiseRequest franchise) {
        return franchiseService.updateName(id, franchise.getName())
                .map(updatedFranchise -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(updatedFranchise));
                    resp.setMessage("Franchise name updated successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Franchise> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }
}
