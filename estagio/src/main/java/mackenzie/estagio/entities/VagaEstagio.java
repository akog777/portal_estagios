package mackenzie.estagio.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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
    
    // Construtores
    public VagaEstagio() {
        this.dataCriacao = new Date();
    }
    
    public VagaEstagio(String titulo, String descricao, String localizacao, 
                       Modalidade modalidade, Integer cargaHoraria, 
                       String requisitos, Empresa empresa) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.modalidade = modalidade;
        this.cargaHoraria = cargaHoraria;
        this.requisitos = requisitos;
        this.empresa = empresa;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public Modalidade getModalidade() {
        return modalidade;
    }
    
    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
    
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }
    
    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
    public String getRequisitos() {
        return requisitos;
    }
    
    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }
    
    public Boolean getEncerrada() {
        return encerrada;
    }
    
    public void setEncerrada(Boolean encerrada) {
        this.encerrada = encerrada;
    }
    
    public Date getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public Date getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public List<AreaInteresse> getAreasInteresse() {
        return areasInteresse;
    }
    
    public void setAreasInteresse(List<AreaInteresse> areasInteresse) {
        this.areasInteresse = areasInteresse;
    }
    
    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }
    
    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }
    
    // Enum para modalidade
    public enum Modalidade {
        REMOTO,
        PRESENCIAL,
        HIBRIDO
    }
}