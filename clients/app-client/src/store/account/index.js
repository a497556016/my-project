import * as types from '../types'
import accountApi from '../../api/account'

const SESSION_LOGIN_USER = "login_user";

const state = {
    loginUser: {
        account: '',
        username: '',
        password: '',
        phone: '',
        accessToken: '',
        avatar: '',
        gender: 1
    }
}

const getters = {
    [types.GET_LOGIN_USER] (state){
        if(!state.loginUser || !state.loginUser.username) {
            const json = sessionStorage.getItem(SESSION_LOGIN_USER);
            if(json) {
                state.loginUser = JSON.parse(json);
            }
        }
        return state.loginUser;
    }
}

const mutations = {
    [types.SAVE_LOGIN_USER] (state) {
        const json = JSON.stringify(state.loginUser);
        sessionStorage.setItem(SESSION_LOGIN_USER, json);
    },
    [types.LOGOUT_CURRENT_USER] (state) {
        sessionStorage.removeItem(SESSION_LOGIN_USER);
        state.loginUser = {}
    }
}

const actions = {
    [types.LOGIN_BY_PHONE_CODE] ({commit, state}, code) {
        console.log(state.loginUser, code)
        return accountApi.loginByPhoneCode(state.loginUser.phone, code).then(res => {
            if(res.code == 1) {
                Object.assign(state.loginUser, res.data);
                commit(types.SAVE_LOGIN_USER);
            }
            return res;
        });
    },
    [types.LOGIN_BY_ACCOUNT] ({commit, state}) {
        return accountApi.loginByAccount(state.loginUser).then((res) => {
            if(res.code == 1) {
                Object.assign(state.loginUser, res.data);
                commit(types.SAVE_LOGIN_USER);
            }
            return res;
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