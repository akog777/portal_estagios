package mackenzie.estagio.repositories;

import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    
    Empresa findByCnpj(String cnpj);
    
    Empresa findByUsuarioEmail(String email);
    
}