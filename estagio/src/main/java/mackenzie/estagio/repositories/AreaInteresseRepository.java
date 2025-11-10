package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.AreaInteresse;

public interface AreaInteresseRepository extends CrudRepository<AreaInteresse, Long> {
    
    AreaInteresse findByTitulo(String titulo);
    
    boolean existsByTitulo(String titulo);
    
}