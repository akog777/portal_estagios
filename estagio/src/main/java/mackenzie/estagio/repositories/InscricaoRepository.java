package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;

import br.mack.estagios.model.Empresa;
import br.mack.estagios.model.Inscricao;

public interface InscricaoRepository extends CrudRepository<Inscricao, Long>{

}
