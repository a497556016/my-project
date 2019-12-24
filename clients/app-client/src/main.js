import Vue from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'
console.log(store)

import {Toast} from 'vant'
Vue.use(Toast)
import { ImagePreview } from 'vant';
Vue.use(ImagePreview);
import H5Editor from '../public/plugins/h5-editor'
Vue.use(H5Editor);

const mock = require('./mock');
Vue.prototype.$mock = mock.default;

import '@/assets/style/index.less'

import 'font-awesome/css/font-awesome.min.css'

Vue.config.productionTip = false

function init() {
  new Vue({
    render: h => h(App),
    router,
    store
  }).$mount('#app')
}

if('undefined' == typeof cordova){
  init();
}else {
  document.addEventListener('deviceready', () => {
    Toast('deviceready');
    // StatusBar.hide();
    init();
  })

  let exitClicks = 0;
  document.addEventListener("backbutton", () => {
    const path = location.href.split("#")[1];
    Toast(path);

    if(path.startsWith("/main")){
      if(exitClicks >= 1){
        navigator.app.exitApp();
      }else {
        Toast('再次点击退出系统', {duration: 2000});
        setTimeout(() => {exitClicks = 0}, 2000);
        exitClicks+=1;
      }
    }else {
      router.back();
    }
  }, false);
}




