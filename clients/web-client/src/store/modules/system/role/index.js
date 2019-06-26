import * as types from './types'

import roleService from '@/api/system/role'

const state = {
    editRole: {}
}

const getters = {
    [types.GET_EDIT_ROLE_DATA](state){
        return state.editRole;
    }
}

const mutations = {
    [types.SET_EDIT_ROLE_DATA](state, role) {
        state.editRole = role;
    }
}

const actions = {
    [types.QUERY_ROLE_LIST]({commit, state}, params) {
        return roleService.selectPage(params);
    },
    [types.SAVE_EDIT_ROLE]({state}){
        return roleService.save(state.editRole);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}