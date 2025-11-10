package mackenzie.estagio.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Inscricao {
    @Id @GeneratedValue
    private Long id;
    private Date dataInscricao;
    private int status;
    private VagaEstagio vagaEstagio;
    private List<Estudante> estudantes;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public VagaEstagio getVagaEstagio() {
        return vagaEstagio;
    }
    public void setVagaEstagio(VagaEstagio vagaEstagio) {
        this.vagaEstagio = vagaEstagio;
    }
    public List<Estudante> getEstudantes() {
        return estudantes;
    }
    public void setEstudantes(List<Estudante> estudantes) {
        this.estudantes = estudantes;
    }
}
