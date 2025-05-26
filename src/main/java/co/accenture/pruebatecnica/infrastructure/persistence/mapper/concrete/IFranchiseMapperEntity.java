package co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete;

import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.FranchiseEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFranchiseMapperEntity extends MapperEntity<FranchiseEntity, Franchise> {
    Franchise toDomain(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise domain);
}
