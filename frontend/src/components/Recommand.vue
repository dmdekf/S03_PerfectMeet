<template>
  <div>
      <template v-if="selectType==0">
          <div>
              <SelectMap 
                @next="locationNext"
              />
          </div>
      </template>
      <template v-if="selectType==1">
          <div>
              <SelectUserInfo
                @prev="infoPrev"
                @next="infoNext"
              />
          </div>
      </template>
      <template v-if="selectType==2">
          <div>
              <SelectUserPurpose
                @prev="purposePrev"
                @next="purposeNext"
              />
          </div>
      </template>
      <template v-if="selectType==3">
          <div>
              <Result 
                :loc="location"
                :pur="purpose"
              />
          </div>
      </template>
  </div>
</template>

<script>
import SelectMap from '@/components/SelectMap.vue';
import SelectUserInfo from '@/components/SelectUserInfo.vue';
import SelectUserPurpose from '@/components/SelectUserPurpose.vue';
import Result from '@/components/Result.vue';

export default {
    components:{
        SelectMap,
        SelectUserInfo,
        SelectUserPurpose,
        Result
    },
    data: () => {
        return {
            selectType: 0,
            location:"",
            purpose:0,
        }
    },
    methods: {
        locationNext(loc){
            this.selectType = 1;
            this.location = loc;
        },
        infoNext(){
            this.selectType = 2;
        },
        infoPrev(){
            this.selectType = 1;
        },
        purposeNext(pur){
            this.selectType = 3;
            this.purpose = pur*1;
        },
        purposePrev(){
            this.selectType = 2;
            this.purpose = 0;
        }
    },
}
</script>

<style>

</style>