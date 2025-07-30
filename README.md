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
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/1e4fc234-5074-4785-b52b-7c4c3e0ab746" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/feddde61-685e-46a8-a3ca-f7af719073b3" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/298acba7-edc0-45f5-b1b8-3d05d28e0314" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/f8fa089e-4e18-4a92-8137-8c5f5d19a947" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/6cda76a9-a25d-435f-82c6-8396cebcece5" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/d9c11e41-5779-4dd2-b9de-409435b92633" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/3f973581-1d82-4546-8330-786e5b7d8f59" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/a89eea30-c6c3-46c7-9263-e267647fc6ea" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/17162eb4-41c4-49b9-9eaa-0fceeeb53c64" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/8a38ee2c-49e9-486a-9f5a-3d6ddb6b4e2f" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/201fbe3e-2665-453d-84f5-d40734886aa4" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/d8c2bc9b-71f1-43f5-be0b-40072276da75" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/0396d7b8-7326-4593-a560-c40f563c2434" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/1ded12d8-6603-4f91-bb23-08c9245daf60" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/4a2bd667-f15b-42ee-b29a-7ce3b834f0f7" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/9d0bc2a6-2bc8-48a1-a24b-3a77c2ae1cc1" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/ae5faa0c-4bde-4fe3-bab4-fae3b3ba7cc3" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/255aef27-3718-4850-961c-6344da5ea4e1" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/8d80f0b9-fb53-442f-916f-a87c4f9c6520" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/a05e3c4b-d809-4e91-9b9a-a5a2b038d31e" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/02edbd18-0b2a-4ebf-8684-fa101bae1be6" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/1202cf00-0fe0-4814-a6a3-90eea516a2e7" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/a24c8e8b-1d1c-47df-bdd2-abfd0944d416" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/39421c11-4585-442a-9f20-aad0222943c7" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/2c238fba-1648-4ba8-ab5e-94c95b52713a" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/559b9fe0-5ee1-404d-a38b-a2345f173d5c" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/d41a2557-a285-436c-b9b6-e07d5efbb5b3" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/452863e8-cafc-491f-8eb3-588818a68a62" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/c1efec33-9dcd-458a-bc18-938af30a69ac" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/db849fe6-237e-4007-a835-f93992deba94" />
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/5f8628a1-a070-4e6a-b74b-d165633595c0" />
