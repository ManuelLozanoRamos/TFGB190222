export class ReviewInfo {
    titulo:string;
    comentario:string;
    nota:number;
    fechaRegistro:Date;

    constructor(){
        this.titulo = '';
        this.comentario = '';
        this.nota = 0;
        this.fechaRegistro = new Date();
    }
}
