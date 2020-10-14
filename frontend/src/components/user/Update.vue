<template>
  <div id="app">
    <v-app id="inspire">
      <v-main>
        <v-container class="fill-height" fluid>
          <v-row align="center" justify="center">
            <v-col cols="8">
              <v-card class="elevation-12">
                <v-toolbar color="deep-purple accent-4" dark flat>
                  <v-toolbar-title>{{ nickname }}님의 회원 정보</v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form ref="registerForm" v-model="valid" lazy-validation>
                    <v-text-field
                        prepend-icon="mdi-emoticon-cool-outline"
                        v-model="nickname" id="nickname"
                        type="text"
                        label="nickname"
                        outlined
                        readonly
                        dense
                    ></v-text-field>
                    <v-text-field
                        prepend-icon="mdi-email"
                        v-model="email" id="email" type="text"
                        label="email"
                        outlined
                        dense
                    ></v-text-field>
                    <v-text-field id="password"
                        outlined
                        dense v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Password" hint="At least 8 characters" counter @click:append="show1 = !show1"></v-text-field>
                    <v-text-field block v-model="verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, passwordMatch]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Confirm Password" counter @click:append="show1 = !show1"></v-text-field>
                                                         
                </v-form>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" :disabled="!valid" v-on:click="moveUpdate">수정하기</v-btn>
                  <v-btn color="red" v-on:click="Delete">탈퇴하기</v-btn>
                  <v-btn color="lime"  @click="$router.push('/')" >메인화면</v-btn>
                </v-card-actions>
                </v-card-text>
              </v-card> 
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>
<script>
import axios from 'axios';
import SERVER from "@/api/api";
import { mapActions } from 'vuex'
export default {
    name:"userUpdate",
    props:{
      nickname:{
          type:String,
          required:true,
      }
    },
    data: () => {
      return {
        valid: true,
        verify: "",
        email: '',
        nickName: '',
        password:'',
        profileImg:'',
        EmailRules: [
          v => !!v || "Required",
          v => /.+@.+\..+/.test(v) || "E-mail must be valid"
        ],
        show1: false,
        rules: {
          required: value => !!value || "Required.",
          min: v => (v && v.length >= 8) || "Min 8 characters"
        },
      }
    },
    computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Password must match";
    }
  },
    methods: {
      ...mapActions(['logout']),
        moveUpdate() {
          axios({
                method: "put",
                url: SERVER.URL+"/user",
                data: {
                    nickname:this.nickname,
                    email:this.email,
                    password:this.password,
                }
            })
                .then((res) => { 
                  console.log(res.data)
                  alert("수정이 완료되었습니다!")    
                  this.$router.push("/");         
                })
                .catch((err) => console.log(err.response.data));
        },
        Delete() {
          axios({
                method: "delete",
                url: SERVER.URL+"/user",
                data: {
                    nickname:this.nickname
                }
            })
                .then((res) => { 
                  console.log(res.data)
                  alert("회원 탈퇴가 되었습니다")
                  this.logout()    
                  this.$router.push("/");         
                })
                .catch((err) => console.log(err.response.data));
        
        },
        getUserdata() {
            axios({
                method: "get",
                url: SERVER.URL+"/user",
                data: {
                    nickname:this.nickname
                },
            })
                .then((res) => { 
                    this.email = res.data.user.email,
                    this.password = res.data.password,
                    this.profileImg = res.data.user.profileImg                
                })
                .catch((err) => console.log(err.response.data));
        },
    },
    created() {
      this.getUserdata()
    }
}

</script>

<style>
.middle {
    margin-top: 20px;
}
</style>