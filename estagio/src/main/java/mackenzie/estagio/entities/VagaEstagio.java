package mackenzie.estagio.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VagaEstagio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @Column(nullable = false)
    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidade modalidade;

    private Integer cargaHoraria; // horas por semana

    @Column(length = 1000)
    private String requisitos;

    @Column(nullable = false)
    private Boolean encerrada = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
        name = "vaga_area_interesse",
        joinColumns = @JoinColumn(name = "vaga_id"),
        inverseJoinColumns = @JoinColumn(name = "area_interesse_id")
    )
    private List<AreaInteresse> areasInteresse = new ArrayList<>();

    @OneToMany(mappedBy = "vagaEstagio", cascade = CascadeType.ALL)
    private List<Inscricao> inscricoes = new ArrayList<>();

    // Enum para modalidade
    public enum Modalidade {
        REMOTO,
        PRESENCIAL,
        HIBRIDO
    }
}
