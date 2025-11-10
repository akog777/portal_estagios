package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;

import br.mack.estagios.model.Empresa;
import br.mack.estagios.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

}
