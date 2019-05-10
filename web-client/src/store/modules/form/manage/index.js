import * as types from './types'

import CustomerFormService from '@/api/form'

const customerFormService = new CustomerFormService();

const state = {}

const getters = {}

const mutations = {}

const actions = {
    [types.LOAD_TABLE_DATA]({state, commit}, params) {
        return customerFormService.selectPage(params);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}