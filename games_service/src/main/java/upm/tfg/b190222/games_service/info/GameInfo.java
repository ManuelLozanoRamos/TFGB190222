package upm.tfg.b190222.games_service.info;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameInfo {
    private String plataforma;
    private String desarrolladora;
    private String genero1;
    private String genero2;
    private String genero3;
    private LocalDate fechaLanzamiento;
    private LocalDate fechaRegistro;
}
