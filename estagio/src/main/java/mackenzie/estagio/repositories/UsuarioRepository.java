package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
    Usuario findByEmail(String email);
    
}