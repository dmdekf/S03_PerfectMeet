<template>
  <div class="map-road">
    <div class="map-road-step-wrap">
      <div class="map-road-step-progress">
        <span class="step01 pass">
          <i></i>
        </span>
        <span class="step02 current">
          <i></i>
        </span>
        <span class="step03">
          <i></i>
        </span>
        <span class="step04">
          <i></i>
        </span>
      </div>

      <div class="map-road-user-info">
        <div class="sex">
          <h3>1. 당신의 성별은?</h3>
          <!-- 200516 텍스트 수정 -->
          <div class="male">
            <label for="male">
              <input type="radio" id="male" name="gender" value="MALE" />
              <i>남성</i>
            </label>
          </div>
          <div class="female">
            <label for="female">
              <input type="radio" id="female" name="gender" value="FEMALE" />
              <i>여성</i>
            </label>
          </div>
        </div>
        <div class="age">
          <div class="age">
            <h3>2. 당신의 나이는?</h3>
            <span class="form">
              <input type="number" oninput="maxLengthCheck(this)" maxlength="3" id="age" /> 세
            </span>
            <p>
              성인 유저의 데이터를 활용합니다.
              <br />(20세 이상 입력)
            </p>
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
  </div>
</template>
<script>
export default {
  methods: {
    prev(){
      this.$emit('prev');
    },
    next(){
      this.$emit('next');
    }
  },
}
</script>
<style scoped>
.align-area {
  margin-top: 60px;
}
.map-road-user-info .sex > div.male i {
  background-image: url(../assets/img/ico_male.png);
}
.map-road-user-info .sex > div.female i {
  background-image: url(../assets/img/ico_female.png);
}
.map-road-user-info {
  text-align: center;
}
.map-road-user-info h3 {
  margin: 35px 0 25px;
  font-family: Moebius;
  font-weight: 700;
  font-size: 30px;
}
.map-road-user-info .sex {
  text-align: center;
}
.map-road-user-info .sex > div {
  display: inline-block;
  vertical-align: middle;
}
.map-road-user-info .sex > div + div {
  margin-left: 5%;
} /* 200526 suwon */
.map-road-user-info .sex > div label {
  cursor: pointer;
}
.map-road-user-info .sex > div input {
  visibility: hidden;
  position: absolute;
  top: 0;
  left: 0;
  width: 1px;
  height: 1px;
  border: none;
}
.map-road-user-info .sex > div i {
  display: block;
  width: 150px;
  padding-top: 135px;
  border: 3px #fff solid;
  border-radius: 13px;
  background-repeat: no-repeat;
  background-position: 50% 10px;
  font-size: 20px;
  font-weight: 700;
  opacity: 0.2;
}

.map-road-user-info .sex > div input:checked + i {
  opacity: 1;
  border-width: 3px;
  border-style: solid;
  border-color: #d61d2a;
}

.map-road-user-info .age .form {
  display: inline-block;
  font-size: 28px;
}
.map-road-user-info .age .form input {
  width: 100px;
  font-size: 34px;
  font-weight: 800;
  text-align: center;
  vertical-align: middle;
  border: none;
  border-bottom: 1px #333 solid;
}
.map-road-user-info .age p {
  margin-top: 30px;
  font-size: 18px;
  color: #777;
}

.map-road-user-info .age .form {
  display: inline-block;
  font-size: 28px;
}
.map-road-user-info .age .form input {
  width: 100px;
  font-size: 34px;
  font-weight: 800;
  text-align: center;
  vertical-align: middle;
  border: none;
  border-bottom: 1px #333 solid;
}
.map-road-user-info .age p {
  margin-top: 30px;
  font-size: 18px;
  color: #777;
}
@media screen and (max-width: 768px) {
  .map-road-user-info {
    overflow: hidden;
    margin-top: 25px;
  }
  .map-road-user-info h3 {
    margin: 25px 0 15px;
    font-size: 24px;
  }
  .map-road-user-info .sex > div i {
    width: 130px;
    font-size: 16px;
  }

  .map-road-user-info .age .form {
    font-size: 18px;
  }
  .map-road-user-info .age .form input {
    font-size: 24px;
  }
  .map-road-user-info .age p {
    margin-top: 15px;
    font-size: 14px;
  }
}
@media screen and (max-width: 768px) {
  .map-road-user-info {
    overflow: hidden;
    margin-top: 25px;
  }
  .map-road-user-info h3 {
    margin: 25px 0 15px;
    font-size: 24px;
  }
  .map-road-user-info .sex > div i {
    width: 130px;
    font-size: 16px;
  }

  .map-road-user-info .age .form {
    font-size: 18px;
  }
  .map-road-user-info .age .form input {
    font-size: 24px;
  }
  .map-road-user-info .age p {
    margin-top: 15px;
    font-size: 14px;
  }
  .map-search-wrap .map-road-sub-title {
    cursor: pointer;
    height: 44px;
    line-height: unset;
    text-indent: -99em;
    width: auto;
    font-size: 44px;
    background-size: contain;
  }
}
</style>