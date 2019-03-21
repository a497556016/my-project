import Vue from 'vue'

import {
    getters,
    mutations,
    actions
} from './types'

import moment from 'moment'

import UserService from '@/api/user'
const userService = new UserService();
import AccountService from '@/api/account'
const accountService = new AccountService();

export default {
    namespaced: true,
    state: {
        openEdit: false,
        editUserInfo: {},
    },
    getters: {
        [getters.AVATAR_PATH] (state){
            if(state.editUserInfo.avatar) {
                return Vue.config.custom.baseURL + '/file-server/download?path='+encodeURI(state.editUserInfo.avatar);
            }
            return '';
        }
    },
    mutations: {
        [mutations.OPEN_EDIT] (state) {
            state.openEdit = true;
        },
        [mutations.CLOSE_EDIT] (state) {
            state.editUserInfo = {};
            state.openEdit = false;
        },
        [mutations.SET_EDIT_USER_INFO] (state, user) {
            if(user.birthday){
                user.birthday = moment(user.birthday).format('YYYY-MM-DD');
            }
            // user.id = state.editUserInfo.id;
            // user.avatar = state.editUserInfo.avatar;
            Object.assign(state.editUserInfo, user);
        }
    },
    actions: {
        [actions.OPEN_USER_EDIT] ({commit, state}, id) {
            commit(mutations.OPEN_EDIT);
            if(id) {
                userService.selectById(id).then(res => {
                    const user = res.data;
                    if (user) {
                        user.password = '';
                        user.confirmPassword = '';
                        if (user.birthday)
                            user.birthday = moment(user.birthday, 'YYYY-MM-DD');
                    }
                    state.editUserInfo = user;
                })
            }
        },
        [actions.MODIFY_USER] ({commit, state}) {
            console.log('修改用户：', state.editUserInfo)
            return new Promise(resolve => {
                accountService.modify(state.editUserInfo).then(res => {
                    Vue.prototype.$message.success('修改用户信息完成！');
                    commit(mutations.CLOSE_EDIT);
                    resolve();
                });
            });
        }
    }
}