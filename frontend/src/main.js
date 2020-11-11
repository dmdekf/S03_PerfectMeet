import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
Vue.config.productionTip = false

import vuetify from './plugins/vuetify';
import VuetifyDialog from 'vuetify-dialog'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
<<<<<<< HEAD
import VModel from 'vue-js-modal';
=======

Vue.use(VuetifyDialog,{
  context:{
    vuetify
  }
})
>>>>>>> 1ad93e66ada620ce4c0d8720f8fb2caead4c85b3

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App),
  VModel

}).$mount('#app')
