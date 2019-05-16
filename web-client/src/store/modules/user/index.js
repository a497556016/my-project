import Vue from 'vue'

import {getters as getterTypes, mutations as mutationTypes, actions as actionTypes} from './types'

import UserService from '@/api/system/user'
const userService = new UserService();

const state = {
    openEdit: false,
    editUserInfo: {},

    selectedKeys: []
}

const getters = {

}

const mutations = {
    [mutationTypes.SET_SELECTED_KEYS] (state, keys) {
        state.selectedKeys = keys;
    }
}

const actions = {
    [actionTypes.SELECT_PAGE] ({commit}, option) {
        return new Promise(resolve => {
            userService.selectPage(option).then(res => {
                res.data.forEach(d => {
                    if(d.thumbAvatar) {
                        d.avatar = Vue.config.custom.baseURL + '/file-server/download?path=' + encodeURI(d.thumbAvatar);
                    }else{
                        d.avatar = null;
                    }
                });
                resolve(res);
            });
        });
    },
    [actionTypes.BATCH_DELETE] ({commit, state}) {
        console.log('删除', state.selectedKeys)
        return userService.batchDelete(state.selectedKeys);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}