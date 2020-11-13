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
                @click:row="waitreserveClick(props)"
            >
            <template slot="items" slot-scope="props">
              
              <td :class="headers[0].class">{{ props.item.nickname }}</td>
              <td :class="headers[1].class">{{ props.item.people_num }}</td>
              <td :class="headers[2].class">{{ props.item.reserve_date }}</td>
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
  <v-row justify="center">
    
    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">
          예약 승인 
        </v-card-title>

        <v-card-text>
          예약을 승인하시겠습니까??
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              class="ml-2"
              color="primary"
              fab
              small
              dark
              @click="toreservelist"
            >
              <v-icon>mdi-check-bold</v-icon>예약 승인
          </v-btn>
          <v-btn
              color="red accent-4"
              fab
              small
              dark
              @click="toremovewait"
            >
              <v-icon>mdi-close-thick</v-icon>예약 거절
            </v-btn>

          <v-btn
            color="orange darken-1"
            text
            @click="dialog = false"
          >
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</div>
</template>

<script>
import axios from "axios";
// import router from "@/router";
import SERVER from "@/api/api";
import { formatDate } from '@/util/format';

export default {
    methods: {
      waitreserveClick(props){
            this.selected = props
            this.dialog=true;

        },
        getreservewaitlists() {
            axios({
                method: "get",
                url: SERVER.URL+`/reserve/reserveWait?store_id=${this.$store.state.store_id}`,
                
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
        toreservelist() {
          axios({
                method:"delete",
                url: SERVER.URL+'/reserve/removeWait',
                params:{
                    nickname: this.selected.nickname,
                    reserve_time: this.selected.reserve_time,
                    store_id: this.$store.state.store_id
                },
            })
          .then(
            this.sendmsg("예약이 확정되었습니다.", this.selected.nickname),
            axios({
                method:"post",
                url: SERVER.URL+'/reserve/addList',
                params:{
                    nickname: this.selected.nickname,
                    time: this.selected.reserve_time,
                    store_id: this.$store.state.store_id,
                    people_num:this.selected.people_num
                },
            })
          )
          .catch((err) => console.log(err));
          this.selected = []
          this.dialog = false;
          window.location.reload();
          alert("예약을 승인했습니다.")
          
        },
        toremovewait() {
          axios({
                method:"delete",
                url: SERVER.URL+'/reserve/removeWait',
                params:{
                    nickname: this.selected.nickname,
                    reserve_time: this.selected.reserve_time,
                    store_id: this.$store.state.store_id
                },
            })
          .then(
            this.sendmsg("예약이 취소되었습니다.", this.selected.nickname)
          )
          .catch((err) => console.log(err));
          this.selected = []
          this.dialog = false;
          window.location.reload();
          alert("예약을 거절했습니다.")        
        },
        getreservelists() {
            axios({
                method: "get",
                url: SERVER.URL+`/reserve/reserveList?store_id=${this.$store.state.store_id}`,
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
      this.getreservelists()
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
        ],
        dialog:false,
        reserve_lists: [
          
        ],
      }
    },
  }
</script>

<style>

</style>