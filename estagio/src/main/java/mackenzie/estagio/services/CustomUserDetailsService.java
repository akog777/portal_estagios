package mackenzie.estagio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mackenzie.estagio.entities.Usuario;
import mackenzie.estagio.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getTipo().name())
                .build();
    }
}
