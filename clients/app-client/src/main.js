import Vue from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'
console.log(store)

import {Toast} from 'vant'
Vue.use(Toast)
import { ImagePreview } from 'vant';
Vue.use(ImagePreview);

const mock = require('./mock');
Vue.prototype.$mock = mock.default;

import '@/assets/style/index.less'

import 'font-awesome/css/font-awesome.min.css'

Vue.config.productionTip = false

document.addEventListener('deviceready', () => {
  Toast('deviceready');
})

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
