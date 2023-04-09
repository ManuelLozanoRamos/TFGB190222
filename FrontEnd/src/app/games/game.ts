export class Game {
    nombre:string;
    plataforma:string;
    desarrolladora:string;
    generos:string;
    notaMedia:number;
    fechaLanzamiento:Date;
    fechaRegistro:Date;


    constructor(){
        this.nombre = '';
        this.plataforma = '';
        this.desarrolladora = '';
        this.generos = '';
        this.notaMedia = 5;
        this.fechaLanzamiento = new Date();
        this.fechaRegistro = new Date();
    }
}
