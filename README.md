# 🤖 Robotics

이 프로젝트는 Pentesting playground 플랫폼을 위한 시나리오로, OWASP top10에 기반한 웹 취약점이 chaining 형식으로 구성되어있습니다. 
이 문서는 프로젝트의 설치 방법, 기여자 정보, 기술 스택, 협업 방식, 개발 기간, 시스템 아키텍처, ERD, 그리고 시나리오를 설명합니다.
## Technology Stack
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker_Compose-2496ED?style=flat-square&logo=docker&logoColor=white)
![NGINX](https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=nginx&logoColor=white)


---

## 목차
1. [서버 설치 방법](#서버-설치-방법)
2. [기여자 표](#기여자-표)
3. [협업 방식](#협업-방식)
4. [개발 기간](#개발-기간)
5. [시스템 아키텍처](#시스템-아키텍처)
6. [ERD](#erd)
7. [시나리오](#시나리오)

---

<a id="서버-설치-방법"></a>
## 📌 서버 설치 방법

아래 단계를 따라 서버를 설치하고 실행할 수 있습니다.

### 1. 저장소 복제

```bash
# 저장소 복제
git clone https://github.com/WHS-PentestingPlayground/Robotics.git

# 빌드 및 실행
docker compose up -d --build 
```
---

<a id="기여자-표"></a>
## 🙌 기여자 표

<h3>Project Team</h3>

<table>
  <thead>
    <tr>
      <th>Profile</th>
      <th>Role</th>
      <th>Materialize</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/MEspeaker">
          <img src="https://github.com/MEspeaker.png" width="60"/><br/>
          MEspeaker
        </a>
      </td>
      <td align="center">Project Member</td>
      <td align="center">Blind sql injection</td>
    </tr>
    <tr>
      <td align="center">
        <a href="https://github.com/Hwanghangwoo">
          <img src="https://github.com/Hwanghangwoo.png" width="60"/><br/>
          Hwanghangwoo
        </a>
      </td>
      <td align="center">Project Member</td>
      <td align="center">File download vuln</td>
    </tr>
    <tr>
      <td align="center">
        <a href="https://github.com/meowyeok">
          <img src="https://github.com/meowyeok.png" width="60"/><br/>
          meowyeok
        </a>
      </td>
      <td align="center">Project Member</td>
      <td align="center">File upload vuln</td>
    </tr>
    <tr>
      <td align="center">
        <a href="https://github.com/namd0ng">
          <img src="https://github.com/namd0ng.png" width="60"/><br/>
          namd0ng
        </a>
      </td>
      <td align="center">Project Member</td>
      <td align="center">IDOR</td>
    </tr>
  </tbody>
</table>

---

<a id="협업-방식"></a>
## 🔥 협업 방식

| 플랫폼                                                                                                      | 사용 방식                   |
|----------------------------------------------------------------------------------------------------------|-------------------------|
| <img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"> | 매주 토요일 2시 회의    |
| <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=Github&logoColor=white">   | PR을 통해 변경사항 및 테스트 과정 확인 |<br/>|
| <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">   | 시나리오 구성, API, 회의 기록 문서화     |

---

<a id="개발-기간"></a>
## 📆 개발 기간
- 2025.05.01 ~ 2025.05.03 : 팀 규칙 및 코딩 컨벤션 의논, 시나리오 컨셉 정의</br>
- 2025.05.03 ~ 2025.05.07 : 킥오프 보고서작성 및 취약점 구현 역할 분배</br>
- 2025.05.07 ~ 2025.05.10 : 프로젝트 환경 세팅</br>
- 2025.05.10 ~ 2025.05.18 : 개별문제 제작</br>
- 2025.05.18 ~ 2025.05.22 : 유스케이스 다이어그램,기능명세서,ERD제작</br>
- 2025.05.22 ~ 2025.05.31 : 로그인/회원가입, 소개페이지,마이페이지 구현</br>
- 2025.05.31 ~ 2025.06.09 : 게시판 구현 및 도커화</br>
- 2025.06.09 ~ 2025.06.22 : 취약점(IDOR,Blind Sqli,file download/upload)구현 및 난이도조정</br>

---
<a id="시스템-아키텍처"></a>
## 🛠️ 시스템 아키텍처(수정예정)
![MJSECCTF drawio](https://github.com/user-attachments/assets/1257fdac-4325-4c3a-a94f-27f323842ab4)

---

<a id="erd"></a>
## 📝 ERD
<img width="1256" height="748" alt="Image" src="https://github.com/user-attachments/assets/9f8fcbbf-906e-4068-ac58-6cdfc9116b17" />

---

<a id="시나리오"></a>
## 🎬 시나리오

