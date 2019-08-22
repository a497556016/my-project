import Vue from 'vue'
import Vuex from 'vuex'

import account from './modules/system/account'
import user from './modules/system/user'
import userEdit from './modules/system/user/edit'
import userAdd from './modules/system/user/add'
import role from './modules/system/role'
import permission from './modules/system/permission'
import resource from './modules/system/resource'
import layout from './modules/layout'
import file from './modules/file'
import fileMain from './modules/file/main'
import fileManage from './modules/file/manage'
// import formCreate from './modules/form/create'
// import formManage from './modules/form/manage'

import form from './modules/form'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        account,
        user,
        userEdit,
        userAdd,
        role,
        permission,
        resource,
        layout,
        file,
        fileMain,
        fileManage,
        form
    },
});

export default store;