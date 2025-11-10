package mackenzie.estagio.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import mackenzie.estagio.entities.Estudante;
import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.repositories.EstudanteRepository;
import mackenzie.estagio.repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/estudantes")
@CrossOrigin(origins = "*")
public class EstudanteController {
    
    @Autowired
    private EstudanteRepository estudanteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Estudante>> getAllEstudantes() {
        List<Estudante> estudantes = new ArrayList<>();
        estudanteRepository.findAll().forEach(estudantes::add);
        return ResponseEntity.ok(estudantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> getEstudanteById(@PathVariable Long id) {
        Estudante estudante = estudanteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Estudante não encontrado com id: " + id));
        return ResponseEntity.ok(estudante);
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Estudante> getEstudanteByCpf(@PathVariable String cpf) {
        Estudante estudante = estudanteRepository.findByCpf(cpf);
        if (estudante == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Estudante não encontrado com CPF: " + cpf);
        }
        return ResponseEntity.ok(estudante);
    }

    @PostMapping
    public ResponseEntity<Estudante> createEstudante(@RequestBody Estudante estudante) {
        // Validar CPF duplicado
        if (estudanteRepository.findByCpf(estudante.getCpf()) != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }
        
        // Validar email duplicado
        if (estudante.getUsuario() != null && 
            usuarioRepository.existsByEmail(estudante.getUsuario().getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        
        // Salvar usuário primeiro
        if (estudante.getUsuario() != null) {
            estudante.getUsuario().setTipo(Usuario.TipoUsuario.ESTUDANTE);
            usuarioRepository.save(estudante.getUsuario());
        }
        
        Estudante savedEstudante = estudanteRepository.save(estudante);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEstudante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> updateEstudante(
            @PathVariable Long id, 
            @RequestBody Estudante estudanteAtualizado) {
        
        Estudante estudante = estudanteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Estudante não encontrado com id: " + id));
        
        estudante.setNome(estudanteAtualizado.getNome());
        estudante.setCurso(estudanteAtualizado.getCurso());
        estudante.setTelefone(estudanteAtualizado.getTelefone());
        estudante.setAreasInteresse(estudanteAtualizado.getAreasInteresse());
        
        Estudante updated = estudanteRepository.save(estudante);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudante(@PathVariable Long id) {
        if (!estudanteRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Estudante não encontrado com id: " + id);
        }
        estudanteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}