<template>
    <div class="row">
        <modals-container />
    </div>
</template>
<script>
import WriteForm from './MyModal.vue'
export default {
    mounted(){
        this.doc_del_rendar();
    }
    ,methods : {
        doc_del_rendar(){
            this.$modal.show(WriteForm,{
                hot_table : 'data',
                modal : this.$modal },{
                name: 'dynamic-modal',
                width : '430px',
                height : '230px',
                draggable: true,
            })
        }   
    }
}
</script>