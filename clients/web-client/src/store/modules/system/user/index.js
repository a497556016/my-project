import Vue from 'vue'

import {getters as getterTypes, mutations as mutationTypes, actions as actionTypes} from './types'

import UserService from '@/api/system/user'
const userService = new UserService();

import roleService from '@/api/system/role'

const state = {
    openEdit: false,
    editUserInfo: {},

    selectedKeys: [],

    //给此用户分配角色
    assignRoleUser: {
        roles: [] //用户拥有的角色
    },
    //系统角色列表
    roleList: []
}

const getters = {
    [getterTypes.GET_USER_ASSIGNED_INFO] (state) {
        // state.assignRoleUser.roles = state.roleList.filter(role => role.assigned);

        return state.roleList.filter(role => role.assigned);
    }
}

const mutations = {
    [mutationTypes.SET_SELECTED_KEYS] (state, keys) {
        state.selectedKeys = keys;
    },
    [mutationTypes.SET_ROLE_LIST] (state, roleList) {
        const ids = state.assignRoleUser.roles.map(role => role.id);

        state.roleList = roleList.map(role => {
            role.assigned = _.includes(ids, role.id);
            return role;
        })
    },
    [mutationTypes.SET_ASSIGN_ROLE_USER] (state, user) {
        state.assignRoleUser = user;
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
    },
    [actionTypes.ASSIGN_USER_ROLES] ({commit, state}) {
        state.assignRoleUser.roles = state.roleList.filter(role => role.assigned);
        return userService.assignRoles(state.assignRoleUser);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}