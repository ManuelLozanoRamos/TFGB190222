export class GameInfo {
    plataforma:string;
    desarrolladora:string;
    genero1:string;
    genero2:string;
    genero3:string;
    fechaLanzamiento:Date;
    fechaRegistro:Date;

    constructor(){
        this.plataforma = '';
        this.desarrolladora = '';
        this.genero1 = '';
        this.genero2 = '';
        this.genero3 = '';
        this.fechaLanzamiento = new Date();
        this.fechaRegistro = new Date();
    }
}
