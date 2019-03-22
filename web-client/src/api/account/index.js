import BaseService from "../base";

export default class AccountService extends BaseService {
    constructor(){
        super('/account-server/account');
    }

    login(params){
        if(params.username == '888888' && params.password == '888888'){
            return new Promise(resolve => resolve({
                data: {
                    accessToken: 'aaaaaaaaaa',
                    expireTime: new Date().getTime() + 30 * 60 * 1000,
                    avatar: ''
                },
                code: 1
            }));
        }
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