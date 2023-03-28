package upm.tfg.b190222.reviews_service.entity;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Review")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idReview")
    private int idReview;

    @Column(name="username")
    private String username;

    @Column(name="videojuego")
    private String videojuego;

    @Column(name="titulo")
    private String titulo;

    @Column(name="nota")
    private int nota;

    @Column(name="comentario")
    private String comentario;

    @Column(name="fecha")
    private LocalDate fecha;
}
