export class Usuario {
    username:string;
    password:string;
    mail:string;
    fechaRegistro:Date;
    activado:boolean;


    constructor(username:string, password:string, mail:string){
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.fechaRegistro = new Date();
        this.activado = false;
    }
}
