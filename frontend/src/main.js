import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
Vue.config.productionTip = false

import vuetify from './plugins/vuetify';
import VuetifyDialog from 'vuetify-dialog'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VuetifyDialog,{
  context:{
    vuetify
  }
})

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
