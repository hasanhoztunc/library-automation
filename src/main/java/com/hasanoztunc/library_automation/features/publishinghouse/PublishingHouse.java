package com.hasanoztunc.library_automation.features.publishinghouse;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "publishing_houses")
public class PublishingHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publishingHouseId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;
}