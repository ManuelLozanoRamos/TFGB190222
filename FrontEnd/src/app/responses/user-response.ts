export class UserResponse {
    response:string;
    token:string;

    constructor(response:string, token:string){
        this.response = response;
        this.token = token;
    }
}
