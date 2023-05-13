export class ReviewInfo {
    titulo:string|null;
    comentario:string|null;
    nota:number|null;

    username:string|null;
    videojuego:string|null;
    fechaRegIni:string|null;
    fechaRegFin:string|null;
    notaIni:string|null;
    notaFin:string|null;
    order:string|null;

    [key: string]: any;


    constructor(){
        this.titulo = null;
        this.comentario = null;
        this.nota = null;

        this.username = null;
        this.videojuego = null;
        this.fechaRegIni = null;
        this.fechaRegFin = null;
        this.notaIni = null;
        this.notaFin = null;
        this.order = null;
    }
}
