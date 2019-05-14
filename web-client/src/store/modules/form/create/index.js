import * as types from './types'

import CustomerFormService from '@/api/form'

const customerFormService = new CustomerFormService();

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
    [types.SAVE_FORM]({state, commit}, formData) {
        return customerFormService.save(formData)
    },
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