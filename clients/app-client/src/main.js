import Vue from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'
console.log(store)

import {Toast} from 'vant'
Vue.use(Toast)

require('./mock');

import '@/assets/style/index.less'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
