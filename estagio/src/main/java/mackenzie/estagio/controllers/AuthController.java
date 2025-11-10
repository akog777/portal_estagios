package mackenzie.estagio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.entities.Administrador;
import mackenzie.estagio.entities.Empresa;
import mackenzie.estagio.entities.Estudante;
import mackenzie.estagio.repositories.UsuarioRepository;
import mackenzie.estagio.repositories.AdministradorRepository;
import mackenzie.estagio.repositories.EmpresaRepository;
import mackenzie.estagio.repositories.EstudanteRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AdministradorRepository adminRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private EstudanteRepository estudanteRepository;
    
    // Classe auxiliar para receber dados de login
    public static class LoginRequest {
        private String email;
        private String senha;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        // Buscar usuário por email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuario == null) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }
        
        // NOTA: Em produção, usar BCrypt para comparar senhas!
        // Exemplo: if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha()))
        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }
        
        // Buscar dados específicos do perfil
        Map<String, Object> response = new HashMap<>();
        response.put("tipo", usuario.getTipo());
        response.put("email", usuario.getEmail());
        response.put("usuarioId", usuario.getId());
        
        switch (usuario.getTipo()) {
            case ADMINISTRADOR:
                Administrador admin = adminRepository.findByUsuarioEmail(usuario.getEmail());
                if (admin != null) {
                    response.put("perfilId", admin.getId());
                    response.put("nome", admin.getNome());
                }
                break;
                
            case EMPRESA:
                Empresa empresa = empresaRepository.findByUsuarioEmail(usuario.getEmail());
                if (empresa != null) {
                    response.put("perfilId", empresa.getId());
                    response.put("nome", empresa.getNome());
                    response.put("cnpj", empresa.getCnpj());
                }
                break;
                
            case ESTUDANTE:
                Estudante estudante = estudanteRepository.findByUsuarioEmail(usuario.getEmail());
                if (estudante != null) {
                    response.put("perfilId", estudante.getId());
                    response.put("nome", estudante.getNome());
                    response.put("curso", estudante.getCurso());
                }
                break;
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verificar-email")
    public ResponseEntity<Map<String, Boolean>> verificarEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean existe = usuarioRepository.existsByEmail(email);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }
}