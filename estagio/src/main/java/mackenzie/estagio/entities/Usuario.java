package mackenzie.estagio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha; // Deve ser criptografada no service com BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    // Enum para tipo de usuário
    public enum TipoUsuario {
        ADMINISTRADOR,
        EMPRESA,
        ESTUDANTE
    }
}
