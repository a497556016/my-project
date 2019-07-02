import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import account from './account'
import home from './home'
import menus from './menus'

export default new Vuex.Store({
    modules: {
        account,
        home,
        menus
    }
})