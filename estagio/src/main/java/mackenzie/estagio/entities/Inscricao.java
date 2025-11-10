package mackenzie.estagio.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"estudante_id", "vaga_estagio_id"}))
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
    
    // Construtores
    public Inscricao() {
        this.dataInscricao = new Date();
        this.status = StatusInscricao.PENDENTE;
    }
    
    public Inscricao(Estudante estudante, VagaEstagio vagaEstagio) {
        this();
        this.estudante = estudante;
        this.vagaEstagio = vagaEstagio;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDataInscricao() {
        return dataInscricao;
    }
    
    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
    
    public StatusInscricao getStatus() {
        return status;
    }
    
    public void setStatus(StatusInscricao status) {
        this.status = status;
    }
    
    public Estudante getEstudante() {
        return estudante;
    }
    
    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }
    
    public VagaEstagio getVagaEstagio() {
        return vagaEstagio;
    }
    
    public void setVagaEstagio(VagaEstagio vagaEstagio) {
        this.vagaEstagio = vagaEstagio;
    }
    
    // Enum para status da inscrição
    public enum StatusInscricao {
        PENDENTE,
        EM_ANALISE,
        APROVADA,
        RECUSADA,
        CANCELADA
    }
}