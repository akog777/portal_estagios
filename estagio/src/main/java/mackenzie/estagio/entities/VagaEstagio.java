package mackenzie.estagio.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class VagaEstagio {
    @Id @GeneratedValue
    private Long id;
    private String titulo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private List<AreaInteresse> listAreaInteresse;
    private Empresa empresa;
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
    public List<AreaInteresse> getListAreaInteresse() {
        return listAreaInteresse;
    }
    public void setListAreaInteresse(List<AreaInteresse> listAreaInteresse) {
        this.listAreaInteresse = listAreaInteresse;
    }
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
