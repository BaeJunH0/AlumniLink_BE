# 졸업생 게시판 만들기 프로젝트

## 목차

## 🙄 프로젝트 구상 이유
> **Alumni_Link**는 취준, 졸업예정자들에게 도움을 주고자 **자신만의 팁 공유, 프로젝트 구인, 자소서 공유 및 첨삭**을 함께할 수 있는 서비스입니다.
> 
> 대학원생 정도를 제외한 컴공 전공자들은 대부분 학교에서 배우는 내용이 아닌, **따로 부트캠프에 참여하거나, 독학 및 프로젝트를 해서 취업을 스스로 준비**하는 중일 것입니다.
>
> 정해진 공부만 하면 되던 고등학교 시절에서 고작 1년, 2년 지난 시점에서 여러 가지 정보를 스스로 찾다보면 이게 맞나 싶기도 하고, 조금은 적성과 맞지 않는 길로 갈 수도 있을 것입니다.
>
> 또한, 우리 학부, 특히 글솝은 졸업 트랙이 복잡하고, 요건을 채우기가 어렵지만 이에 대한 정보를 구하려면 꽤 많은 시간을 소비하여야 합니다.
>
> 그래서 이런 상황을 조금이나마 개선해보고자 **소통 창구**가 있으면 좋겠다고 생각해서 저희가 만들어 보기로 했습니다.

## Github Repository
> [**_BackEnd_**](https://github.com/BaeJunH0/AlumniLink_BE)
>
> [**_FrontEnd_**](https://github.com/BaeJunH0/AlumniLink_FE)

## 프로젝트 주요 기능
**1. 로그인 및 회원가입 기능**
   - 회원 정보로 필요한 정보는 닉네임, 비밀번호 2가지
     
**2. 게시물 조회, 등록, 삭제 기능**
   - 게시물은 3가지 태그로 구성 ( 팁, 프로젝트 구인, 자소서 공유 )
     
**3. 댓글 조회, 등록, 삭제 기능**
   - 게시글에 댓글을 작성할 수 있음

## 기능 별 주안점
**1. 전체적으로 집중한 부분**
   - **3Layered Architecture** ( **Persistent - Application - Presentation** ) 에 맞추어 DTO 및 메서드 사용에 집중
   - **Custom Error 코드**를 사용하고, **Controller Advice를 이용하여 전역적으로 에러를 처리**
    
**2. 로그인 및 회원가입 기능**
   - **JWT 토큰**, **HandlerMethodArgumentResolver** 두 가지를 조합하여 **Presentation 계층에서 로그인 유저 필터링**
      - **Access Token, Refresh Token 사용**으로 보다 원활한 인증 가능
   - 비밀번호 저장 시, **UUID 형태로 저장**하여 **복호화를 불가**하도록 하여 보안성을 높임
   - admin 계정을 운용하여 **관리자 기능을 사용 가능** ( 현재는 사용자 관리만 가능, 추후 추가 가능 )
    
**3. 게시물 조회, 등록, 삭제 기능**

**4. 댓글 조회, 등록, 삭제 기능**

**5. 관리자 페이지**
   - 보다 수월한 관리를 위한 관리자 페이지 운용
   - Vanila JS 위주로 구현, HTML, CSS, JQuery, AJAX 등을 통해 비동기 통신 방식으로 구현 
    
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
[개발자 블로그](https://velog.io/@baejunh0/posts)

## 기여
|                     BaeJunH0                       |
| :------------------------------------------------: |
| <img width="240px" src="https://avatars.githubusercontent.com/u/114082026?v=4" /> |
|     [@BaeJunH0](https://github.com/BaeJunH0)       |
|                 Birth - 2003.07.11                 |
|          National - 🇰🇷 Republic Of Korea           |
