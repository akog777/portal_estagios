package mackenzie.estagio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        // Criptografar senha antes de salvar
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    public boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return passwordEncoder.matches(senhaDigitada, senhaCriptografada);
    }
}
