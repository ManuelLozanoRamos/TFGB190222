package upm.tfg.b190222.tokens_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Token")
public class Token {

    @Id
    @Column(name="token")
    private String token;

    @Column(name="proceso")
    private String proceso;

    @Column(name="username")
    private String username;

    @Column(name="fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name="fechaValidez")
    private LocalDateTime fechaValidez;
}
