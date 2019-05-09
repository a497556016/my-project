import Vue from 'vue'
import * as types from './types'

import CustomerFormService from '@/api/form/create'

const customerFormService = new CustomerFormService();

const state = {

}

const getters = {

}

const motations = {

}

const actions = {
    [types.SAVE_FORM]({state, commit}, formData) {
        return customerFormService.save(formData)
    }
}

export default {
    namespaced: true,
    state,
    getters,
    motations,
    actions
}