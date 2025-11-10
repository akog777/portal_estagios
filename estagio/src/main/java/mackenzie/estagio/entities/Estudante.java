package mackenzie.estagio.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    
    // Construtores
    public Estudante() {}
    
    public Estudante(String nome, String cpf, String curso, String telefone, Usuario usuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.curso = curso;
        this.telefone = telefone;
        this.usuario = usuario;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getCurso() {
        return curso;
    }
    
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    // Método auxiliar para obter email
    public String getEmail() {
        return usuario != null ? usuario.getEmail() : null;
    }
}