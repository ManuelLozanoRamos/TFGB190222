export class Game {
    nombre:string|null;
    plataforma1:string|null;
    plataforma2:string|null;
    plataforma3:string|null;
    plataformas:string|null;
    desarrolladora:string|null;
    genero1:string|null;
    genero2:string|null;
    genero3:string|null;
    generos:string|null;
    notaMedia:number|null;
    fechaLanzamiento:Date|null;
    fechaRegistro:Date|null;


    constructor(){
        this.nombre = null;
        this.plataforma1 = null;
        this.plataforma2 = null;
        this.plataforma3 = null;
        this.plataformas = null;
        this.desarrolladora = null;
        this.genero1 = null;
        this.genero2 = null;
        this.genero3 = null;
        this.generos = null;
        this.notaMedia = null;
        this.fechaLanzamiento = null;
        this.fechaRegistro = null;
    }
}
