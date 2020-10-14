<template>
    <div class="map-road">
        <div class="map-road-step-wrap"> 
            
            <div class="map-road-sub-copy">누구를 위한<br>맛집을 찾고 있나요?</div>
            <div class="map-road-choice step03">
                    <div class="item item01">
                        <label for="forWho1">
                            <input type="radio" id="forWho1" name="forWho" value="6">
                                <i>
                                    <span class="img sex02"></span>
                                    <strong>
                                            상견례
                                    </strong>
                                </i>
                        </label>
                    </div>
                
                    
                    <div class="item item02">
                        <label for="forWho2">
                            <input type="radio" id="forWho2" name="forWho" value="7">
                            <i>
                                <span class="img"></span>
                                <strong>
                                        회식
                                </strong>
                            </i>
                        </label>
                    </div>
                
                    <div class="item item03">
                        <label for="forWho3">
                            <input type="radio" id="forWho3" name="forWho" value="8">
                                <i>
                                <span class="img"></span>
                                    <strong>
                                            데이트
                                    </strong>
                                </i>
                        </label>
                    </div>
                
                    
                    <div class="item item04">
                        <label for="forWho4">
                            <input type="radio" id="forWho4" name="forWho" value="9">
                                <i>
                                    <span class="img"></span>
                                    <strong>
                                        가족 모임
                                    </strong>
                                </i>
                        </label>
                    </div>
                
            </div>
            <div class="align-area">
                <div class="center-area">
                    <a href="#" class="btn round big gray" @click="prev">이전</a>
                    <a href="#" class="btn round big red" id="btnNext" @click="next">다음</a>
                </div>
            </div>
        </div>
    </div>
</template> 
<script>
export default {
    methods: {
        prev(){
            this.$emit('prev');
        },
        next(){
            var p = document.querySelector("input[name=forWho]:checked").value;
            this.$emit('next', p);
        }
    },
}
</script>
<style scoped>
.map-road-choice { margin-top: 20px; text-align: center; }
.map-road-choice .item { display: inline-block; vertical-align: top; margin-top: 20px; }
.map-road-choice .item:nth-child(even) { margin-left: 35px; }
.map-road-choice .item label { cursor: pointer; }
.map-road-choice .item input { visibility: hidden; position: absolute; top: 0; left: 0; width: 1px; height: 1px; border: none; }
.map-road-choice .item i { display: block; padding-bottom: 10px; border: 3px #fff solid; border-radius: 13px; }
.map-road-choice .item i .img { display: block; width: 237px; height: 150px; background-repeat: no-repeat; background-position: 50% 50%; background-size: contain; }/* 200525 */


.map-road-choice.step03 .item.item01 i .img { background-image: url("../assets/img/wedding.png"); } 
.map-road-choice.step03 .item.item02 i .img { background-image: url("../assets/img/teammate.png"); } 
.map-road-choice.step03 .item.item03 i .img { background-image: url("../assets/img/couple.png"); }
.map-road-choice.step03 .item.item04 i .img { background-image: url("../assets/img/family.png"); }

.map-road-choice .item input:checked + i { border-width: 3px; border-style: solid; border-color: #d61d2a; color: #d61d2a; }
.map-road-choice .item i em { display: block; font-family: Moebius; font-size: 16px; color: #666; }
.map-road-choice .item i strong { display: block; font-family: Moebius; font-size: 20px;  line-height: 1; }
@media screen and (max-width: 768px) {
	.map-road-choice { margin-top: 15px; }
	.map-road-choice .item { width: 48%; margin-top: 15px; }
	.map-road-choice .item:nth-child(even) { margin-left: 2%; }
	.map-road-choice .item i { padding-bottom: 5px; padding-top: 5px; }
	.map-road-choice .item i .img { width: 100%; height: 120px; }
	.map-road-choice .item i em { font-size: 14px; }
	.map-road-choice .item i strong { font-size: 16px; }
}
.map-road-sub-copy { margin-top: 0; font-size: 24px; }
.map-road-sub-copy { margin-top: 40px; font-size: 30px; font-family: Moebius; font-weight: 800; text-align: center; }

.align-area {
    text-align: center;
    margin-top: 60px;
}

</style>