package co.accenture.pruebatecnica.infrastructure.persistence.repository;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.domain.repository.IFranchiseRepository;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.FranchiseEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete.IFranchiseMapperEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class FranchiseRepositoryImpl implements IFranchiseRepository {
    private final R2dbcEntityTemplate template;
    private final IFranchiseMapperEntity mapper;

    public FranchiseRepositoryImpl(R2dbcEntityTemplate template, IFranchiseMapperEntity mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public Mono<Franchise> findById(UUID id) {
        return template
                .select(FranchiseEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return template
                .select(FranchiseEntity.class)
                .matching(Query.empty().sort(Sort.by("name")))
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = mapper.toEntity(franchise);
        return template
                .select(FranchiseEntity.class)
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
    public Mono<Void> deleteById(UUID id) {
        return template
                .delete(FranchiseEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .all()
                .then();
    }
}
