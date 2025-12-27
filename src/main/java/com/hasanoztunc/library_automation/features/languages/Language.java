package com.hasanoztunc.library_automation.features.languages;

import com.hasanoztunc.library_automation.features.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();
}
