import Vue from 'vue'
import Vuex from 'vuex'

import account from './modules/account'
import user from './modules/user'
import userEdit from './modules/user/edit'
import userAdd from './modules/user/add'
import layout from './modules/layout'
import file from './modules/file'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        account,
        user,
        userEdit,
        userAdd,
        layout,
        file,
    },
});

export default store;