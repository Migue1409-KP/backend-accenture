package co.accenture.pruebatecnica.infrastructure.persistence.mapper;

public interface MapperEntity<E, D> {
    D toDomain(E entity);
    E toEntity(D domain);
}
