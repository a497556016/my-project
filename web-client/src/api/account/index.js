import BaseService from "../base";

export default class AccountService extends BaseService {
    constructor(){
        super('/account-server/account');
    }

    login(params){
        return this.get('/login', params);
    }

    refreshToken(accessToken) {
        return this.get('/refreshToken', {
            accessToken
        });
    }

    register(user){
        return this.post('/regist', user);
    }

    modify(user) {
        return this.put('/modify', user);
    }
}