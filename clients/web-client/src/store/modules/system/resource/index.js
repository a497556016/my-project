import * as types from './types'
import resourceService from '../../../../api/system/resource'

import Vue from 'vue'

const state = {
    editResource: {}
}

const getters = {}

const mutations = {
    [types.SET_EDIT_RESOURCE_DATA] (state, resource) {
        state.editResource = resource;
    }
}

const actions = {
    [types.QUERY_LIST]({commit, state}, params = {current: 1, size: 10}){
        return resourceService.selectPage(params);
    },
    async [types.SAVE_EDIT_RESOURCE] ({commit, state}) {
        const res = await resourceService.save(state.editResource);
        if(res.code == 1){
            Vue.prototype.$message.success('保存成功！');
        }
        return res;
    },
    [types.DELETE_RESOURCES] ({commit, state}, keys){
        return new Promise((resolve) => {
            Vue.prototype.$confirm({
                content: '是否要删除？',
                async onOk(){
                    const res = await resourceService.batchDelete(keys);
                    if(res.code == 1){
                        Vue.prototype.$message.success('删除成功！');
                    }
                    resolve();
                }
            })
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