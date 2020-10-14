<template> 
    <v-container>
        <v-card class="elevation-12">
            <v-toolbar color="deep-orange accent-2" dark flat>
                <v-toolbar-title>공지사항 글 작성</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>   
            <v-form ref="writeForm" v-model="valid" lazy-validation>
            <v-row class="mx-4"> 
                <v-text-field :counter="50" label="제목" name="subject" :rules="[rules.required]" v-model="board_write.subject" maxlength="50" >
                </v-text-field> 
                </v-row> 
                <v-row class="mx-4">  내용 </v-row>
                <v-row class="mx-4"> 
                <v-textarea filled name="content" hint="내용을 입력해주세요." :rules="[rules.required]" v-model="board_write.content" :counter="1000" maxlength="1000" ></v-textarea> 
                </v-row> 
            </v-form>
            <v-card-actions>
                <v-spacer></v-spacer>
            <v-btn block outlined :disabled="!valid" color="blue" @click="writeBoard"> 등록 </v-btn> 
            </v-card-actions>
            </v-card-text>
        </v-card> 
    </v-container> 
</template>

<script>
import axios from "axios";
import SERVER from "@/api/api";
import { mapState } from 'vuex'
export default {
    data () {
        return { 
            valid: true,
            board_write:{
                subject: '', 
                content: '' 
                },
            rules: {
                required: value => !!value || "Required."
                },
            }
    },
    computed:{
        ...mapState(['nickname'])
    },
    methods:{
        writeBoard() {
            axios({
                method: "post",
                url: SERVER.URL+"/feature/board/write",
                data: {
                    subject:this.board_write.subject,
                    content:this.board_write.content,
                    nickname:this.$store.state.nickname
                },
            })
                .then((res) => { 
                    console.log(res.data)
                    alert("글 작성이 성공했습니다.")
                    this.$router.push("/");
                
                })
                .catch((err) => console.log(err.response.data));
            }
        }   
}

</script>