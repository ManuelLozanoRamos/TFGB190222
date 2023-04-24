package upm.tfg.b190222.usuarios_service.entity;

import java.time.LocalDate;
import java.util.BitSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Usuario")
public class Usuario {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @Column(name = "fechaRegistro")
    private LocalDate fechaRegistro;

    @Column(name="activado")
    private boolean activado;
}
