import Vue from 'vue'
import Antd from 'ant-design-vue'
import App from './App.vue'
import 'ant-design-vue/dist/antd.less'
import '@/assets/style/antd_over.less'

let _ = require('lodash');

import router from './route/router'
import store from './store'

// import {filter} from './config/filter'
import http from './util/http'

Vue.config.productionTip = false

Vue.use(Antd)

console.log(process.env)
//预加载公共配置信息
http.get(`${process.env.BASE_URL}config.json`).then(config => {
    console.log('公共配置：', Vue.config);

    Vue.config.custom = config;

    http.defaults.baseURL = config.baseURL;  // 默认地址
    //创建http注入到全局
    Vue.prototype.$http = http;

    new Vue({
        render: h => h(App),
        router,
        store,
        mounted() {
            // filter();
        }
    }).$mount('#app')
});


