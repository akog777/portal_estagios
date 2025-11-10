package mackenzie.estagio.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.VagaEstagio;
import mackenzie.estagio.entities.AreaInteresse;

public interface VagaEstagioRepository extends CrudRepository<VagaEstagio, Long> {
    
    List<VagaEstagio> findByEncerradaFalse();
    
    List<VagaEstagio> findByEmpresaId(Long empresaId);
    
    List<VagaEstagio> findByAreaInteresseInAndEncerradaFalse(List<AreaInteresse> areasInteresse);
    
}