import * as types from './types'

import roleService from '@/api/system/role'
import rolePermissionService from '@/api/system/role/role_permission'

import Vue from 'vue'

const state = {
    editRole: {},

    assignPermissionRole: {
        permissions: []
    },
    assignedPermissions: []
}

const getters = {
    [types.GET_EDIT_ROLE_DATA](state){
        return state.editRole;
    }
}

const mutations = {
    [types.SET_EDIT_ROLE_DATA](state, role) {
        state.editRole = role;
    },
    [types.SET_ASSIGN_PERMISSION_ROLE](state, role) {
        if(role.permissions == null)
        role.permissions = [];
        state.assignPermissionRole = role;
    },
    [types.SET_ASSIGNED_PERMISSIONS](state, permissions) {
        const ids = state.assignPermissionRole.permissions.map(p => p.id);
        permissions.forEach(permission => {
            if(ids.includes(permission.id)){
                permission.assigned = true;
            }
        })

        state.assignedPermissions = permissions;
    }
}

const actions = {
    [types.QUERY_ROLE_LIST]({commit, state}, params) {
        return rolePermissionService.selectPage(params);
    },
    [types.SAVE_EDIT_ROLE]({state}){
        return roleService.save(state.editRole);
    },
    [types.SAVE_ASSIGNED_PERMISSIONS] ({commit, state}) {
        state.assignPermissionRole.permissions = state.assignedPermissions.filter(p => p.assigned);
        rolePermissionService.assignPermissions(state.assignPermissionRole).then(res => {
            if(res.code == 1){
                Vue.prototype.$message.success('分配权限完成！');
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