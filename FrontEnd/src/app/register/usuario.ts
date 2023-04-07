export class Usuario {
    username:string;
    password:string;
    fechaRegistro:Date;

    constructor(username:string, password:string){
        this.username = username;
        this.password = password;
        this.fechaRegistro = new Date();
    }
}
