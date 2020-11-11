import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
Vue.config.productionTip = false

import vuetify from './plugins/vuetify';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VModel from 'vue-js-modal';

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App),
  VModel

}).$mount('#app')
