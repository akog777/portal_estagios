package mackenzie.estagio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String curso;

    private String telefone;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "estudante_area_interesse",
        joinColumns = @JoinColumn(name = "estudante_id"),
        inverseJoinColumns = @JoinColumn(name = "area_interesse_id")
    )
    private List<AreaInteresse> areasInteresse = new ArrayList<>();

    @OneToMany(mappedBy = "estudante")
    private List<Inscricao> inscricoes = new ArrayList<>();

    // Método auxiliar para obter email
    public String getEmail() {
        return usuario != null ? usuario.getEmail() : null;
    }
}
