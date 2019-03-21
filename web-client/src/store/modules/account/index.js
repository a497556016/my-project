import Vue from 'vue'
import types from './types'

import router from '@/route/router'

import AccountService from '@/api/account'
const accountService = new AccountService();

const state = {
    userInfo: null,
}

const getters = {
    [types.getters.GET_USER_INFO]: (state) => {
        if(state.userInfo == null) {
            const json = sessionStorage.getItem('loginUserInfo');
            if(null!=json){
                state.userInfo = JSON.parse(json);
            }else{
                return null;
            }
        }
        return state.userInfo;
    },
    [types.getters.GET_AVATAR]: (state) => {
        if(!state.userInfo||!state.userInfo.avatar){
            return '';
        }
        const encodedPath = encodeURI(state.userInfo.avatar);
        return Vue.config.custom.baseURL + '/file-server/download?path='+encodedPath;
    }
}

const mutations = {
    [types.mutations.LOGIN](state, data) {
        accountService.login(data).then(res => {
            state.userInfo = {
                username: data.username,
                password: data.password,
                accessToken: res.data.accessToken,
                expireTime: res.data.expireTime,
                avatar: res.data.avatar
            }
            sessionStorage.setItem('loginUserInfo', JSON.stringify(state.userInfo));

            router.push({path: '/home'});
        });
    },
    [types.mutations.LOGOUT](state) {
        state.userInfo = null;
        sessionStorage.setItem('loginUserInfo', null);

        router.push({path: '/login'})
    },
    [types.mutations.SET_TOKEN] (state, token) {
        if(!state.userInfo){
            const json = sessionStorage.getItem('loginUserInfo');
            if(null!=json){
                state.userInfo = JSON.parse(json);
            }
        }
        state.userInfo.accessToken = token.accessToken;
        state.userInfo.expireTime = token.expireTime;

        sessionStorage.setItem('loginUserInfo', JSON.stringify(state.userInfo));
    },
}

const actions = {
    [types.actions.REFRESH_TOKEN] ({commit}, accessToken) {
        return new Promise(resolve => {
            accountService.refreshToken(accessToken).then(res => {
                commit(types.mutations.SET_TOKEN, res.data);
                resolve(res.data.accessToken);
            });
        });
    },
    [types.actions.REGISTER_USER] ({commit}, user) {
        return new Promise(resolve => {
            accountService.register(user).then(res => {
                Vue.prototype.$message.success('新用户注册成功！');
                resolve(res);
            });
        });
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}