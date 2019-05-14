import Vue from 'vue'
import * as types from './types'

import CustomerFormService from '@/api/form'

const customerFormService = new CustomerFormService();

const state = {
    formData: {}
}

const getters = {

}

const mutations = {
    [types.SET_FORM_DATA](state, data) {
        state.formData = data;
    }
}

const actions = {
    [types.SAVE_FORM]({state, commit}, formData) {
        return customerFormService.save(formData)
    },
    [types.LOAD_TABLE_DATA]({state, commit}, params) {
        return customerFormService.selectPage(params);
    },
    [types.FIND_BY_ID]({commit}, id) {
        customerFormService.selectUserFormById(id).then(res => {
            if(res.code == 1){
                res.data.formItems.forEach(item => {
                    if(item.component == 'm-rate') {
                        item.meta.value = parseFloat(item.meta.value||0);
                        item.meta.count = parseInt(item.meta.count||0);
                    }
                })
                commit(types.SET_FORM_DATA, res.data);
            }
        });
    },
    [types.SUBMIT_FORM] ({commit, state}) {
        console.log(state.formData)
        customerFormService.saveUserForm(state.formData).then(res => {
            if(res.code == 1){
                Vue.prototype.$message.success('保存成功！');
            }
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