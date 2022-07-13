# FishFinder(Android)
+ YOLO v3 를 이용한 민물 어종 인식 어플리케이션
+ Team: GalaxyS3
    + 서정우
    + 김신동
    + 송현석


# 목차
[1. 프로젝트 소개](#1-프로젝트-소개)<br>
[2. 시스템 설계](#2-시스템-아키텍처)<br>
[3. 사용된 기술 스택](#3-사용된-기술-스택)<br>
[4. 기능 소개](#4-기능-소개)<br>
[5. 주요 소스코드](#5-주요-소스코드)<br>
[6. 시연 영상](#6-시연-영상)<br>


<br>
<br>

# 1. 프로젝트 소개
+ 실시간 객체 탐지 알고리즘인 YOLO v3를 이용하여 직접 어종 학습을 진행하고 이를 바탕으로 안드로이드와 연동 시켜 어종인식과 더불어 커뮤니티 기능을 제공하는 앱이다.
+ 민물어종 22종에 대해서 크롤링을 통해 이미지를 확보해 딥러닝 학습을 진행했다.
+ 어종인식 기능과 커뮤니티 기능이 제공된다.
+ 글을 직접 써서 다른 유저들과 소통할 수 있으며 주요 타겟층은 낚시를 즐기는 사람이다.

<br>
<br>

# 2. 시스템 아키텍처
<img src=https://user-images.githubusercontent.com/59912150/172136165-f89866a2-2add-4f28-b1bb-2d66f7b48f7d.png>

+ 웹서버를 APM으로 직접 구축하여 통신을 했다.
+ 유저들의 정보나 게시판의 글 정보는 DB에 저장된다.
+ 서버os는 VMware를 이용하여 centos7버전을 사용했다.
+ MYSQL 5.7 버전을 사용했다.
+ 안드로이드에서 서버 통신은 http 통신이 기본이며 Volley 라이브러리를 이용하여 웹서버와 통신을 했다.
+ 추가적으로 DB 설계는 다음 그림과 같다.

<img src=https://user-images.githubusercontent.com/59912150/172136811-4fca1002-afef-491e-806a-47d1d2724b95.png>

+ 유저정보를 저장하는 테이블, 게시판을 정보를 저장하는 테이블, 댓글정보를 저장하는 테이블 총 3개로 DB설계를 했다.
+ 각 기본키는 시리얼넘버로 지정했다.

<br>
<br>

# 3. 사용된 기술 스택
+ 웹 서버
    + PHP
    + Centos 7.x
    + MYSQL 5.7
    + Apache 2.x
+ 안드로이드
    + Java
    + OpenCV 3.x
+ 딥 러닝
    + YOLO v3
    + Python

<br>
<br>

# 4. 기능 소개

### 1. 웹 서버(APM)를 통한 회원가입 및 로그인 기능 

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178696806-3775cad0-1f5c-4a4a-8f04-9e8ea8699f15.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178696925-0551b660-c7c8-4775-904f-0b8c3c2da1f9.png></td>
</tr>
</table>

+ 메인화면에서 회원가입을 시도하면 2번째 사진처럼 화면이 넘어가 회원가입을 진행한다.

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178698423-b67d5218-4797-4589-85f1-ab7fe03c70f5.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178698618-934a790d-a651-4146-bb58-001557e980d8.png></td>
</tr>
</table>

+ TextUtils코드를 이용하여 만약 공백인 채로 회원가입을 진행하게 된다면 회원가입이 불가능하게 구현하였다.
<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178699131-77963568-b6a8-4f4c-a81b-7bda6512b7f0.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178699320-6aa41db3-5654-42ee-bfad-8a4e399c2f15.png></td>
</tr>
</table>

+ 웹서버에 작성한 php코드를 통해서 중복된 닉네임을 검사하여 회원가입을 진행한다.
+ 성공적으로 회원가입이 완료되면 토스트 메시지와 함께 로그인 화면으로 이동하게 된다.
+ 자동 로그인은 SharedPreference를 이용하여 기능을 구현했다.


### 2. 게시판을 통한 글쓰기 기능 

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178699923-e7c2a632-83db-495f-952b-e37cd8eeaa05.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178700175-451631c8-69a4-4f62-afae-14345609fe30.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178700455-dc61452b-aba2-46b8-876f-4640f66fec91.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178701220-8e9a7cd9-969c-418c-9f6e-6d70fc321d6f.png></td>
</tr>
</table>

+ 로그인에 성공하게 되면 다음  첫번째 그림과 같은 선택화면으로 넘어가게 된다.
+ 선택화면에서 글을 쓸지 어종을 인식할지 선택하게 된다.
+ 게시판 목록 화면은 카드뷰로 나타냈으면 사진이 있다면 썸네일 사진으로 나타내게 하였다.
+ 연필 모양의 플로팅 버튼을 왼쪽 하단에 설정하여 버튼을 클릭하면 글을 쓸 수 있는 화면으로 넘어가게 된다.
+ 글을 쓸수 있는 화면에서는 갤러리 버튼을 눌러서 사진을 추가할 수도 있다.
+ 글을 쓰는 화면에서 사진을 추가하게 되면 미리보기 사진이 뜨게 하였다.

### 3. 어종 인식 기능

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178699923-e7c2a632-83db-495f-952b-e37cd8eeaa05.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178701613-ddadd835-44c5-4ab7-a830-56291bb0eb05.png></td>
</tr>
</table>

+ 선택화면에서 어종인식을 선택하고 들어가게 되면 카메라로 어종을 인식하게 된다.
+ DETECT 버튼을 누르게되면 탐지가 시작이 되며, 실시간으로 탐지가 시작이 된다.
+ 탐지가 시작이되면 물고기 사진에 주위에 테두리 박스가 생기면서 물고기의 이름을 나타내며 몇%의 정확도를 가졌는지 나타낸다.


# 5. 주요 소스코드
### 1. 웹 서버 주요 소스코드

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178706759-703edea7-4503-43d7-8bfc-f07deace6a5d.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178706767-175aba05-ea54-4468-9497-cdd5a0ca5a31.png></td>
</tr>
</table>

+ 사진 순서대로 회원가입, 로그인 관련 php 소스코드 이다.
+ 안드로이드에서 요청하면 쿼리를 통해 회원가입과 로그인을 진행한다.

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178707136-37374c52-9f6b-4134-b1da-d7f578952966.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178707149-2fa12b76-c7f7-4292-9627-21453fb3f24e.png></td>
</tr>
</table>

+ 순서대로 게시판 리스트를 불러오는 코드, 게시글 작성 php 코드를 나타낸 사진이다.
+ 


# 6. 시연 영상
