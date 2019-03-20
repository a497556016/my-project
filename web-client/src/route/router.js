import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './routes'

import util from '../util'
import store from "../store";
import {account, layout} from '../store/types'

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    routes // (缩写) 相当于 routes: routes
});

router.beforeEach((to, from, next) => {
    // ...
    console.log(from, to);
    if(to.path != '/login'){
        //check login
        const userInfo = store.getters[account.getters.GET_USER_INFO];
        console.log(userInfo)
        if(!userInfo){
            next({path: '/login'});
        }
    }
    next();
})


//生成栏目
store.commit(layout.SET_MENUS_BY_ROUTES, routes);


export default router;