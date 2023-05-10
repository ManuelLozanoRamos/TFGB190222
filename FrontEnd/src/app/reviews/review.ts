export class Review {
    idReview:number|null;
    username:string|null;
    videojuego:string|null;
    titulo:string|null;
    comentario:string|null;
    nota:number|null;
    fechaRegistro:Date|null;

    constructor(){
        this.idReview = null;
        this.username = null;
        this.videojuego = null;
        this.titulo = null;
        this.comentario = null;
        this.nota = null;
        this.fechaRegistro = null;
    }
}
