import * as types from './types'

import friendsShareApi from '../../api/friends-share'

const state = {
    friendsShare: {
        current: 1,
        size: 5,
        loading: false,
        finished: false,
        refreshing: false,
        data: []
    }
}

const getters = {}

const mutations = {}

const actions = {
    [types.GET_FRIENDS_SHARE_LIST] ({state}) {
        setTimeout(() => {
            friendsShareApi.getFriendsShareData(state.friendsShare.current, state.friendsShare.size).then(res => {
                state.friendsShare.loading = false;
                state.friendsShare.finished = (res.current >= res.pages);
                state.friendsShare.current += 1;
                state.friendsShare.data.push(...res.data);
            })
        }, 1000);
    },
    [types.REFRESH_FRIENDS_SHARE] ({state}) {
        setTimeout(() => {
            friendsShareApi.getFriendsShareData(1, state.friendsShare.size).then(res => {
                state.friendsShare.refreshing = false;
                state.friendsShare.current = 2;
                state.friendsShare.data = res.data;
            })
        }, 1000);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}