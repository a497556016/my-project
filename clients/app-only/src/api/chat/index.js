import http from '../../net/http'

export default {
    loadHisChatRecord(params){
        return http.get('/message/queryHisUserMessages', {params});
    },
    queryNotReceiveMessages(params){
        return http.get('/message/queryNotReceiveMessages', {params})
    }
}