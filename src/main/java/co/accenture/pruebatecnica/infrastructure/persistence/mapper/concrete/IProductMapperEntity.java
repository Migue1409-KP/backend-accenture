package co.accenture.pruebatecnica.infrastructure.persistence.mapper.concrete;

import co.accenture.pruebatecnica.domain.model.Branch;
import co.accenture.pruebatecnica.domain.model.Product;
import co.accenture.pruebatecnica.infrastructure.persistence.entity.ProductEntity;
import co.accenture.pruebatecnica.infrastructure.persistence.mapper.MapperEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IProductMapperEntity extends MapperEntity<ProductEntity, Product> {

    @Mapping(source = "branch", target = "branch", qualifiedByName = "uuidToBranch")
    Product toDomain(ProductEntity entity);

    @Mapping(source = "branch.id", target = "branch")
    ProductEntity toEntity(Product domain);

    /** Convierte un UUID (columna branch) en un objeto Branch con sólo el id. */
    @Named("uuidToBranch")
    default Branch uuidToBranch(UUID id) {
        return new Branch(id, null, null);
    }

    /** Extrae el id de la Branch de dominio para persistir en la columna branch. */
    @Named("branchToUuid")
    default UUID branchToUuid(Branch branch) {
        return branch.getId();
    }
}
