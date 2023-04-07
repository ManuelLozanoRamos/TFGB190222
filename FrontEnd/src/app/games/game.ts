export class Game {
    nombre:string;
    plataforma:string;
    desarrolladora:string;
    generos:string;
    fechaLanzamiento:Date;
    fechaRegistro:Date;

    constructor(){
        this.nombre = '';
        this.plataforma = '';
        this.desarrolladora = '';
        this.generos = '';
        this.fechaLanzamiento = new Date();
        this.fechaRegistro = new Date();
    }
}
