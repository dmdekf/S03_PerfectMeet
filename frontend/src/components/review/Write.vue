<template> 
    <v-container>
        <v-card class="elevation-12">
            <v-toolbar color="deep-orange accent-2" dark flat>
                <v-toolbar-title>리뷰 글 작성</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>   
            <v-form ref="writeForm" v-model="valid" lazy-validation>
            <v-row class="mx-4"> 
                <v-card-text>
                <v-slider
                    v-model="review_write.score"
                    max="5"
                    step="1"
                    thumb-label="always"
                ></v-slider>
                </v-card-text> 
                </v-row> 
                <v-row class="mx-4"> 내용 </v-row>
                <v-row class="mx-4"> 
                <v-textarea filled name="content" hint="내용을 입력해주세요." :rules="[rules.required]" v-model="review_write.content" :counter="1000" maxlength="1000" ></v-textarea> 
                </v-row> 
            </v-form>
            <v-card-actions>
                <v-spacer></v-spacer>
            <v-btn block outlined :disabled="!valid" color="blue" @click="writeReview"> 등록 </v-btn> 
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
    props:{
        storeId:{
            type:String,
            required:true,
        },
    },
    data () {
        return { 
            review_write:{
                img:'', 
                content: '',
                score:0,
                }
            }
    },
    computed:{
        ...mapState(['nickname'])
    },
    methods:{
        writeboard() {
            axios({
                method: "post",
                url: SERVER.URL+"/feature/review/write",
                data: {
                    img: this.review_write.img,
                    score:this.review_write.score,
                    storeId: this.storeId,
                    content:this.review_write.content,
                    nickname:this.$store.state.nickname
                },
            })
                .then((res) => { 
                    console.log(res.data)
                    alert("리뷰 작성이 성공했습니다.")
                    this.$router.push("/");
                
                })
                .catch((err) => console.log(err.response.data));
            }
        }   
}

</script>