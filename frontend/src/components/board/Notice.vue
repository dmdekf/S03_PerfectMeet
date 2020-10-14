<template> 
    <v-container class="p-0"> 
        
        <v-row class="my-2"> 
            <v-spacer></v-spacer>
            <v-btn class="mr-3" color="primary" @click="writeClick" > 작성 </v-btn>
        </v-row>
        <v-data-table :headers="headers" :items="board_lists" :items-per-page="5" class="elevation-1" @click:row="showDetail"  > 
        <template v-slot:item.created="{ item }">
        <span>{{new Date(item.created).toLocaleString()}}</span>
        </template>
        </v-data-table> 
        
    </v-container> 
</template> 
<script> 
import { mapState, mapActions } from 'vuex'
export default { 
    methods: { 
        ...mapActions(['getBoardLists']),
        writeClick() {
             this.$router.push('/board/write')
        },
        showDetail(item){
            console.log(item.id)
            this.$router.push(`/board/detail/${item.id}`);
        }
    },
    computed:{
        ...mapState(['board_lists'])
    },
    data () { 
        return { 
            selectedId:"",
            headers: [ { 
                text: 'Number', 
                align: 'left',
                 sortable: false,
                  value: 'id', 
            }, { 
                text: '제목', 
                value: 'subject' 
            }, {
                text: '작성 시간',
                value: 'created' 
            }, {
                text: '조회수',
                value:'hit'
            }], 
            } 
        },
    mounted(){
        this.getBoardLists()
        console.log(this.board_lists)
    },

}
</script> 