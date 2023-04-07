export class GameInfo {
    plataforma:string;
    desarrolladora:string;
    generos:string;
    fechaLanzamiento:Date;
    fechaRegistro:Date;

    constructor(){
        this.plataforma = '';
        this.desarrolladora = '';
        this.generos = '';
        this.fechaLanzamiento = new Date();
        this.fechaRegistro = new Date();
    }
}
