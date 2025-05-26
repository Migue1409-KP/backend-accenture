package co.accenture.pruebatecnica.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("branches")
public class BranchEntity {

    @Id
    private UUID id;

    private String name;

    @Column("franchise_id")
    private UUID franchise;
}
