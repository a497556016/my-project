import http from '../../net/http'

export default {
    getLineUserInfo() {
        return http.get('/only-chat-server/getLineUserInfo');
    },
    selectUserList(params){
        return http.get('/user/list', params);
    },
    login(params) {
        return http.get('/user/login', {
            params
        })
    },
    register(user) {
        return http.post('/user/register', user);
    }
}