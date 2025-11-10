package mackenzie.estagio.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import mackenzie.estagio.entities.Inscricao;
import mackenzie.estagio.entities.VagaEstagio;
import mackenzie.estagio.repositories.InscricaoRepository;
import mackenzie.estagio.repositories.VagaEstagioRepository;

@RestController
@RequestMapping("/api/inscricoes")
@CrossOrigin(origins = "*")
public class InscricaoController {
    
    @Autowired
    private InscricaoRepository inscricaoRepository;
    
    @Autowired
    private VagaEstagioRepository vagaRepository;

    @GetMapping
    public ResponseEntity<List<Inscricao>> getAllInscricoes() {
        List<Inscricao> inscricoes = new ArrayList<>();
        inscricaoRepository.findAll().forEach(inscricoes::add);
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> getInscricaoById(@PathVariable Long id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inscrição não encontrada com id: " + id));
        return ResponseEntity.ok(inscricao);
    }
    
    @GetMapping("/vaga/{vagaId}")
    public ResponseEntity<List<Inscricao>> getInscricoesByVaga(@PathVariable Long vagaId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByVagaEstagioId(vagaId);
        return ResponseEntity.ok(inscricoes);
    }
    
    @GetMapping("/estudante/{estudanteId}")
    public ResponseEntity<List<Inscricao>> getInscricoesByEstudante(@PathVariable Long estudanteId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByEstudanteId(estudanteId);
        return ResponseEntity.ok(inscricoes);
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Inscricao>> getInscricoesByEmpresa(@PathVariable Long empresaId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByVagaEstagioEmpresaId(empresaId);
        return ResponseEntity.ok(inscricoes);
    }

    @PostMapping
    public ResponseEntity<Inscricao> createInscricao(@RequestBody Inscricao inscricao) {
        // Validar se estudante e vaga existem
        if (inscricao.getEstudante() == null || inscricao.getEstudante().getId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Estudante é obrigatório");
        }
        
        if (inscricao.getVagaEstagio() == null || inscricao.getVagaEstagio().getId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Vaga é obrigatória");
        }
        
        // Verificar se vaga está encerrada
        VagaEstagio vaga = vagaRepository.findById(inscricao.getVagaEstagio().getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada"));
        
        if (vaga.getEncerrada()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Vaga está encerrada");
        }
        
        // Verificar duplicidade
        if (inscricaoRepository.existsByEstudanteIdAndVagaEstagioId(
                inscricao.getEstudante().getId(), 
                inscricao.getVagaEstagio().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Estudante já inscrito nesta vaga");
        }
        
        Inscricao savedInscricao = inscricaoRepository.save(inscricao);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInscricao);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Inscricao> updateStatus(
            @PathVariable Long id, 
            @RequestBody Inscricao.StatusInscricao novoStatus) {
        
        Inscricao inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inscrição não encontrada com id: " + id));
        
        inscricao.setStatus(novoStatus);
        Inscricao updated = inscricaoRepository.save(inscricao);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscricao(@PathVariable Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Inscrição não encontrada com id: " + id);
        }
        inscricaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}