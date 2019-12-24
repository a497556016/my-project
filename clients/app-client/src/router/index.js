import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import routes from './routes'
import store from "../store";

const router = new VueRouter({
    routes,
    /*scrollBehavior (to, from, savedPosition) {
        console.log(from.path, document.scrollingElement.scrollTop)
        if (savedPosition) {
            return savedPosition
        } else {
            from.meta.savedPosition = document.scrollingElement.scrollTop;
            return { x: 0, y: to.meta.savedPosition ||0}
        }
    }*/
})

router.beforeEach((to, from, next) => {
    // ...
    console.log(`切换路由从${from.fullPath}到${to.fullPath}`);
    if(to.path.startsWith('/main/user')){
        //check login
        const userInfo = store.getters['account/GET_LOGIN_USER'];
        console.log('检查用户登录信息：', userInfo)
        if(!userInfo || (!userInfo.username && !userInfo.phone)){
            next({path: '/login'});
        }
    }

    next();
})


export default router;