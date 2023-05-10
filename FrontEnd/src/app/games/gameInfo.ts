export class GameInfo {
    nombre:string|null;
    plataforma1:string|null;
    plataforma2:string|null;
    plataforma3:string|null;
    desarrolladora:string|null;
    genero1:string|null;
    genero2:string|null;
    genero3:string|null;
    fechaLanzamiento:Date|null;

    notaMediaIni:string|null;
    notaMediaFin:string|null;
    fechaLanIni:string|null;
    fechaLanFin:string|null;
    order:string|null;

    [key: string]: any;


    constructor(){
        this.nombre = null;
        this.plataforma1 = null;
        this.plataforma2 = null;
        this.plataforma3 = null;
        this.desarrolladora = null;
        this.genero1 = null;
        this.genero2 = null;
        this.genero3 = null;
        this.fechaLanzamiento = null;
        this.notaMediaIni = null;
        this.notaMediaFin = null;
        this.fechaLanIni = null;
        this.fechaLanFin = null;
        this.order = null;
    }
}
