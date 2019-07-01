import axios from 'axios'
import {Toast} from "vant";

import interceptor from '../interceptor'

interceptor(axios);

// axios默认配置
axios.defaults.timeout = 10000;   // 超时时间
axios.defaults.baseURL = '';  // 默认地址

axios.withLoading = function () {
    Toast.loading({
        mask: true,
        message: '加载中...'
    });

    return this;
}

axios.closeLoading = function () {
    Toast.clear();
}

export default axios;