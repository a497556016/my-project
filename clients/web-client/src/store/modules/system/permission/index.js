import * as types from './types'

import permissionService from '../../../../api/system/permission'

const state = {}

const getters = {}

const mutations = {}

const actions = {
    [types.QUERY_LIST] ({commit, state}) {
        return permissionService.selectPage({current: 1, size: 100});
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}