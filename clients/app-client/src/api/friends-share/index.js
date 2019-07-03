import http from '../../net/http'


const getFriendsShareData = function (current, size) {
    return http.get('/bbs-server/getFriendsShareData', {
        params: {current, size}
    });
}

export default {
    getFriendsShareData
}