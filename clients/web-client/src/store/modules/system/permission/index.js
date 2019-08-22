import * as types from './types'

import permissionService from '../../../../api/system/permission'
import permissionResourceService from '../../../../api/system/permission/permission_resource'

import {resource} from "../../../types"

import Vue from 'vue'

const state = {
    editPermission: {
        resources: []
    },

    assignedResources: []
}

const getters = {
    [types.GET_EDIT_PERMISSION_DATA] (state) {
        return state.editPermission;
    }
}

const mutations = {
    [types.SET_EDIT_PERMISSION_DATA] (state, permission){
        if(!permission.resources) {
            permission.resources = []
        }
        state.editPermission = permission
    },
    [types.PUT_EDIT_PERMISSION_RESOURCES] (state, resource){

        if(resource.assigned) {
            const r = _.cloneDeep(resource);
            delete r.assigned;
            state.editPermission.resources.push(r)
        }else {
            const index = state.editPermission.resources.findIndex(r => r.id == resource.id);
            if(index){
                state.editPermission.resources.splice(index, 1);
            }
        }

    }
}

const actions = {
    [types.QUERY_LIST] ({commit, state}, params = {current: 1, size: 100}) {
        return permissionService.selectPage(params);
    },
    [types.QUERY_PERMISSION_RESOURCE_LIST] ({commit, state}, params = {current: 1, size: 10}){
        return permissionResourceService.selectPage(params);
    },
    [types.SAVE_EDIT_PERMISSION] ({commit, state}, permission){
        return permissionService.save(state.editPermission).then(res => {
            Vue.prototype.$message.success('保存成功！');
        });
    },
    [types.SET_ASSIGNED_RESOURCES] ({commit, state, dispatch}, permission){
        commit(types.SET_EDIT_PERMISSION_DATA, permission);
        const ids = state.editPermission.resources.map(r => r.id);
        return dispatch(resource.QUERY_LIST, {current: 1, size: 100}, {root: true}).then(data => {
            state.assignedResources = data.map(r => {
                r.assigned = _.includes(ids, r.id);
                return r;
            });
        })
    },
    async [types.SAVE_ASSIGNED_RESOURCES] ({commit, state}) {
        const res = await permissionResourceService.save(state.editPermission);
        if(res.code == 1) {
            Vue.prototype.$message.success('已完成资源配置！');
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}