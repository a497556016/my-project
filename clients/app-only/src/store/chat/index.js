import {chat as types, user as userTypes} from '../types'

import api from '../../api/chat'

const state = {
    hisChatRecords: [],
    chatRecords: []
}

const getters = {}

const mutations = {
    [types.ADD_CHAT_RECORD](state, record){
        state.chatRecords.push(record);
    }
}

const actions = {
    async [types.LOAD_HIS_CHAT_RECORDS] ({commit, state, rootGetters}) {
        const loginUserInfo = rootGetters['user/'+userTypes.GET_LOGIN_USER];
        const lineUserInfo = rootGetters['user/'+userTypes.GET_LINE_USER_INFO];
        const res = await api.loadHisChatRecord({current: 1, size: 10, loginUsername: loginUserInfo.username, lineUsername: lineUserInfo.username});
        if(res.code == 1){
            state.hisChatRecords = res.data.content;
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}