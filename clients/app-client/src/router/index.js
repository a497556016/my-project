import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import routes from './routes'
import store from "../store";

const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    // ...
    console.log(from, to);
    if(to.path.startsWith('/main/user')){
        //check login
        const userInfo = store.getters['account/GET_LOGIN_USER'];
        console.log(userInfo)
        if(!userInfo || (!userInfo.username && !userInfo.phone)){
            next({path: '/login'});
        }
    }

    next();
})


export default router;