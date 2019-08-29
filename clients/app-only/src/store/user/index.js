import {user as types} from '../types'

import api from '../../api/user'

import router from '../../router'

const LOGIN_USER_INFO_KEY = "loginUserInfo";
const LINE_USER_INFO_KEY = "lineUserInfo";

const state = {
    /*{
        id: 'fsf33f2g32g23g223g',
        username: 'heshaowei',
        nickname: '',
        phone: '',
        token: '123456789',
        createTime: ''
    }*/
    loginUserInfo: null,

    //连线的对象
    lineUserInfo: null,

    //搜索用户结果列表
    searchUserList: []
}

const getters = {
    [types.GET_LOGIN_USER](state){
        let json = localStorage.getItem(LOGIN_USER_INFO_KEY);
        if(json){
            state.loginUserInfo = JSON.parse(json);
        }
        return state.loginUserInfo;
    },
    [types.GET_LINE_USER_INFO](state) {
        const json = localStorage.getItem(LINE_USER_INFO_KEY);
        if(json){
            state.lineUserInfo = JSON.parse(json);
        }
        return state.lineUserInfo;
    },
    [types.GET_SEARCH_RESULT_LIST](state) {
        return state.searchUserList;
    }
}

const mutations = {
    [types.LOGOUT] (state) {
        state.loginUserInfo = null;
        localStorage.removeItem(LOGIN_USER_INFO_KEY);

        state.lineUserInfo = null;
        localStorage.removeItem(LINE_USER_INFO_KEY);
    },
    [types.SET_LOGIN_USER] (state, user) {
        if(user) {
            localStorage.setItem(LOGIN_USER_INFO_KEY, JSON.stringify(user));
            state.loginUserInfo = user;
        }
    },
    [types.SET_LINE_USER_INFO] (state, user){
        if(user){
            localStorage.setItem(LINE_USER_INFO_KEY, JSON.stringify(user));
            state.lineUserInfo = user;
        }
    }
}

const actions = {
    async [types.SEARCH_USER] ({commit, state}, searchWords) {
        const res = await api.selectUserList({current: 1, size: 10, query: searchWords});
        if(res.code == 1){
            state.searchUserList = res.data;
        }
    },
    [types.FIND_LINE_USER_INFO]({commit, state}){
        return new Promise(async resolve => {
            const res = await api.getLineUserInfo();
            if(res.code == 1){
                state.lineUserInfo = res.data;
                resolve(state.lineUserInfo);
            }
        })
    },
    async [types.LOGIN] ({commit, state}, user) {
        const res = await api.login(user);

        commit(types.SET_LOGIN_USER, res.data);
        // state.loginUserInfo = res.data;
        console.log(router)
        router.push({path: '/chat'})
    },
    async [types.REGISTER] ({commit, state}, user) {
        const res = await api.register(user);
        commit(types.SET_LOGIN_USER, res.data);
        router.push({path: '/chat'})
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}