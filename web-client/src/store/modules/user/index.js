import Vue from 'vue'

import {getters as getterTypes, mutations as mutationTypes, actions as actionTypes} from './types'

import UserService from '@/api/user'
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
                res.content.forEach(d => {
                    if(d.avatar) {
                        d.avatar = Vue.config.custom.baseURL + '/file-server/download?path=' + encodeURI(d.avatar);
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