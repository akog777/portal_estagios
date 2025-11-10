package mackenzie.estagio.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    
    // Construtores
    public Empresa() {}
    
    public Empresa(String nome, String cnpj, String telefone, String endereco, Usuario usuario) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.endereco = endereco;
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
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<AreaInteresse> getAreasAtuacao() {
        return areasAtuacao;
    }
    
    public void setAreasAtuacao(List<AreaInteresse> areasAtuacao) {
        this.areasAtuacao = areasAtuacao;
    }
    
    public List<VagaEstagio> getVagas() {
        return vagas;
    }
    
    public void setVagas(List<VagaEstagio> vagas) {
        this.vagas = vagas;
    }
    
    // Método auxiliar para obter email
    public String getEmail() {
        return usuario != null ? usuario.getEmail() : null;
    }
}