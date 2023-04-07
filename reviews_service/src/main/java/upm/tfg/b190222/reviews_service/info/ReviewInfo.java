package upm.tfg.b190222.reviews_service.info;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewInfo {
     private String titulo;
     private String comentario;
     private int nota;
     private LocalDate fechaRegistro;
}
