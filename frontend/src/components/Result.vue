<template>
    <v-app id="inspire">
    <v-main class="mx-10px">
    <div class="map-road">
            <section class="post-list" >
            <div v-for="(post, id) in storeList" :key="id">
                <div class="map-road-result" v-if="post.id">
                    <a style="color: black" @click="storeDetail(post.id)">
                        <div class="item">
                            <div class="recom">추천{{id+1}}</div><br/>
                            
                                <img v-if="id==0" src="../assets/img/gold-medal.png">
                                <img v-if="id==1" src="../assets/img/silver-medal.png">
                                <img v-if="id==2" src="../assets/img/bronze-medal.png">
                            
                            <div class="name"><h3>{{post.name}}</h3></div>
                            <div class = "address"><v-icon>mdi-home-variant</v-icon>{{post.address}}</div>
                            <div class = "tel"><v-icon>mdi-deskphone</v-icon>{{post.tel}}</div>
                            <div class="review"><v-icon>mdi-comment-multiple-outline</v-icon> review </div>
                            <hr>
                        </div> 
                    </a>
                </div>
            </div>
            </section>
        </div>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
//import SERVER from "@/api/api";
export default {
    props:{
        loc:{
            type:String,
            required:true
        },
        pur:{
            type:Number,
            required:true
        }
    },
    data: () => {
        return {
            storeList:[],
            photos: [
                '../assets/img/gold-medal.png',
                '../assets/img/silver-medal.png',
                '../assets/img/bronze-medal.png',
            ],
            limit:0,
            url:"",
        }
    },
   
    mounted(){
        this.getStores()
    },
    methods: {
        storeDetail(id) {
            this.$router.push(`/stores/detail/${id}`);
        },
        getStores() {
            axios.get("http://j3a507.p.ssafy.io:8399/stores?loc="+this.loc+"&pur="+this.pur)
            .then((res)=>{
                    if(res.data) {
                        console.log(res.data);
                        this.storeList = res.data.data;  
                    }
            })
            .catch((err) => console.error(err));
        },
        
    },
}
</script>

<style scoped>
.map-road-result .item { margin-top: 20px; text-align: center; }
.map-road-result .recom { 
    display: inline-block; padding: 0 30px; height: 40px; line-height: 40px; font-size: 18px; color:black; background-color: pink; border-radius: 99em; 
    
}
.map-road-result .item .address { font-size: 20px; color: black; line-height: 1;
background-size: 17px 17px; }
.map-road-result .item .review { font-size: 20px; color: black; line-height: 1; align-self: right;
background-size: 17px 17px; }
</style>