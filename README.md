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

<br>
<br>

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

<br>
<br>

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

<br>
<br>

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

<br>
<br>

### 2. 데이터베이스 구조
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178753574-b75e6738-94aa-4e23-a178-ee64ce968b03.png></td>
<td><img src=https://user-images.githubusercontent.com/59912150/178753585-cd08e364-a6d9-4dfd-827a-57780aa0279b.png></td>
</tr>
</table>

+ 각각 게시판 테이블, 유저 테이블을 나타낸 그림이다.

<br>
<br>

### 3. 회원가입 및 로그인 소스코드

```java
private void login(final String id, final String pw) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("로그인중 입니다.");
        progressDialog.show();
         String url = "http://211.232.201.35/login.php";
          SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
            public void onResponse(String response) {
                if (response.equals("Login Success")) {
                    name = id;
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (loginstate.isChecked()) {
                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedin");
                    } else {
                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedout");
                    }
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, Choice_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    });

        smpr.addStringParam("id", id);
        smpr.addStringParam("password", pw);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
    }
```
+ 사용자가 입력한 id와 pw를 서버에 요청하여 로그인을 하는 코드 부분이다.

<br>
<br>

```java
 String url = "http://211.232.201.35/register.php";     //서버 IP주소
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")) {
                    Toast.makeText(Join_Activity.this, "회원가입을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Join_Activity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Join_Activity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Join_Activity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("nickname", Alias);
        smpr.addStringParam("id", ID);
        smpr.addStringParam("password", PW);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
```
+ 서버에 회원가입을 하는 닉네임,아이디,비밀번호를 서버에 요청하여 회원가입을 진행한다.
+ 이때 닉네임,아이디 중복체크는 웹 서버에서 진행하여 회원가입 성공여부는 response를 통해 알려준다.
+ 웹 서버와 통신하기위해 [volley plus](https://github.com/DWorkS/VolleyPlus) 라이브러리를 이용해 웹서버와 통신했다.

<br>
<br>

### 4. 이미지 등록 소스코드
```java
  gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
            }
        });

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                //사진의 경로 객체 얻어오기
                Uri fileUri = data.getData();
                if(fileUri != null){
                    imgview.setImageURI(fileUri);
                    imgpath = getImgPath(fileUri);
                    new AlertDialog.Builder(this).setMessage(fileUri.toString()+"\n"+imgpath).create().show();
                }else{
                    Toast.makeText(getApplicationContext(), "사진을 선택하지 않았습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //uri를 절대경로로 바꿔서 알려주는 메소드
    private String getImgPath(Uri fileUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, fileUri, proj, null, null,null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realpath = cursor.getString(column_index);
        cursor.close();
        return realpath;
    }
```
+ 게시글을 쓸 때 사진을 넣어서 등록할 경우 사진을 등록하는 코드이다.
+ DB의 성능을 위해서 사진 파일은 웹서버에 직접 저장하고 DB에는 사진의 경로만을 저장한다.
+ 스마트폰의 갤러리에 있는 사진의 경로 데이터를 얻어오고, getImgPath라는 메소드를 통해서 절대경로로 바꿔줘서 서버에 보낼 큐에 저장한다.

<br>
<br>

```java
 private void RegisterContent(String title, String content, String writer) {
        String url = "http://211.232.201.35/board.php";
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(Write_Activity.this).setMessage("응답:"+response).create().show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Write_Activity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("writer", writer);
        smpr.addStringParam("title", title);
        smpr.addStringParam("content", content);
        //이미지 파일 추가
        smpr.addFile("img_path", imgpath);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);

    }
```
+ 사진의 경로와 파일, 작성자, 글 제목, 글 내용을 서버에 요청하여 DB에 반영되도록 한다.

```java
        Glide.with(this).load(intent.getExtras().getString("img_path")).into(board_img_view);

```
+ 그 후에 게시판에서 보일때는 Glide 라이브러리를 통해서 이미지 경로를 가져와 이미지를 나타낸다.

<br>
<br>

### 5. 어종인식 소스코드

```java
static {
        System.loadLibrary("opencv_java3");
    }
```
+ openCV 라이브러리를 설치하고 인식하고자 하는 클래스에 load해준다.

<table>
<tr>
<td><img src=https://user-images.githubusercontent.com/59912150/178759197-a2c3d127-56e6-4820-8a2b-666aa9918fdd.png
></td>
</tr>
</table>

+ 학습된 딥러닝 모델을 assets폴더에 넣어서 해당모델을 이용해 어종 인식을 진행한다.

```java
public void YOLO(View Button){
        if (startYolo == false){
            startYolo = true;
            if (firstTimeYolo == false){
                firstTimeYolo = true;
                String tinyYoloCfg = getPath("yolov3-tiny.cfg", this) ;
                String tinyYoloWeights = getPath("yolov3-tiny_final.weights", this) ;
                tinyYolo = Dnn.readNetFromDarknet(tinyYoloCfg, tinyYoloWeights);
            }
        }
        else{
            startYolo = false;
        }
    }
```
+ DETECT 버튼을 누르게 되면 실행되는 메소드로, assets 폴더에 넣어둔 모델을 이용하여 인식을 시작한다.
+ 어종인식 코드 부분은 [여기](https://github.com/ivangrov/Android-Deep-Learning-with-OpenCV)를 참고하였다.
+ 코드가 많으므로 전체 코드는 [여기](https://github.com/SEO-J17/2021_Capstone/blob/master/app/src/main/java/jeongbuk/galaxys3/fishfinder/Camera_Activity.java)에서 볼 수 있다.

<br>
<br>

# 6. 시연 영상
+ 약 1분 30초 시연 영상은 [링크](https://youtu.be/P_gQI2dfQjw)에서 볼 수 있다.
