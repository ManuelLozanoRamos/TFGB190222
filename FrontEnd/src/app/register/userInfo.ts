export class UserInfo {
    username:string|null;
    password:string|null;
    mail:string|null;

    constructor(username:string|null, password:string|null, mail:string|null){
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
}
