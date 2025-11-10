package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Administrador;

public interface AdministradorRepository extends CrudRepository<Administrador, Long> {
    
    Administrador findByUsuarioEmail(String email);
    
}