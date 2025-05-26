package co.accenture.pruebatecnica.infrastructure.web;

import co.accenture.pruebatecnica.application.dto.BranchRequest;
import co.accenture.pruebatecnica.application.dto.Response;
import co.accenture.pruebatecnica.application.service.BranchService;
import co.accenture.pruebatecnica.domain.model.Branch;
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
@RequestMapping("/v1/rest/branches")
@Validated
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public Mono<ResponseEntity<Response<Branch>>> create(@Valid @RequestBody BranchRequest branchRequest) {
        return branchService.create(branchRequest.getName(), branchRequest.getFranchiseId())
                .map(createdBranch -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.CREATED);
                    resp.setData(List.of(createdBranch));
                    resp.setMessage("Branch created successfully");
                    return ResponseEntity
                            .created(URI.create("/v1/rest/branches/" + createdBranch.getId()))
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.BAD_REQUEST);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .badRequest()
                            .body(resp));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Response<Branch>>> listAll() {
        return branchService.listAll()
                .collectList()
                .map(branches -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(branches);
                    resp.setMessage("Branches retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(resp));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response<Branch>>> getById(@PathVariable("id") UUID id) {
        return branchService.getById(id)
                .map(branch -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(branch));
                    resp.setMessage("Branch retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @GetMapping("/franchise/{franchiseId}")
    public Mono<ResponseEntity<Response<Branch>>> listByFranchiseId(@PathVariable("franchiseId") UUID franchiseId) {
        return branchService.listByFranchiseId(franchiseId)
                .collectList()
                .map(branches -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(branches);
                    resp.setMessage("Branches for franchise retrieved successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(resp));
                });
    }

    @PutMapping("name/{id}")
    public Mono<ResponseEntity<Response<Branch>>> updateName(@PathVariable("id") UUID id, @RequestBody BranchRequest branchRequest) {
        return branchService.updateName(id, branchRequest.getName())
                .map(updatedBranch -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setData(List.of(updatedBranch));
                    resp.setMessage("Branch name updated successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                })
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response<Branch>>> delete(@PathVariable("id") UUID id) {
        return branchService.delete(id)
                .then(Mono.fromCallable(() -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.OK);
                    resp.setMessage("Branch deleted successfully");
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(resp);
                }))
                .onErrorResume(e -> {
                    Response<Branch> resp = new Response<>();
                    resp.setStatus(HttpStatus.NOT_FOUND);
                    resp.setMessage(e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(resp));
                });
    }
}
