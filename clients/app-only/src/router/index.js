import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

import routes from './routes'


import {user as userTypes} from '../store/types'
import store from '../store'

const router = new VueRouter({
    routes,
    scrollBehavior (to, from, savedPosition) {
        // return 期望滚动到哪个的位置
        return { x: 0, y: 0 }
    }
})

router.beforeEach((to, from, next) => {
    // ...
    console.log(`切换路由从${from.fullPath}到${to.fullPath}`);
    if(!to.path.startsWith('/login')){
        //check login
        const userInfo = store.getters['user/'+userTypes.GET_LOGIN_USER];
        console.log('检查用户登录信息：', userInfo)
        if(!userInfo || (!userInfo.username && !userInfo.phone)){
            next({path: '/login'});
        }else if(to.path == '/chat') {
            const lineUserInfo = store.getters['user/'+userTypes.GET_LINE_USER_INFO];
            if(null == lineUserInfo) {
                next({path: '/search_user'});
            }
        }
    }

    next();
})


export default router;