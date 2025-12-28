package com.hasanoztunc.library_automation.features.authentication;

import com.hasanoztunc.library_automation.features.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
        }
)
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @NotBlank
    @Size(max = 20)
    @Column(
            name = "username",
            nullable = false
    )
    private String username;

    @NotBlank
    @Size(max = 30)
    @Column(name = "full_name")
    private String fullName;

    @NotBlank
    @Email
    @Size(max = 50)
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "created_at",
            updatable = false
    )
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Member(
            String username,
            String fullName,
            String email,
            String password
    ) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}