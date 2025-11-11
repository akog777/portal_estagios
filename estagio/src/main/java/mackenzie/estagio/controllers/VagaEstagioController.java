package mackenzie.estagio.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import mackenzie.estagio.entities.*;
import mackenzie.estagio.repositories.*;

@RestController
@RequestMapping("/api/vagas")
@CrossOrigin(origins = "*")
public class VagaEstagioController {
    
    @Autowired
    private VagaEstagioRepository vagaRepository;

    @GetMapping
    public ResponseEntity<List<VagaEstagio>> getAllVagas() {
        List<VagaEstagio> vagas = new ArrayList<>();
        vagaRepository.findAll().forEach(vagas::add);
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/abertas")
    public ResponseEntity<List<VagaEstagio>> getVagasAbertas() {
        List<VagaEstagio> vagas = vagaRepository.findByEncerradaFalse();
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<VagaEstagio>> getVagasByEmpresa(@PathVariable Long empresaId) {
        List<VagaEstagio> vagas = vagaRepository.findByEmpresaId(empresaId);
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/empresa/{empresaId}/abertas")
    public ResponseEntity<List<VagaEstagio>> getVagasAbertasByEmpresa(@PathVariable Long empresaId) {
        List<VagaEstagio> vagas = vagaRepository.findByEmpresaIdAndEncerradaFalse(empresaId);
        return ResponseEntity.ok(vagas);
    }
    
    @PostMapping("/buscar-por-areas")
    public ResponseEntity<List<VagaEstagio>> getVagasByAreasInteresse(
            @RequestBody List<AreaInteresse> areas) {
        List<VagaEstagio> vagas = vagaRepository.findByAreasInteresseInAndEncerradaFalse(areas);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaEstagio> getVagaById(@PathVariable Long id) {
        VagaEstagio vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada com id: " + id));
        return ResponseEntity.ok(vaga);
    }

    @PostMapping
    public ResponseEntity<VagaEstagio> createVaga(@RequestBody VagaEstagio vaga) {
        if (vaga.getEmpresa() == null || vaga.getEmpresa().getId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Empresa é obrigatória");
        }
        
        vaga.setEncerrada(false);
        VagaEstagio savedVaga = vagaRepository.save(vaga);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVaga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VagaEstagio> updateVaga(
            @PathVariable Long id, 
            @RequestBody VagaEstagio vagaAtualizada) {
        
        VagaEstagio vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada com id: " + id));
        
        vaga.setTitulo(vagaAtualizada.getTitulo());
        vaga.setDescricao(vagaAtualizada.getDescricao());
        vaga.setLocalizacao(vagaAtualizada.getLocalizacao());
        vaga.setModalidade(vagaAtualizada.getModalidade());
        vaga.setCargaHoraria(vagaAtualizada.getCargaHoraria());
        vaga.setRequisitos(vagaAtualizada.getRequisitos());
        vaga.setAreasInteresse(vagaAtualizada.getAreasInteresse());
        vaga.setDataInicio(vagaAtualizada.getDataInicio());
        vaga.setDataFim(vagaAtualizada.getDataFim());
        
        VagaEstagio updated = vagaRepository.save(vaga);
        return ResponseEntity.ok(updated);
    }
    
    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<VagaEstagio> encerrarVaga(@PathVariable Long id) {
        VagaEstagio vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada com id: " + id));
        
        vaga.setEncerrada(true);
        VagaEstagio updated = vagaRepository.save(vaga);
        return ResponseEntity.ok(updated);
    }
    
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<VagaEstagio> reabrirVaga(@PathVariable Long id) {
        VagaEstagio vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada com id: " + id));
        
        vaga.setEncerrada(false);
        VagaEstagio updated = vagaRepository.save(vaga);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaga(@PathVariable Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vaga não encontrada com id: " + id);
        }
        vagaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}