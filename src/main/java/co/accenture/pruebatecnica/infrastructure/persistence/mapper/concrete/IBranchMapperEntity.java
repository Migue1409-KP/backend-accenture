package co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Franchise;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.BranchEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.MapperEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IBranchMapperEntity extends MapperEntity<BranchEntity, Branch> {
    @Mapping(source = "franchise", target = "franchise", qualifiedByName = "uuidToFranchise")
    Branch toDomain(BranchEntity entity);

    @Mapping(source = "franchise.id", target = "franchise")
    BranchEntity toEntity(Branch domain);

    /** Convierte un UUID en un objeto Franchise sólo con el id. */
    @Named("uuidToFranchise")
    default Franchise uuidToFranchise(UUID id) {
        return new Franchise(id, null);
    }

    /** Extrae el id de un Franchise para persistirlo. */
    @Named("franchiseToUuid")
    default UUID franchiseToUuid(Franchise franchise) {
        return franchise.getId();
    }
}
