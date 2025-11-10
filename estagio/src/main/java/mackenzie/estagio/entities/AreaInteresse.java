package mackenzie.estagio.entities;

import jakarta.persistence.*;

@Entity
public class AreaInteresse {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String titulo;
    
    private String descricao;
    
    // Construtores
    public AreaInteresse() {}
    
    public AreaInteresse(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
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
}