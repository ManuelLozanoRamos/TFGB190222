package upm.tfg.b190222.games_service.info;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameInfo {
    private String nombre;
    private String plataforma1;
    private String plataforma2;
    private String plataforma3;
    private String desarrolladora;
    private String genero1;
    private String genero2;
    private String genero3;
    private LocalDate fechaLanzamiento;

    private String notaMediaIni;
    private String notaMediaFin;
    private String fechaLanIni;
    private String fechaLanFin;
    private String order;
}
