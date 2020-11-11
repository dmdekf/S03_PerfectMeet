<template>
  <div id="app">
  <v-app id="inspire">
    <v-card class="mx-auto mb-14"
    >
    <v-container>
    <v-row>
        <v-col cos="12" align="center"
          justify="center">
            {{ nickname }}님의 Profile Page
        <div v-if="(nickname)===this.$store.state.nickname">
            <div class="my-2">
                <v-btn color="primary" v-on:click="userUpdate">회원 정보 수정하기</v-btn>
            </div>
        </div>
        <v-img src="https://picsum.photos/200/300" >
        <v-row class="fill-height">
          <v-card-title class="text_border white--text pl-12 pt-15 text-right ma-5">
          </v-card-title>
        </v-row>
      </v-img>
      <v-list>
          <div v-if="this.likes">
            <v-list-item-icon justify="center">
            <v-icon color="#DC143C" >mdi-heart</v-icon>
            내가 찜한 식당
            </v-list-item-icon>
        <v-divider></v-divider>
            <v-list-item v-for="(like, idx) in likes" 
                
                @click="showresDetail(like.id)"
                :key="idx">
            <v-list-item-content>
                <v-img src="like.image"></v-img>
                <v-list-item-title class="mb-2">#{{idx+1}}. {{like.name}}</v-list-item-title>
                <v-list-item-subtitle>주소 : {{ like.address }}</v-list-item-subtitle>
                <v-list-item-subtitle>전화번호 : {{ like.tel}}</v-list-item-subtitle>
            </v-list-item-content>          
            </v-list-item>  
            <v-divider
        ></v-divider>
        </div>
      </v-list>
      <v-list>
          <div v-if="this.messages">
            <v-list-item-icon justify="center">
            <v-icon  >mdi-android-messages</v-icon>
            메시지 목록
            </v-list-item-icon>
            <v-divider></v-divider>
                <v-data-table
                    :headers="messageHeaders"
                    :items="messages"
                >
                    <template slot="items" slot-scope="props">
                    <td :class="headers[0].class">{{ props.item.sender }}</td>
                    <td :class="headers[1].class">{{ props.item.content }}</td>
                    
                    </template>
                >
                </v-data-table>
            <v-divider></v-divider>
        </div>
      </v-list>
       <v-list>
          <div v-if="this.reserveList">
            <v-list-item-icon justify="center">
            <v-icon  >mdi-android-messages</v-icon>
            예약 목록
            </v-list-item-icon>
            <v-divider></v-divider>
                <v-data-table
                    :headers="reserveHeaders"
                    :items="reserveList"
                   
                >
                    <template slot="items" slot-scope="props">
                    <td :class="headers[0].class">{{ props.item.store_name }}</td>
                    <td :class="headers[1].class">{{ props.item.people_num }}</td>
                    <td :class="headers[2].class">{{ getFormatDate(props.item.reserve_date) }}</td>
                    
                    </template>
                >
                </v-data-table>
            <v-divider></v-divider>
        </div>
      </v-list>
    <v-list>
        <v-list-item-icon justify="center">
            <v-icon color="indigo" class="mr-9">mdi-newspaper-variant-multiple-outline</v-icon>
            작성한 글 목록
        </v-list-item-icon>
        <v-divider></v-divider>
            <v-list-item v-for="(review, idx) in reviews" 
                
                @click="showDetail(review.id)"
                :key="idx">
            <v-list-item-content>
                <v-list-item-title class="mb-2">#{{idx+1}}. 점수 : {{review.score}}</v-list-item-title>
                <v-list-item-subtitle>{{ review.content }}</v-list-item-subtitle>
            </v-list-item-content>
                      
            </v-list-item>  
            <v-divider
        ></v-divider>
      </v-list>
    </v-col>
    </v-row>
    </v-container>
    </v-card>
  </v-app>
</div>
</template>

// <script>
// import Vue from "vue";
import axios from "axios";
// import router from "@/router";
import SERVER from "@/api/api";
import { formatDate } from '@/util/format';

export default {
    name:"Profile",
    props:{
    nickname:{
        type:String,
        required:true,
    },
    },
    data:() => {
        return {
            age:'',
            gender:'',
            reviews:[],
            likes:[],
            profileImg:'',
            messages:[],
            reserveList:[],
            reserveHeaders:[
                {text: '가게 이름', value:'store_name', sortable:true},
                {text: '예약 인원', value:'people_num', sortable:true},
                {text: '예약 날짜', value:'reserve_date', sortable:true},
            ],
            messageHeaders:[
                {text: '보낸 사람', value:'sender', sortable:true},
                {text: '내용', value:'content', sortable:true},
            ]
        }
    },
    methods: {
        userUpdate(){
            const nickname = this.$store.state.nickname
            this.$router.push(`/user/update/${nickname}`);
        },
        getUserdata() {
            axios({
                method: "get",
                url: SERVER.URL+`/user?nickname=${this.nickname}`,
                headers:{
                    nickname: this.nickname
                },
            })
                .then((res) => { 
                    console.log(res.message)
                    this.age = res.data.user.age,
                    this.gender = res.data.user.gender,
                    this.reviews = res.data.reviews,
                    this.profileImg = res.data.user.profileImg                
                })
                .catch((err) => console.log(err.response.data));
        },
        getUserlikes() {
            axios({
                method: "get",
                url: SERVER.URL+"/dibs",
                headers:{
                    nickname: this.nickname
                },
            })
                .then((res) => { 
                    this.likes = res.data.data          
                })
                .catch((err) => console.log(err.response.data));
        },
        getUsermessages() {
            axios({
                method: "get",
                url: SERVER.URL+`/message/getReceiver?receiver=${this.nickname}`,
                headers: {
                    receiver:this.nickname
                },
            })
                .then((res) => { 
                    console.log(res);
                    this.messages = res.data.data.list;           
                })
                .catch((err) => console.log(err.response.data));
        },
        getUserReserves(){
            axios({
                method:"get",
                url: SERVER.URL+'/reserve/userReserveList',
                params:{
                    nickname: this.nickname
                },
            })
            .then((res) =>{
                this.reserveList = res.data.data.list;
                
                console.log(this.reserveList);

                for(var i=0; i<this.reserveList.length; i++ ){
                    
                    this.reserveList[i].reserve_date = this.getFormatDate(this.reserveList[i].reserve_date);
                }
            })
            .catch((err) => console.log(err));
        },
        getFormatDate(date){
            console.log(date);
            return formatDate(date, 'YY.MM.DD HH:mm');
        }
    },
    created() {
        this.getUserdata()
        this.getUserlikes()
        this.getUsermessages()
        this.getUserReserves()
            
    }
}
</script>
<style>
.text_border {
	text-shadow: 0 0 1px black;
}
</style>
