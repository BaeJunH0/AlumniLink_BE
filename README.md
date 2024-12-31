# 졸업생 게시판 만들기 프로젝트

## 목차

## 🙄 프로젝트 구상 이유
> 대학원생 정도를 제외한 컴공 전공자들은 대부분 학교에서 배우는 내용이 아닌, **따로 부트캠프에 참여하거나, 독학 및 프로젝트를 해서 취업을 스스로 준비**하는 중일 것입니다.
>
> 정해진 공부만 하면 되던 고등학교 시절에서 고작 1년, 2년 지난 시점에서 여러 가지 정보를 스스로 찾다보면 이게 맞나 싶기도 하고, 조금은 적성과 맞지 않는 길로 갈 수도 있을 것입니다.
>
> 또한 저희 학교를 기준으로 생각해보면 에타와 같은 여타 커뮤니티도 별로 활성화 되어있지 않아 **정보를 찾거나 질문하기 어려운 점이 많습니다**.
>
> 그래서 이런 상황을 조금이나마 개선해보고자 **소통 창구**가 있으면 좋겠다고 생각해서 저희가 만들어 보기로 했습니다.
>
> **Alumni_Link**는 **후배들은 자신의 상황에 대한 조언을 구하고, 선배들은 팁이 될만한 정보를 주거나, 질문에 답을 하는 플랫폼이 될 것 입니다.**

## Github Repository
> [**_BackEnd_**](https://github.com/BaeJunH0/AlumniLink_BE)
>
> [**_FrontEnd_**](https://github.com/BaeJunH0/AlumniLink_FE)

## 프로젝트 주요 기능
**1. 로그인 및 회원가입 기능**
   - 회원 정보로 필요한 정보는 닉네임, 비밀번호 2가지
     
**2. 게시물 조회, 등록, 삭제 기능**
   - 게시물은 자유, 학업, 취업 3가지 태그로 구성
   - 로그인 한 사용자만 등록, 삭제 가능
     
**3. 댓글 조회, 등록, 삭제 기능**
   - 댓글은 게시물에 종속적
   - 로그인 한 사용자만 등록, 삭제 가능

## 기능 별 주안점
**1. 전체적으로 집중한 부분**
   - **3Layered Architecture** ( **Persistent - Application - Presentation** ) 에 맞추어 DTO 및 메서드 사용에 집중
   - **Custom Error 코드**를 사용하고, **Controller Advice를 이용하여 전역적으로 에러를 처리**
    
**2. 로그인 및 회원가입 기능**
   - **JWT 토큰**, **HandlerMethodArgumentResolver** 두 가지를 조합하여 **Presentation 계층에서 로그인 유저 필터링**
   - 비밀번호 저장 시, **UUID 형태로 저장**하여 **복호화를 불가**하도록 하여 보안성을 높임
   - admin 계정을 운용하여 **관리자 기능을 사용 가능** ( 현재는 사용자 관리만 가능, 추후 추가 가능 / 관리자 페이지 구현 )
    
**3. 게시물 조회, 등록, 삭제 기능**

**4. 댓글 조회, 등록, 삭제 기능**
   - 댓글 조회 과정에서 성능 저하 문제 발생 시 fetch join 추가 예정
    
## 테스팅
### 게시물
- 
### 댓글
- 
### 유저
- 

## 기술 스택
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![H2 MEM Database](https://img.shields.io/badge/H2-1D539F?style=flat-square&logo=h2&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat-square&logo=amazonaws&logoColor=white)

## 참조

## 기여
|                     BaeJunH0                       |
| :------------------------------------------------: |
| <img width="240px" src="https://avatars.githubusercontent.com/u/114082026?v=4" /> |
|     [@BaeJunH0](https://github.com/BaeJunH0)       |
|                 Birth - 2003.07.11                 |
|          National - 🇰🇷 Republic Of Korea           |
