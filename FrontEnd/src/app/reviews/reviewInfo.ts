export class ReviewInfo {
    titulo:string;
    comentario:string;
    nota:number;
    fecha:Date;

    constructor(){
        this.titulo = '';
        this.comentario = '';
        this.nota = 0;
        this.fecha = new Date();
    }
}
