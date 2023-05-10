package upm.tfg.b190222.reviews_service.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewInfo {
     private String titulo;
     private String comentario;
     private Integer nota;

     private String username;
     private String videojuego;
     private String fechaRegIni;
     private String fechaRegFin;
     private String notaMediaIni;
     private String notaMediaFin;
     private String order;
}
