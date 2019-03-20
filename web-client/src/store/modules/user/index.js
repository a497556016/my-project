import Vue from 'vue'

import {getters as getterTypes, mutations as mutationTypes, actions as actionTypes} from './types'

import UserService from '@/service/user'
const userService = new UserService();

const state = {
    openEdit: false,
    editUserInfo: {},


}

const getters = {

}

const mutations = {

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
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}