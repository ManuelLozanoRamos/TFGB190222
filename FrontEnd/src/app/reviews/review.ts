export class Review {
    idReview:number;
    username:string;
    videojuego:string;
    titulo:string;
    comentario:string;
    nota:number;
    fecha:Date;

    constructor(){
        this.idReview = 0;
        this.username = '';
        this.videojuego = '';
        this.titulo = '';
        this.comentario = '';
        this.nota = 0;
        this.fecha = new Date();
    }
}
