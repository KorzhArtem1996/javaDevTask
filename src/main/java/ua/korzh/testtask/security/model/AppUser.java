package ua.korzh.testtask.security.model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Role> roles;

    public AppUser(String username, String password, Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
