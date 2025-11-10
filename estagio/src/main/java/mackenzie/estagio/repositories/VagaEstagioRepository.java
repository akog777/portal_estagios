package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;

import br.mack.estagios.model.Empresa;
import br.mack.estagios.model.VagaEstagio;

public interface VagaEstagioRepository extends CrudRepository<VagaEstagio, Long>{

}
