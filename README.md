# 📃 Alumni_Link

## 🙄 프로젝트 구상 이유
> **Alumni_Link**는 경북대학교 컴퓨터학부생이 **자신만의 팁 공유, 질문, 프로젝트 구인**을 함께할 수 있는 서비스입니다.
> 
> 경북대학교 컴퓨터학부는 졸업 요건이 복잡하고, 어려운 편입니다.
>
> 글솝 22학번인 저는 졸업 및 취업 준비를 위한 정보를 수집하는 데에 굉장히 많은 노력이 들었고, 이런 과정이 좀 더 간소화되면 좋겠다는 생각을 하게 되었습니다.
>
> 이를 위해 서로 팁을 공유하고, 소통하는 창구를 만들어보았습니다.

## Github Repository
> [**_BackEnd_**](https://github.com/BaeJunH0/AlumniLink_BE)
>
> [**_FrontEnd_**](https://github.com/Sunja-An/AlumniLink_FE)

## 프로젝트 주요 기능
**1. 로그인 및 회원가입 기능**
   - 서로의 원활한 소통을 위해 회원가입 시 Github 및 이력서 링크를 달아 공유 가능
     
**2. 게시물 기능**
   - 단순 게시글 및 댓글 기능 구현

**3. 프로젝트 구인 기능**
   - 프로젝트에 참여 요청 시, 프로젝트 리더가 이를 수락해야 참여 가능
   - 데드라인, 프로젝트 제한 인원 등의 조건 구현

## 주안점
**1. 자원 사용에서의 효율성**
   - 이전까지 고수하던 AWS 프리티어 인스턴스에서 Serverless 방식으로 변경
   - 서비스의 크기가 작을 것으로 예상되어 Neon + Fly.io 조합의 배포를 선택
   - 서비스의 크기가 커지고, 자원적 문제가 발생할 시, 서버 마이그레이션 예정
     
**2. 협업 과정에서 문서화**
   - 이전까지 협업 과정은 대면 과정이 동반되었지만, 이번 협업은 대면이 불가능한 상황에서 진행
   - 이 과정에서 소통 오류를 최소화 하기 위해 다음과 같은 방식을 사용
     - Github 이슈를 통한 기본적 요청 및 소통
     - 트러블 슈팅 과정을 기록하고 이해하기 쉽게 공유

## 설계
**ERD**
<br>
<img src="https://github.com/user-attachments/assets/a96bce6c-817c-4046-bb4c-06d1bc107b2a" width="500" height="300">

**Architecture**

추가예정

## 기술 스택
> Framework
> 
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
> DB
> 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
> Deploy & CICD
>
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Neon](https://img.shields.io/badge/Neon-0000FF?style=for-the-badge&logo=postgresql&logoColor=white)
![Fly.io](https://img.shields.io/badge/Fly.io-8E75FF?style=for-the-badge&logo=flydotio&logoColor=white)
> Collaboration Tool
>
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)

## 참조
[개발자 블로그](https://velog.io/@baejunh0/posts)

[개발 문서](https://tasty-sundae-b2e.notion.site/Docs-193c18f57d2b805c920df278d7275c5a?pvs=4)

## 백엔드 개발
|                     BaeJunH0                       |
| :------------------------------------------------: |
| <img width="240px" src="https://avatars.githubusercontent.com/u/114082026?v=4" /> |
|     [@BaeJunH0](https://github.com/BaeJunH0)       |
|                 Birth - 2003.07.11                 |
|          National - 🇰🇷 Republic Of Korea           |
