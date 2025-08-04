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
5. [공격 흐름도](#공격흐름도)
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
<a id="공격흐름도"></a>
## 🛠️ 공격 흐름도
<img width="1700" height="923" alt="Image" src="https://github.com/user-attachments/assets/42dae4ec-39a6-4ff3-a7d6-fff50821a3e8" />

---

<a id="erd"></a>
## 📝 ERD
<img width="1256" height="748" alt="Image" src="https://github.com/user-attachments/assets/9f8fcbbf-906e-4068-ac58-6cdfc9116b17" />

---

<a id="시나리오"></a>
## 🎬 시나리오
<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/4ed1d075-23fd-4154-916c-b8dd23704396" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/3cbf7b66-891b-4b60-9800-096dc61c9ec5" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/aba7fb5a-3da4-474b-806d-b6fedb8c551c" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/54a531e2-7cb3-4127-af9e-42a31b300905" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/7a329c4c-8412-43a5-959f-82834db00e74" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/c46eb259-1bce-432f-a5d0-8a0e1d8e2603" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/1d914201-b9e1-40aa-945e-d1eb89790f63" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/01f2b310-308d-489e-b7b9-92f994045a4e" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/e741ffc2-c7c0-4966-a7a4-cbfb35e0ba9f" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/039289eb-3078-4ac4-8eb4-119089f5653c" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/a56bd7ee-1126-4ea7-ba2d-6f848918f748" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/03cf62b9-951b-446b-afc9-6ffe66c38426" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/28e7aa5e-b3bb-4721-9579-3c8d2841b8f1" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/b87e013a-5a21-476f-99ce-3e6a20fcc08b" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/b33d6b27-4358-4c98-a14a-87e38a8bb139" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/c55b4b27-0edf-4ce0-898e-d5705aeec749" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/66d60cda-501f-4cf1-bdd2-fdbea254d646" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/3b90f7da-a352-48de-9cc1-7db4fa112a00" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/e096d576-1e0d-4222-9648-cf60f1b7be3f" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/e4bd7e20-144f-4cc8-bc02-88e0eee3107f" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/ef233275-9b46-40f5-8859-eb00c0caa4f0" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/81272c87-656c-4f61-99e8-4c78ba7e7e99" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/1f0758ed-2b60-4cd7-b253-308ae0c000bd" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/262c561b-2be0-4c3f-9888-55223f9d71e0" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/6619484d-57b4-4aed-a0c7-1f8e6b43b729" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/152cccd2-506e-4ac2-9b3b-da150a80ae2c" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/b7d9e2c7-6aef-4af7-8303-38b616e05615" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/b08c192b-a785-4cef-b2bd-e398e1f9347a" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/bebf1793-4b10-4dcc-a8b3-21a46c670fa4" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/56c1487c-2bca-45d1-884f-9b92bb25ddf8" />

<img width="1700" height="2200" alt="Image" src="https://github.com/user-attachments/assets/b9dd5f43-e6fa-41a3-b1d8-eec5d5d75705" />
