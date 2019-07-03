import http from '../../net/http'

const getTopImages = function () {
    return http.get('/file-server/getTopImages');
}



export default {
    getTopImages,
}