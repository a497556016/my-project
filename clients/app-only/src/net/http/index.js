import axios from 'axios'

import interceptor from '../interceptor'

interceptor(axios);

// axios默认配置
axios.defaults.timeout = 10000;   // 超时时间
axios.defaults.baseURL = 'http://192.168.0.171:8081/';  // 默认地址

axios.withLoading = function () {

    return this;
}

axios.closeLoading = function () {

}

export default axios;