import Vue from "vue";
import VueRouter from "vue-router";
import Vuex from "vuex";
import BootstrapVue from "bootstrap-vue";

import Main from "../components/Main.vue";
import Recommand from '../components/Recommand.vue';

import Login from "../components/user/Login.vue";
import Profile from "../components/user/Profile.vue";
import Update from "../components/user/Update.vue";
import notice from "../components/board/Notice.vue";
import boardWrite from "../components/board/Write.vue";
import boardDetail from "../components/board/Detail.vue";

import storeDetail from "../components/stores/Detail.vue"

import reviewWrite from "../components/review/Write.vue"
Vue.use(BootstrapVue)
Vue.use(Vuex)
Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "MAIN",
    component: Main,
  },
  {
    path:'/user/login',
    name: 'userLogin',
    component: Login,
  },
  {
    path: '/user/profile/:nickname',
      name: 'userProfile',
    props: ({
      params
    }) => ({
      nickname: params.nickname
    }),
    component: Profile,
  },
  {
    path: '/user/update/:nickname',
    name: 'userUpdate',
    props: ({
      params
    }) => ({
      nickname: params.nickname
    }),
    component: Update,
  },
  {
    path: "/user/selectMap",
    name: "SELECTMAP",
    component: Recommand,
  },
  {
    path: "/board/notice",
    name: "notice",
    component: notice,
  },

  {
    path: "/board/write",
    name: "boardWrite",
    component: boardWrite,
  },
  {
    path: "/board/detail/:id",
    props: ({
      params
    }) => ({
      id: Number.parseInt(params.id)
    }),
    name: "boardDetail",
    component: boardDetail
  },
  {
    path: "/stores/detail/:id",
    props: ({
      params
    }) => ({
      id: Number.parseInt(params.id)
    }),
    name: "storeDetail",
    component: storeDetail
  },
  {
    path: "/review/:storeId/write",
    props: ({
      params
    }) => ({
      storeId: Number.parseInt(params.storeId)
    }),
    name: "reviewWrite",
    component: reviewWrite,
  },
  
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
