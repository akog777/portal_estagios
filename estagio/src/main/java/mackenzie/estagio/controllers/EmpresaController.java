package mackenzie.estagio.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import mackenzie.estagio.entities.Empresa;
import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.repositories.EmpresaRepository;
import mackenzie.estagio.repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        empresaRepository.findAll().forEach(empresas::add);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Empresa não encontrada com id: " + id));
        return ResponseEntity.ok(empresa);
    }
    
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Empresa> getEmpresaByCnpj(@PathVariable String cnpj) {
        Empresa empresa = empresaRepository.findByCnpj(cnpj);
        if (empresa == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Empresa não encontrada com CNPJ: " + cnpj);
        }
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
        // Validar CNPJ duplicado
        if (empresaRepository.findByCnpj(empresa.getCnpj()) != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "CNPJ já cadastrado");
        }
        
        // Validar email duplicado
        if (empresa.getUsuario() != null && 
            usuarioRepository.existsByEmail(empresa.getUsuario().getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        
        // Salvar usuário primeiro
        if (empresa.getUsuario() != null) {
            empresa.getUsuario().setTipo(Usuario.TipoUsuario.EMPRESA);
            usuarioRepository.save(empresa.getUsuario());
        }
        
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(
            @PathVariable Long id, 
            @RequestBody Empresa empresaAtualizada) {
        
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Empresa não encontrada com id: " + id));
        
        empresa.setNome(empresaAtualizada.getNome());
        empresa.setTelefone(empresaAtualizada.getTelefone());
        empresa.setEndereco(empresaAtualizada.getEndereco());
        empresa.setAreasAtuacao(empresaAtualizada.getAreasAtuacao());
        
        Empresa updated = empresaRepository.save(empresa);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Empresa não encontrada com id: " + id);
        }
        empresaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}