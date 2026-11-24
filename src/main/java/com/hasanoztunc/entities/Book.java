package com.hasanoztunc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "book",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"name", "author"},
                        name = "uq_name_author"
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;
}
