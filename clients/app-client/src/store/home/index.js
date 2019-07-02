import * as types from '../types'

import homeApi from '../../api/home'

const state = {
    headerBar: {
        placeholder: '水弹枪',
        searchWords: ''
    },
    topImages: [

    ],
    commendPosts: {
        current: 1,
        size: 5,
        finished: false,
        loading: false,
        data: []
    },
}

const getters = {

}

const mutations = {
    [types.SET_TOP_IMAGES] (state, images) {
        state.topImages = images;
    }
}

const actions = {
    [types.LOAD_TOP_IMAGES] ({commit, state}) {
        homeApi.getTopImages().then(res => {
            if(res.code == 1){
                commit(types.SET_TOP_IMAGES, res.data);
            }
        })
    },
    [types.GET_COMMEND_POSTS] ({commit, state}){
        state.commendPosts.loading = true;
        homeApi.getRecommendPosts(state.commendPosts.current, state.commendPosts.size).then(res => {
            state.commendPosts.loading = false;

            if(res.code == 1){
                state.commendPosts.finished = (res.current >= res.pages);
                state.commendPosts.current = res.current + 1;
                state.commendPosts.data.push(...res.data);
            }
        })
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}