package mackenzie.estagio.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mackenzie.estagio.entities.Inscricao;

public interface InscricaoRepository extends CrudRepository<Inscricao, Long> {
    
    List<Inscricao> findByVagaEstagioId(Long vagaId);
    
    List<Inscricao> findByEstudanteId(Long estudanteId);
    
    boolean existsByEstudanteIdAndVagaEstagioId(Long estudanteId, Long vagaId);
    
}