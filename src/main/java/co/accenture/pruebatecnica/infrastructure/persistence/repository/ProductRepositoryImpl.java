package co.accenture.pruebatecnica.infrastructure.persistence.repository;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.domain.repository.IProductRepository;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.BranchEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.FranchiseEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.ProductEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete.IProductMapperEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    private final R2dbcEntityTemplate template;
    private final IProductMapperEntity mapper;

    public ProductRepositoryImpl(R2dbcEntityTemplate template, IProductMapperEntity mapper) {
        this.template = template;
        this.mapper = mapper;
    }


    @Override
    public Mono<Product> findById(UUID id) {
        return template
                .select(ProductEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findAll() {
        return template
                .select(ProductEntity.class)
                .matching(Query.empty().sort(Sort.by("name")))
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findAllByBranchId(UUID branchId) {
        return template
                .select(ProductEntity.class)
                .matching(Query.query(Criteria.where("branch_id").is(branchId)).sort(Sort.by("name")))
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        return template
                .select(ProductEntity.class)
                .matching(Query.query(Criteria.where("id").is(entity.getId())))
                .exists()
                .flatMap(exists -> {
                    if (exists) {
                        return template
                                .update(entity)
                                .map(mapper::toDomain);
                    } else {
                        return template
                                .insert(entity)
                                .map(mapper::toDomain);
                    }
                });
    }

    @Override
    public Flux<Product> findTopProductByFranchise(UUID franchiseId) {
        return template
                .select(BranchEntity.class)
                .matching(Query.query(Criteria.where("franchise_id").is(franchiseId)))
                .all()
                .flatMap(branchEntity ->
                        template
                                .select(ProductEntity.class)
                                .matching(Query.query(Criteria.where("branch_id").is(branchEntity.getId()))
                                        .sort(Sort.by(Sort.Direction.DESC, "amount"))
                                        .limit(1)
                                )
                                .all()
                                .next()
                                .map(pe -> {
                                    Product product = mapper.toDomain(pe);
                                    product.setBranch(new Branch(
                                            branchEntity.getId(),
                                            branchEntity.getName(),
                                            new Franchise(branchEntity.getFranchise(), null)
                                    ));
                                    return product;
                                })
                );
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return template
                .delete(ProductEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .all()
                .then();
    }
}
