export class Game {
    nombre:string;
    plataforma:string;
    desarrolladora:string;
    genero1:string;
    genero2:string;
    genero3:string;
    generos:string;
    notaMedia:number;
    fechaLanzamiento:Date;
    fechaRegistro:Date;


    constructor(){
        this.nombre = '';
        this.plataforma = '';
        this.desarrolladora = '';
        this.genero1 = '';
        this.genero2 = '';
        this.genero3 = '';
        this.generos = '';
        this.notaMedia = 5;
        this.fechaLanzamiento = new Date();
        this.fechaRegistro = new Date();
    }
}
