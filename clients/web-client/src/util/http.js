import Vue from 'vue'

import axios from 'axios'

import store from '../store';
import {account, layout} from '../store/types'

const excludes = ['/form-server/userForm/save','/form-server/userForm/selectById','/account-server/account/login','/account-server/account/refreshToken'];

// axios默认配置
axios.defaults.timeout = 10000;   // 超时时间
axios.defaults.baseURL = '';  // 默认地址

// 添加一个请求拦截器
axios.interceptors.request.use(function (config) {
    store.commit(layout.LOADING_START);

    //过滤不需要拦截的请求
    if(excludes.includes(config.url) || config.url.endsWith('/config.json')){
        return config;
    }

    //检查用户登录
    const userInfo = store.getters[account.getters.GET_USER_INFO];
    if(null == userInfo) {
        Vue.prototype.$router.push({path: '/login'})
    }else {
        //判断token是否快过期
        const expireTime = userInfo.expireTime || 0;
        const leftTime = expireTime - new Date().getTime();
        if(leftTime < 5 * 60 * 1000 && leftTime > 0){
            Vue.prototype.$message.info('静默刷新token');
            //重新申请token
            return new Promise(resolve => {
                store.dispatch(account.actions.REFRESH_TOKEN, userInfo.accessToken).then(accessToken => {
                    config.headers.Authorization = accessToken;
                    Vue.prototype.$message.info('完成静默刷新token');
                    resolve(config);
                });
            });
        }

        if(userInfo.accessToken) {
            config.headers.Authorization = userInfo.accessToken;
        }
    }

    return config;
}, function (error) {
    store.commit(layout.LOADING_END);

    // Do something with request error
    return Promise.reject(error);
});

// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
    store.commit(layout.LOADING_END);

    // Do something with response data
    console.log('http after', response);
    if(response.data.code == 0 && response.data.msg){
        Vue.prototype.$error({title: '请求失败', content: response.data.msg});
        throw new Error(response);
    }

    return response.data;
}, function (error) {
    store.commit(layout.LOADING_END);

    let msg = '';
    if(error.response){

        if(error.response.status == 500){
            msg = '系统异常！';
        }else if(error.response.status == 401) {
            msg = '没有访问权限！';
        }else if(error.response.status == 408) {
            msg = '登录已超时，请重新登录！';
        }else if(error.response.status == 203) {
            msg = '非法Token值！';
        }

        if(error.response.data) {
            msg += (error.response.data.msg || error.response.data.message);
        }
        // msg += error.response.statusText;
    }
    // Do something with response error
    if(error.message) {
        msg += error.message;
    }


    Vue.prototype.$message.error(msg);

    return Promise.reject(error);
});

export default axios;