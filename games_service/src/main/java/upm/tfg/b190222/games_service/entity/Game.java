package upm.tfg.b190222.games_service.entity;

import java.time.LocalDate;
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
@Table(name="Game")
public class Game {
    
    @Id
    @Column(name="nombre")
    private String nombre;

    @Column(name="plataforma")
    private String plataforma;

    @Column(name="desarrolladora")
    private String desarrolladora;

    @Column(name="generos")
    private String generos;

    @Column(name="notaMedia")
    private float notaMedia;

    @Column(name="fechaLanzamiento")
    private LocalDate fechaLanzamiento;

    @Column(name="fechaRegistro")
    private LocalDate fechaRegistro;
}
