package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.entities.Usuario.TipoUsuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
    Usuario findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    Usuario findByEmailAndTipo(String email, TipoUsuario tipo);
    
}