<template>

<div id="app">
    <v-app>
        <v-dialog v-model="dialog" persistent max-width="600px" min-width="360px">
            <div>
                <v-tabs v-model="tab" show-arrows background-color="deep-purple accent-4" icons-and-text dark grow>
                    <v-tabs-slider color="purple darken-4"></v-tabs-slider>
                    <v-tab v-for="i in tabs" :key="i">
                        <v-icon large>{{ i.icon }}</v-icon>
                        <div class="caption py-1">{{ i.name }}</div>
                    </v-tab>
                    <v-tab-item>
                        <v-card class="px-4">
                            <v-card-text>
                                <v-form ref="loginForm" v-model="valid" lazy-validation>
                                    <v-row max-width="500px">
                                        <v-col cols="12">
                                            <v-text-field v-model="loginData.loginEmail" :rules="loginEmailRules" label="E-mail" required></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field v-model="loginData.loginPassword" :append-icon="show1?'eye':'eye-off'" :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Password" hint="At least 8 characters" counter @click:append="show1 = !show1"></v-text-field>
                                        </v-col>
                                        <v-col class="d-flex" cols="12" sm="6" xsm="12">
                                        </v-col>
                                    </v-row>
                                </v-form>
																<v-card-actions>
																	<v-spacer></v-spacer>
																	<v-btn color="red"  @click="!dialog" to="/">Close</v-btn>
																	<v-btn :disabled="!valid" color="success" @click="loginvalidate(loginData)"> Login </v-btn>
																</v-card-actions>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                    <v-tab-item>
                        <v-card class="px-4">
                            <v-card-text>
                                <v-form ref="registerForm" v-model="valid" lazy-validation>
                                    <v-row>
                                        <v-col cols="12">
                                            <v-text-field v-model="signupData.email" :rules="signupEmailRules" label="E-mail" required></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="6">
                                            <v-text-field v-model="signupData.nickname" :rules="[rules.required]" label="Nick Name(영어만 허용됩니다)" maxlength="20" required></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field v-model="signupData.password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, rules.min]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Password" hint="숫자+대문자+소문자+8글자이상이어야합니다." counter @click:append="show1 = !show1"></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field block v-model="signupData.verify" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'" :rules="[rules.required, passwordMatch]" :type="show1 ? 'text' : 'password'" name="input-10-1" label="Confirm Password" counter @click:append="show1 = !show1"></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-form>
																<v-card-actions>
																	<v-spacer></v-spacer>
																	<v-btn color="red"  @click="!dialog" to="/">Close</v-btn>
																	<v-btn :disabled="!valid" color="success" @click="signupvalidate(signupData)">Register</v-btn>
																</v-card-actions>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                </v-tabs>
            </div>
        </v-dialog>
    </v-app>
</div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/api";
import { mapState, mapActions } from 'vuex';
export default {
  computed: {
    ...mapState,
    passwordMatch() {
      return () => this.signupData.password === this.signupData.verify || "Password must match";
    }
  },
  methods: {
    ...mapActions(['login','signup']),
    onUpload() {
      console.log(this.imagefile.name)
      axios({
        method: "post",
        url: SERVER.URL +"/feature/upload/uploadFile",
        data: {
          file : this.imagefile,
        },
      })
        .then((res) => {
            console.log(res.data.fileName)
        })
        .catch((err) => console.error(err));
    },
    loginvalidate(loginData) {
      if (this.$refs.loginForm.validate()) {
        this.login(loginData)
      }
      else {
        alert("잘못된 접근입니다.")
      }
    },
    signupvalidate(signupData) {
      console.log(signupData)
      this.signup(signupData)
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data: () => ({
    imagefile:null,
    imageUrl:null,
    dialog: true,
    tab: 0,
    tabs: [
        {name:"Login", icon:"mdi-account"},
        {name:"Register", icon:"mdi-account-outline"}
    ],
    valid: true,
    signupData:{
      nickname: "",
      email: "",
      password: "",
      verify:""
    },
    verify: "",
    key:"",
    loginData:{
      loginPassword: "",
      loginEmail: ""
    },
    loginEmailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    signupEmailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],

    show1: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 8) || "Min 8 characters"
    },
    filerules: [
        value => !value || value.size < 200000000 || 'Avatar size should be less than 200 MB!',
      ],
  })
}
</script>
<style scoped>

</style>