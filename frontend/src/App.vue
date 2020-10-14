<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
    >
      <v-list dense>
        <v-list-item link @click="$router.push('/')">>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="$router.push('/user/selectMap')" >
          <v-list-item-action>
            <v-icon>mdi-food</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>restruant</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
         <v-list-item link @click="$router.push('/board/notice')">
          <v-list-item-action>
            <v-icon>mdi-star</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Notice</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      app
      color="deep-orange accent-3"
      dark
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-btn tile color="deep-orange accent-3" to="/">
      <v-toolbar-title>완벽한 맛남</v-toolbar-title>
      </v-btn>
      <v-spacer></v-spacer>
      <div v-if="isLoggedIn">
          <small class="font-weight-bold">{{ this.$store.state.nickname }}</small>님 환영합니다.
          
          <v-btn class="ma-2" outlined color="white" v-on:click="logout">
            로그아웃
          </v-btn>
          <v-btn class="ma-2" outlined color="white" v-on:click="userProfile">
            프로필
          </v-btn>
          </div>
          <div v-if="!isLoggedIn">        
          <v-btn class="ma-2" outlined color="white" to="/user/login" >
            <v-icon>mdi-account-outline</v-icon>로그인 & 회원가입
          </v-btn>
        </div>
    </v-app-bar>
    <v-main>
        <v-row
          align="center"
          justify="center"
        >
          <v-col class="text-center">
            <v-tooltip left>
            </v-tooltip>
          </v-col>
        </v-row>
    <router-view class="container"/>
    </v-main>
    <v-footer
      id="footer"
      class="pa-0"
      color="deep-orange accent-3"
      app
    >
      <v-col
        class="text-center" 
        cols="12"
      >
      
      <div class="white--text text-center">
        &copy; {{ new Date().getFullYear() }} made by
        <a href="https://github.com/gyw8526"> Youngwook Go, </a> 
        <a href="https://github.com/dmdekf"> Yunji Na, </a> 
        <a href="https://github.com/lshmn951"> Sung Hyeon Lee, </a> 
        <a href="https://github.com/wjsgudwls89"> Hyung jin Jeon. </a>
      </div>
      </v-col>
    </v-footer>
    
    <div class = "blank">
    </div>

  </v-app>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'App',
  computed: {
    ...mapGetters(['isLoggedIn'])
  },
  props: {
    source: String,
  },
  data: () => ({
    drawer: false,
    
  }),
  methods:{
    ...mapActions(['logout']),
    userProfile(){
      const nickname = this.$store.state.nickname
            this.$router.push(`/user/profile/${nickname}`);
        },
  }
}
</script>

<style>
.blank{
  height: 50px;
}
#footer {
  opacity: 80%;
}

#footer a {
  color:white; 
}

</style>