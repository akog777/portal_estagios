package mackenzie.estagio.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import mackenzie.estagio.entities.Administrador;
import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.repositories.AdministradorRepository;
import mackenzie.estagio.repositories.UsuarioRepository;
import mackenzie.estagio.repositories.EmpresaRepository;
import mackenzie.estagio.repositories.EstudanteRepository;
import mackenzie.estagio.repositories.VagaEstagioRepository;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*")
public class AdministradorController {
    
    @Autowired
    private AdministradorRepository adminRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private EstudanteRepository estudanteRepository;
    
    @Autowired
    private VagaEstagioRepository vagaRepository;

    @GetMapping
    public ResponseEntity<List<Administrador>> getAllAdministradores() {
        List<Administrador> admins = new ArrayList<>();
        adminRepository.findAll().forEach(admins::add);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> getAdministradorById(@PathVariable Long id) {
        Administrador admin = adminRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Administrador não encontrado com id: " + id));
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public ResponseEntity<Administrador> createAdministrador(@RequestBody Administrador admin) {
        // Validar email duplicado
        if (admin.getUsuario() != null && 
            usuarioRepository.existsByEmail(admin.getUsuario().getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        
        // Salvar usuário primeiro
        if (admin.getUsuario() != null) {
            admin.getUsuario().setTipo(Usuario.TipoUsuario.ADMINISTRADOR);
            usuarioRepository.save(admin.getUsuario());
        }
        
        Administrador savedAdmin = adminRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> updateAdministrador(
            @PathVariable Long id, 
            @RequestBody Administrador adminAtualizado) {
        
        Administrador admin = adminRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Administrador não encontrado com id: " + id));
        
        admin.setNome(adminAtualizado.getNome());
        
        Administrador updated = adminRepository.save(admin);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrador(@PathVariable Long id) {
        if (!adminRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Administrador não encontrado com id: " + id);
        }
        adminRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Dashboard - Estatísticas
    @GetMapping("/dashboard/estatisticas")
    public ResponseEntity<Map<String, Object>> getEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        // Contar empresas
        long totalEmpresas = 0;
        for (Object obj : empresaRepository.findAll()) {
            totalEmpresas++;
        }
        
        // Contar estudantes
        long totalEstudantes = 0;
        for (Object obj : estudanteRepository.findAll()) {
            totalEstudantes++;
        }
        
        // Contar vagas abertas e encerradas
        Long vagasAbertas = vagaRepository.countByEncerradaFalse();
        Long vagasEncerradas = vagaRepository.countByEncerradaTrue();
        
        stats.put("totalEmpresas", totalEmpresas);
        stats.put("totalEstudantes", totalEstudantes);
        stats.put("vagasAbertas", vagasAbertas != null ? vagasAbertas : 0);
        stats.put("vagasEncerradas", vagasEncerradas != null ? vagasEncerradas : 0);
        
        return ResponseEntity.ok(stats);
    }
}