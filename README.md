<h1 align="center">Welcome to 완벽한 맛남 👋</h1>
<p>
<div></div>
  <img alt="Version" src="https://img.shields.io/badge/version-1.1-blue.svg?cacheSeconds=2592000" />
  <a href="https://lab.ssafy.com/s03-webmobile1-sub2/s03p12a501/blob/master/README.md" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
</p>
<p>
<div>Front & Back</div>
   <img alt="Node.js" src="https://img.shields.io/badge/Node.js-339933?logo=Node.js&logoColor=white" />
   <img alt="Java" src="https://img.shields.io/badge/Java-007396?logo=Java&logoColor=white" />
    <img alt="Vuetify" src="https://img.shields.io/badge/Vuetify-1867C0?logo=vuetify&logoColor=white" />
    <img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?logo=vue.js&logoColor=white" />
    <img alt="CSS3" src="https://img.shields.io/badge/CSS3-1572B6?logo=CSS3&logoColor=white" />
    <img alt="HTML5" src="https://img.shields.io/badge/html5-E34F26?logo=HTML5&logoColor=white" />
    <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F?logo=Spring&logoColor=white" />
    <img alt="Python" src="https://img.shields.io/badge/Python-3776AB?logo=Python&logoColor=white" />
</p>
<p>
<div>Env</div>
    <img alt="Amazon AWS" src="https://img.shields.io/badge/Amazon AWS-232F3E?logo=Amazon AWS&logoColor=white" />
    <img alt="Jira Software" src="https://img.shields.io/badge/Jira Software-0052CC?logo=Jira Software&logoColor=white" />
    <img alt="GitLab" src="https://img.shields.io/badge/GitLab-FCA121?logo=GitLab&logoColor=white" />
    <img alt="Jenkins" src="https://img.shields.io/badge/Jenkins-D24939?logo=Jenkins&logoColor=white" />
    <img alt="Visual studio Code" src="https://img.shields.io/badge/VisualStudioCode-007ACC?logo=Visual studio Code&logoColor=white" />
    <img alt="NPM" src="https://img.shields.io/badge/NPM-CB3837?logo=NPM&logoColor=white" />
    <img alt=" Yarn" src="https://img.shields.io/badge/Yarn-2C8EBB?logo=Yarn&logoColor=white" />



> 완벽한 맛남을 위한 완벽한 장소를 `완벽한 맛남`에서 찾아보세요~


### 🏠

## What it is

상견례에 적합한 장소를 고민하지 않고 pick! 

## Main Feature

- BIGDATA를 이용한 목적에 맞는 식당 데이터

- 소셜 oauth 를 통한 로그인 구현

- 검색 기능을 통한 맛집 리스트 보기

- 리뷰를 통한 신뢰도 up

  

## Develop Period

- 20201005-20201120

## Git-flow 전략

- master : 최종 제출할 브랜치

- release : 최종 제출 전 정리하는 브랜치

- feature/front/*: Frontend 기능을 개발하는 브랜치

- feature/back/*: Backend 기능을 개발하는 브랜치

## Develop rule

1. 개발을 시작하기 전에 JIRA를 작성한다.

2. 공유하는 브랜치의 커밋 그래프는 함부로 변경하지 않는다.
 
3. 팀원에게 꼭 리뷰를 받는다.

4. 자신의 Pull Request는 스스로 merge한다.

## Gantt Chart

```mermaid
gantt
    dateFormat  YYYY-MM-DD
    title       Dinning&ssul
    %% (`excludes` accepts specific dates in YYYY-MM-DD format, days of the week ("sunday") or "weekends", but not the word "weekdays".)

    section 프로젝트 준비
    Git 이관 			:done, des1, 2020-10-12, 2d
    JIRA 작성			:done, des2, 2020-10-13, 2d
    Frontend준비     :done, after des2, 2d
	Backend 준비		:done, after des2, 2d
	
	section backend
    Backend 구축 1주차 	: crit, done, b1, 2020-10-19, 5d
    User CRUD 		: done, user, 2020-10-19, 2d
    Board CRUD      : done, board, after user, 1d
    Store CRUD		: done, store, after user, 1d
    Review CRUD		: done, review, after store, 1d
    Dib CRUD 		: done, dib, after board,1d
   
   	Backend 구축 2주차	: crit, active, b2, 2020-10-26, 5d
   	Reserve CRUD	: active, reserve, 2020-10-26, 3d
   	
   	Backend 구축 3주차	: b3, 2020-11-02, 5d
    section Frontend
    Frontend 구축 1주차	: crit, done, f1, 2020-10-19, 5d
    Wireframe 	     : done, wireframe, 2020-10-19, 1d
    User page 		: done, userPage, after user, 1d
    Board page      : done, boardPage, after board, 1d
    Store page		: done, storePage, after store, 1d
    Review page		: done, reviewPage, after review, 1d
    Dibs page 		: done, dibPage, after dib, 1d
    
    Frontend 구축 2주차	: crit, active, f2, 2020-10-26, 5d
    Page 안정화 작업	: active, PageStabilization, 2020-10-26, 3d
    reserve Page   : active, reservePage, after PageStabilization,2d
    
    Backend 구축 3주차	: f3, 2020-11-02, 5d
    section 테스트
    기능 테스트      : crit, 2020-11-07, 5d
    
    section 기타
    UCC제작 : 2020-11-16,3d
    발표준비 : 2d
```


## 기술 스택

- Front-end: Vue.cli, node.js, Vuetify, Vuex, bootstrap
- Back-end: Python,Spring
- Env & etc: AWS-EC2, labssafy, Jira, Jenkins
- API를 이용하여 서버 구동
- Oauth2.0을 이용한 소셜 로그인
- Vue.cli와 router를 이용하여 SPA를 구현함으로써 빠른 UI/UX 향상

## Author

👨 **Youngwook Go**

* Github: [@gyw8526](https://github.com/gyw8526)

👩  **Yunji Na**

* Github: [@dmdekf](https://github.com/dmdekf)

👨 **Sung Hyeon Lee**

* Github: [@lshmn951](https://github.com/lshmn951)

👨 **Hyung jin Jeon**

* Github: [@wjsgudwls89](https://github.com/wjsgudwls89)

👨 **Myung Hun Kang**

* Github: [@MHKANG](https://github.com/MHKANG)



## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2020 Youngwook Go, Yunji Na, Soyun Jeon, Hyung jin Jeon

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_

