package mackenzie.estagio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"estudante_id", "vaga_estagio_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dataInscricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInscricao status;

    @ManyToOne
    @JoinColumn(name = "estudante_id", nullable = false)
    private Estudante estudante;

    @ManyToOne
    @JoinColumn(name = "vaga_estagio_id", nullable = false)
    private VagaEstagio vagaEstagio;

    // Enum para status da inscrição
    public enum StatusInscricao {
        PENDENTE,
        EM_ANALISE,
        APROVADA,
        RECUSADA,
        CANCELADA
    }
}
