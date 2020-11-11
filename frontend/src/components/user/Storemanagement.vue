<template>
<div id="app">
  <v-app id="inspire">
    <v-card class="mx-auto mb-14"
    >
    <v-container>
    <v-row>
        <v-col cos="12" align="center"
          justify="center">
          예약 대기 리스트
            <v-data-table
                v-model="selected"
                :headers="headers_waitlist"
                :items="reserve_waitlists"
                :single-select="singleSelect"
                item-key="nickname"
                show-select
                class="elevation-1"
            >
            </v-data-table>
        </v-col>
        <hr>
        <v-col cos="12" align="center"
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
        headers_waitlist: [
          {
            text: '예약자',
            align: 'start',
            sortable: false,
            value: 'nickname',
          },
          { text: '날짜', value: 'date' },
          { text: '총 인원', value: 'people_num' },
        ],
        reserve_waitlists: [
          {
            nickname: '',
            data:'',
            people_num:'',
          },
        ],
        headers_list: [
          {
            text: '예약자',
            align: 'start',
            sortable: false,
            value: 'nickname',
          },
          { text: '날짜', value: 'date' },
          { text: '총 인원', value: 'people_num' },
        ],
        reserve_lists: [
          {
            nickname: '',
            data:'',
            people_num:'',
          },
        ],
      }
    },
  }
</script>

<style>

</style>