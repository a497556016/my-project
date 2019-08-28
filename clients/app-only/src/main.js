import Vue from 'vue'
import App from './App.vue'

import router from './router'

import store from './store'

import http from './net/http'

require('./mock')

import '@/assets/style/index.less'

import 'font-awesome/css/font-awesome.min.css'

import iMServer from './util/io'

Vue.config.productionTip = false
Vue.prototype.axios = http;
Vue.prototype.iMServer = iMServer;

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
