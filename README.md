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
5. 주요 소스코드<br>
6. 시연 영상<br>
7. 향후 보완점<br>

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

+ 웹 서버 구축(Apache, PHP, MYSQL)
    <table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178695516-83c19252-8372-4574-a801-d359fad5a694.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178695474-61f0d0e6-aba5-44bc-b5dd-d49975603fee.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178695494-19a21095-3441-444d-aa65-277fc2d3c4e5.png></td>
</tr>
</table>
