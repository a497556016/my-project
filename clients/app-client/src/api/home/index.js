import http from '../../net/http'

const getTopImages = function () {
    return http.get('/file-server/getTopImages');
}

const getRecommendPosts = function (current, size) {
    return http.get('/bbs-server/getRecommendPosts', {
        params: {current, size}
    })
}

export default {
    getTopImages,
    getRecommendPosts
}