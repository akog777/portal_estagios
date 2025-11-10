package mackenzie.estagio.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Estudante {
    @Id @GeneratedValue
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private List<AreaInteresse> listAreaInteresse;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<AreaInteresse> getListAreaInteresse() {
        return listAreaInteresse;
    }
    public void setListAreaInteresse(List<AreaInteresse> listAreaInteresse) {
        this.listAreaInteresse = listAreaInteresse;
    }

}
