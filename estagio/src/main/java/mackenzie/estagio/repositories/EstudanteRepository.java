package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;

import br.mack.estagios.model.Estudante;

public interface EstudanteRepository extends CrudRepository<Estudante, Long>{

    public Estudante findEstudanteByCPF(String cpf);

}
