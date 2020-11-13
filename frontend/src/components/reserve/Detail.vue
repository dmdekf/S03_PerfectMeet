<template>
<div id="app">
    <v-app id="inspire">
        <v-window class="mb-6">
        <v-window-item class="my-6">
        <v-card flat>
        <v-card-title class="orange lighten-5">
            <v-list-item>
            <v-list-item-content>
            <v-list-item-title class="headline my-2">음식점 : {{ name }} </v-list-item-title>
            <div ><small>주소 :{{ address }}</small></div>
            <v-list-item-subtitle>전화번호 :  {{ tel }}</v-list-item-subtitle>
            
            <v-row justify="space-around"  class="child-flex">
            <v-row>
            </v-row>
            <v-row>
             <!-- 좋아요 버튼-->
            <div>
                <div v-if="this.likestatus">
                    <v-btn class="mx-2"
                    fab
                    dark
                    small
                    color="pink" v-on:click="unlikeStore(id)" >
                    <v-icon>mdi-heart</v-icon>
                    </v-btn>
                </div>
                <div v-else>
                    <v-btn class="mx-2"
                    fab
                    dark
                    small
                    color="white" v-on:click="likeStore(id)">
                    <v-icon color="pink" >mdi-heart</v-icon>
                    </v-btn>
                </div>
            </div>
                <v-btn class="mx-2"
                fab
                dark
                small
                @click="handleClickButton"><v-icon >mdi-calendar</v-icon></v-btn>
                <app-my-modal
                    title="예약 정보를 입력해주세요."

                    :visible.sync="visible">
                <div>
                    This is modal body
                </div>
                </app-my-modal>
            </v-row>
                
            </v-row>
            </v-list-item-content>
            </v-list-item>
        </v-card-title> 
        </v-card>
    </v-window-item>
    </v-window>
</v-app>
</div>
</template>

<script>
import WriteForm from "./MyModal.vue";
import axios from 'axios';
import SERVER from "@/api/api";
export default {
    props:{
        id:{
            type:Number,
            required:true,
        },
    },
    data: () => {
        return {
            name: '', 
            address: '',
            tel: '',
            image:'',
            likestatus:false,
            reivews:[],
            visible: false,
    }},
    components: {
        appMyModal: WriteForm
    },
    methods:{
        writeReview(storeId) {
            this.$router.push(`/review/${storeId}/write`);
        },
        deleteReivew(reviewid) {
            axios({
                method: "delete",
                url : SERVER.URL +`/feature/review/delete/${reviewid}`,
                data:{
                    id:reviewid
                }                    
            })
            .then(  
                (res) => {
                console.log(res.data)
                alert('리뷰가 삭제되었습니다.')
            })     
            .catch((err) => console.error(err));
        },
        likeStore(){
            axios({
                method: "post",
                url : SERVER.URL +"/dibs",
                data:{
                    nickname:this.$store.state.nickname,
                    sid:this.id
                }                    
            }).then(
                    this.likestatus = !this.likestatus                        
                )
        },
        unlikeStore(){
            axios({
                method: "delete",
                url : SERVER.URL +"/dibs",
                data:{
                    nickname:this.$store.state.nickname,
                    sid:this.id
                }                    
            }).then(
                    this.likestatus = !this.likestatus                        
                )
        }, 
        userProfile(nickname) {
            this.$router.push(`/user/profile/${nickname}`);
        },
        getStore() {
            axios
            .get(SERVER.URL +"/feature/storeres/detail/"+this.id)
            .then((res) => {
                console.log(res.data)
                this.name = res.data.data.name;
                this.address = res.data.data.address;
                this.tel = res.data.data.tel
                this.image = res.data.data.image
            })
            .catch((err) => console.error(err));
        },
        getLike() {
            axios
            .get(SERVER.URL +"/dibs/"+this.id)
            .then((res) => {
                this.likestatus = res.data
            })
            .catch((err) => console.error(err));
        },
        getReviews() {
            axios
            .get(SERVER.URL +`/feature/review/${this.id}/list`)
            .then((res) => {
                this.reivews = res.data
            })
            .catch((err) => console.error(err));
        },
        doc_del_rendar(){
            this.$modal.show(WriteForm,{
                hot_table : 'data',
                modal : this.$modal },{
                name: 'dynamic-modal',
                width : '430px',
                height : '230px',
                draggable: true,
            })
        },
        handleClickButton(){
            this.visible = !this.visible
        } 
    },
    created() {
        this.getStore()
        this.getLike()
        this.getReviews()
    },
}
</script>

<style>

</style>