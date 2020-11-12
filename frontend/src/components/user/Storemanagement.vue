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
            >
              <v-icon>mdi-close-thick</v-icon>
            </v-btn>
              </td>
            </template>
            </v-data-table>
        </v-col>
        <v-col cols="6" align="center"
          justify="center">
          예약 리스트
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
          예약 대기 관리
        </v-card-title>

        <v-card-text>
          해당 예약을 수락 하시겠습니까?
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="orange darken-1"
            text
            @click="deleteMessage()"
          >
            예약 수락
          </v-btn>

          <v-btn
            color="orange darken-1"
            text
            @click="dialog = false"
          >
            예약 삭제
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
export default {
    methods: {
        getreservewaitlists() {
            axios({
                method: "get",
                url: SERVER.URL+`reserve/reserveWait?store_id=${this.$store.state.store_id}`,
            })
                .then((res) => { 
                    console.log(res);
                    this.reserve_waitlists = res.data.data.list;           
                })
                .catch((err) => console.log(err.response.data));
        },
        reservewaitClick(value) {
            this.selectreservewaitId = value.id;
            this.dialog=true;
        
        },
        getreservelists() {
            axios({
                method: "get",
                url: SERVER.URL+`reserve/reserveList?store_id=${this.$store.state.store_id}`,
            })
                .then((res) => { 
                    console.log(res);
                    this.reserve_lists = res.data.data.list;           
                })
                .catch((err) => console.log(err.response.data));
        }
    },
    data: () => {
      return {
        singleSelect: 'True',
        selected: [],
        selectreservewaitId:'',
        headers_waitlist: [
          {
            text: '예약자',
            align: 'start',
            sortable: false,
            value: 'nickname',
          },
          { text: '예약 날짜', value: 'date' },
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
          { text: '예약 날짜', value: 'date' },
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