<template>

<div id="app">
    <v-app>
        <v-dialog v-model="dialog" persistent max-width="600px" min-width="360px">
            <div>
                <v-tabs v-model="tab" show-arrows background-color="deep-purple accent-4" icons-and-text dark grow>
                    <v-tabs-slider color="purple darken-4"></v-tabs-slider>
                    <v-tab v-for="(i, idx) in tabs" :key="idx">
                        <v-icon large>{{ i.icon }}</v-icon>
                        <div class="caption py-1">{{ i.name }}</div>
                    </v-tab>
                    <v-tab-item>
                        <v-card class="px-4">
                            <v-card-text>
                                <v-form ref="loginForm" >
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
                                        <v-col cols="12">
                                        <v-checkbox
                                          v-model="allowSpaces"
                                          label="가게 주인계정 회원가입"
                                        ></v-checkbox>
                                        </v-col>
                                        <v-col v-show="allowSpaces" cols="12">
                                        <p>가게 주소 입력</p>
                                        <p><input type="text" id="sample4_detailAddress" placeholder="가게 이름"  v-model="storeData.name"></p>
                                        <p><input type="number" id="sample4_extraAddress" placeholder="전화번호 ex)023334444" v-model="storeData.tel">
                                        </p>
                                        <input type="button" @click="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
                                        <input type="text" id="sample4_postcode" placeholder="우편번호" :readonly="true" >
                                        <input type="text" id="sample4_roadAddress" placeholder="도로명주소" :readonly="true" >
                                        <span id="guide" style="color:#999;display:none"></span>
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
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
    sample4_execDaumPostcode() {
      new daum.Postcode({
        oncomplete(data) {
          var roadAddr = data.roadAddress; 
                var extraRoadAddr = ''; 

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                  extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                  extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                  extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                  document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                  
                } else {
                  document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                  var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                  var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                  guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            
            }
        }).open();
    },
    ...mapActions(['login','signup']),
    sendstoreinfo() {
      this.storeData.address = document.getElementById("sample4_roadAddress").value
      console.log(this.storeData)
      if (this.storeData) {
      console.log(this.storeData)
      axios({
        method: "post",
        //주소 바꾸기
        url: SERVER.URL +"/feature/storeres/write",
        data: {
          name:this.storeData.name,
          tel:this.storeData.tel,
          address:this.storeData.address,
          nickname:this.nickname,
        }
        })
      } else {
        pass
      }
    },
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
      this.sendstoreinfo()
      
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    }
  },
  data: () => ({
    allowSpaces: false,
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
    storeData:{
      address:"",
      tel:"", 
      name:""
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