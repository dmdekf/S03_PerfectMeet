<template>
<div id="app">
  <v-app id="inspire">
    <v-card class="mx-auto mb-14"
    >
    <v-container>
    <v-row>
        <v-col cols="6" align="center"
          justify="center">
          예약 대기 리스트
            <v-data-table
                :headers="headers_waitlist"
                :items="reserve_waitlists"
                item-key="nickname"
            >
            <template slot="items" slot-scope="props">
              <td :class="headers[0].class">{{ props.item.nickname }}</td>
              <td :class="headers[1].class">{{ props.item.people_num }}</td>
              <td :class="headers[2].class">{{ props.item.reserve_date }}</td>
              <td :class="headers[3].class">
                <v-btn
              color="primary"
              fab
              small
              dark
              @click="toreservelist"
            >
              <v-icon>mdi-check-bold</v-icon>
            </v-btn>
              </td>
              <td :class="headers[4].class">
                <v-btn
              color="red accent-4"
              fab
              small
              dark
              @click="toremovewait"
            >
              <v-icon>mdi-close-thick</v-icon>
            </v-btn>
              </td>
            </template>
            </v-data-table>
        </v-col>
        <v-col cols="6" align="center"
          justify="center">
          예약 확정 리스트
            <v-data-table
                :headers="headers_list"
                :items="reserve_lists"
                item-key="nickname"
                class="elevation-1"
            >
            </v-data-table>
        </v-col>
    </v-row>
    </v-container>
    </v-card>
  </v-app>
</div>
</template>

<script>
import axios from "axios";
// import router from "@/router";
import SERVER from "@/api/api";
import { formatDate } from '@/util/format';

export default {
    methods: {
        getreservewaitlists() {
            axios({
                method: "get",
                url: SERVER.URL+`/reserve/reserveWait?store_id=3`,
                
            })
                .then((res) => { 
                    console.log(res);
                    this.reserve_waitlists = res.data.data.list;  
                    
                     for(var i=0; i<this.reserve_waitlists.length; i++ ){
                    
                        this.reserve_waitlists[i].reserve_date = this.getFormatDate(this.reserve_waitlists[i].reserve_date);
                     }
                })
                .catch((err) => console.log(err.response.data));
        },
        sendmsg(content, receiver) {
          axios({
                method:"post",
                url: SERVER.URL+'/message/sendMessage',
                params:{
                    receiver: receiver,
                    content: content,
                    sender: this.$store.state.store_id
                },
            })
        },
        toreservelist(props) {
          axios({
                method:"delete",
                url: SERVER.URL+'/reserve/removeWait',
                params:{
                    nickname: props.nickname,
                    reserve_time: props.reserve_time,
                    store_id: this.$store.state.store_id
                },
            })
          .then(
            this.sendmsg("예약이 확정되었습니다.", props.nickname),
            axios({
                method:"post",
                url: SERVER.URL+'/reserve/addList',
                params:{
                    nickname: props.nickname,
                    time: props.reserve_time,
                    store_id: this.$store.state.store_id,
                    people_num:props.people_num
                },
            })
          )
          .catch((err) => console.log(err));

          var index = this.reserve_waitlists.indexOf(props)
          this.reserve_waitlists.splice(index, 1)
          alert("예약을 승인했습니다.")
        },
        toremovewait(props) {
          axios({
                method:"delete",
                url: SERVER.URL+'/reserve/removeWait',
                params:{
                    nickname: props.nickname,
                    reserve_time: props.reserve_time,
                    store_id: this.$store.state.store_id
                },
            })
          .then(
            this.sendmsg("예약이 취소되었습니다.", props.nickname)
          )
          .catch((err) => console.log(err));
        var index = this.reserve_waitlists.indexOf(props)
            this.reserve_waitlists.splice(index, 1)
            alert("예약을 거절했습니다.")        
        },
        getreservelists() {
            axios({
                method: "get",
                url: SERVER.URL+`reserve/reserveList?store_id=${this.$store.state.store_id}`,
            })
                .then((res) => { 
                    console.log(res);
                    this.reserve_lists = res.data.data.list;   
                    
                    for(var i=0; i<this.reserve_lists.length; i++ ){
                    
                    this.reserve_lists[i].reserve_date = this.getFormatDate(this.reserve_lists[i].reserve_date);
                }
                })
                .catch((err) => console.log(err.response.data));
        },
        getFormatDate(date){
            return formatDate(date, 'YY.MM.DD HH:mm');
        },
    },
    created (){
      this.getreservewaitlists()
      this.getreservelists
    },
    data: () => {
      return {
        selected: [],
        selectreservewaitId:'',
        headers_waitlist: [
          {
            text: '예약자',
            align: 'start',
            sortable: false,
            value: 'nickname',
          },
          { text: '예약 날짜', value: 'reserve_date' },
          { text: '예약 인원', value: 'people_num' },
          { text: '수락', value: 'button_1' },
          { text: '거절', value: 'button_0' }
        ],
        reserve_waitlists: [
          
        ],
        headers_list: [
          {
            text: '예약자',
            align: 'start',
            sortable: false,
            value: 'nickname',
          },
          { text: '예약 날짜', value: 'reserve_date' },
          { text: '예약 인원', value: 'people_num' },
          { text: '완료', value: 'button_1' },
          { text: '삭제', value: 'button_0' }
        ],
        
        reserve_lists: [
          
        ],
      }
    },
  }
</script>

<style>

</style>