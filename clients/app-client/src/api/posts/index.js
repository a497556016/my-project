import http from "../../net/http";

const getRecommendPosts = function (current, size) {
    return http.get('/bbs-server/getRecommendPosts', {
        params: {current, size}
    })
}

const refreshRecommendPosts = function () {
    return http.get('/bbs-server/getRecommendPosts', {
        params: {current: 1, size: 5}
    })
}

export default {
    getRecommendPosts,
    refreshRecommendPosts
}