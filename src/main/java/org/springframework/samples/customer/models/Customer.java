package org.springframework.samples.customer.models;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customers")
public class Customer {
    
    @Id
    @Column("id")
    @Min(value = 1)
    @Getter @Setter private Long id;

    @Column("name")
    @NotNull
    @Size(min = 3, max = 255)
    @Getter @Setter private String name;

}
