import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './routes'

import store from "../store";
import {account, layout} from '../store/types'

Vue.use(VueRouter)

const router = new VueRouter({
    // mode: 'history',
    routes // (缩写) 相当于 routes: routes
});

router.beforeEach((to, from, next) => {
    // ...
    console.log(from, to);
    if(to.path != '/login' && !to.path.startsWith('/form/share')){
        //check login
        const userInfo = store.getters[account.getters.GET_USER_INFO];
        console.log(userInfo)
        if(!userInfo){
            next({path: '/login'});
        }
    }

    /*路由发生改变修改页面的title */
    if(to.meta.title) {
        document.title = to.meta.title
    }

    next();
})


export default router;