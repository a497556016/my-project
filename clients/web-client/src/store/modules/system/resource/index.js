import * as types from './types'
import resourceService from '../../../../api/system/resource'

const state = {

}

const getters = {}

const mutations = {}

const actions = {
    [types.QUERY_LIST]({commit, state}, params = {current: 1, size: 10}){
        return resourceService.selectPage(params).then(res => {
            if(res.code == 1){
                return res.data;
            }
            return [];
        })
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}