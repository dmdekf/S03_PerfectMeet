<template>
    <v-app id="inspire">
    <v-main class="mx-10px">
    <div class="map-road">

            <section class="post-list" >
            <div v-for="(store, id) in storesList" :key="id">
                <div class="map-road-result" v-if="store.id+1">
                    <a style="color: black" @click="storeDetail(store.id)">
                        <div class="item">
                            <div class="name"><h3>{{store.name}}</h3></div>
                            <div class="address"><v-icon>mdi-home-variant</v-icon>{{store.address}}</div>
                            <div class="tel"><v-icon>mdi-deskphone</v-icon>{{store.tel}}</div>
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
import SERVER from "@/api/api";
export default { 
    props:{
        id:{
            type:Number,
        },
    },
    data: () => {
        return {
            storesList:[],
            photos: [
                '../../assets/img/gold-medal.png',
                '../../assets/img/silver-medal.png',
                '../../assets/img/bronze-medal.png',
            ],
        }
    },
   
    mounted(){
        this.getStores();
    },
    methods: {
        storeDetail(id) {
            this.$router.push(`/reserve/detail/${id}`);
        },
        getStores() {
            axios.get(SERVER.URL+"/feature/storeres/list")
            .then((res)=>{
                    if(res.data) {
                        console.log(res.data);
                        this.id = res.data.id;
                        this.storesList = res.data;  
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