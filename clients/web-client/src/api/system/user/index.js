import BaseService from "../../base";

export default class UserService extends BaseService{
    constructor() {
        super('/account-server/user');

    }

    assignRoles(user){
        return this.post('/assignRoles', user);
    }

}