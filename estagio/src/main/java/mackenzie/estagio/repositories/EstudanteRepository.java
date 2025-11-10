package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Estudante;

public interface EstudanteRepository extends CrudRepository<Estudante, Long> {
    
    Estudante findByCpf(String cpf);
    
    Estudante findByUsuarioEmail(String email);
    
}