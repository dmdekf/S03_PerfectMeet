<template>
  <div class="my-modal"
    v-if="visible" @click.self="handleWrapperClick">
    <div class="my-modal__dialog">
      <header class="my-modal__header">
        <span>{{title}}</span>
      </header>
      <div class="my-modal__body">
          
                <v-row align="center">
                    <v-col
                    sm="6"
                    class="my-2 px-1"
                    >
                    <v-date-picker
                        ref="picker"
                        v-model="date"
                        :picker-date.sync="pickerDate"
                        full-width
                    ></v-date-picker>
                    </v-col>
                    
                    <v-col>
                        <v-time-picker
                        v-model="start"
                        ></v-time-picker>
                    </v-col>
     
                    <v-col
                        class="d-flex"
                    >
                        <v-select
                        :items="items"
                        label="인원수"
                        v-model="people_num"
                        outlined
                        ></v-select>
                    </v-col>

                    
                </v-row>
                 <v-btn block outlined color="blue" @click="writeBoard"> 등록 </v-btn> 
                
      </div>
      <div class="my_modal__footer">
          <button @click="$emit('update:visible', !visible)" aria-flowto="">Close</button>
      </div>
    </div>
    
    </div>
</template>

<script>

import axios from "axios";
import SERVER from "@/api/api";
import { formatDate } from '@/util/format';

export default {
  name: 'my-modal',
  computed:{
      ...mapState(['nickname'])
  },
  data: () => ({
      date: new Date().toISOString().substr(0, 10),
      pickerDate: null,
      notes: [],
      items:['3','4','5','6','7','8','9','10'],
      start: null,
      people_num: 0,
      concat_time: null,
    }),
    watch: {
      pickerDate () {
        this.notes = [
          this.allNotes[Math.floor(Math.random() * 5)],
          this.allNotes[Math.floor(Math.random() * 5)],
          this.allNotes[Math.floor(Math.random() * 5)],
        ].filter((value, index, self) => self.indexOf(value) === index)
        
      },
    },
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false
    },
    title: {
      type: String,
      require: false,
    },
    nickname:{
      type:String,
      required:true,
    } 
  },
  methods: {
    handleWrapperClick(){
      this.$emit('update:visible', false)
    },
    writeBoard(){
      // console.log(this.date)
      // console.log(this.start)
      // console.log(this.people_num)
      let time = this.date.concat(' ',this.start)
      this.concat_time = this.getFormatDate(new Date(time))
      // console.log(new Date(time))
      console.log(time)
      console.log(typeof(time))
      axios({
                method: "post",
                url: SERVER.URL+'/reserve/addWait',
                data:{
                    nickname: this.$store.state.nickname,
                    store_id: this.$store.state.store_id,
                    date: new Date(this.concat_time),
                    people_num: this.people_num
                },
            })
                .then((res) => { 
                    console.log(res)          
                })
                .catch((err) => console.log(err));
    },
    getFormatDate(date){
            return formatDate(date, 'YYYY.MM.DD HH:mm');
        },
  },
} 
</script>

<style lang="scss">
$module: 'my-modal';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  position: center;
  overflow: auto;
  margin: 0;
  //This is modal layer
  &__dialog{
    top: 75px;
    
    position: absolute;
    background: #fff;
    margin-bottom: 50px;
  }

  &__header {
    font-size: 28px;
    font-weight: bold;
    line-height: 1.29;
    padding: 16px 16px 0 25px;
    position: relative;
  }
  &__body {
    padding: 25px;
    overflow-y: scroll;
  }
  &__footer {
    font-size: 28px;
    font-weight: bold;
    padding: 16px 16px 25 25px;
    position: relative;
  }
}
</style>