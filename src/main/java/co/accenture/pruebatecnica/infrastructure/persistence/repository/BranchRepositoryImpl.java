package co.accenture.pruebatecnica.infrastructure.persistence.repository;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.repository.IBranchRepository;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.BranchEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete.IBranchMapperEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class BranchRepositoryImpl implements IBranchRepository {

    private final R2dbcEntityTemplate template;
    private final IBranchMapperEntity mapper;

    public BranchRepositoryImpl(R2dbcEntityTemplate template, IBranchMapperEntity mapper) {
        this.template = template;
        this.mapper = mapper;
    }


    @Override
    public Mono<Branch> findById(UUID id) {
        return template
                .select(BranchEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .one()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Branch> findAll() {
        return template
                .select(BranchEntity.class)
                .matching(Query.empty().sort(Sort.by("name")))
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(UUID franchiseId) {
        return template
                .select(BranchEntity.class)
                .matching(Query.query(Criteria.where("franchise_id").is(franchiseId)).sort(Sort.by("name")))
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = mapper.toEntity(branch);
        return template
                .select(BranchEntity.class)
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
                .delete(BranchEntity.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .all()
                .then();
    }
}
