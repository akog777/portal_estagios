package mackenzie.estagio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String telefone;

    private String endereco;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "empresa_area_atuacao",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "area_interesse_id")
    )
    private List<AreaInteresse> areasAtuacao = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<VagaEstagio> vagas = new ArrayList<>();

    // Método auxiliar para obter email
    public String getEmail() {
        return usuario != null ? usuario.getEmail() : null;
    }
}
