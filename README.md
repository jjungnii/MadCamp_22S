# MadCamp_22S    
# Week1 - private     

Android Studio와 Java를 이용하여 스플래시 화면과 3개의 탭으로 구성된 안드로이드 어플을 제작했습니다.       
3개의 탭은 아래와 같은 구성으로 이루어져 있습니다. 

+ Contacts     
+ Gallery       
+ Record      


## Tab 상세 설명     

### Tab1 - Contacts     
첫 번째 탭에서는 연락처를 구현했습니다.

#### 기능 설명
+ Tab 1에서는 현재 저장된 연락처들을 확인 할 수 있습니다.
  + 저장된 연락처가 없을 경우 No Data로 표현됩니다.
  + 연락처에는 인물의 id(번호), 사진, 이름, 전화번호가 저장됩니다.

+ Tab1의 우측 하단 버튼을 이용해서 연락처를 추가하는 Addactivity로 이동합니다.
  + Addactivity에서는 이름, 전화번호, 사진을 연락처에 추가할 수 잇습니다.
  + 사진은 기본사진, 사진찍기, 갤러리에서 가져오기 중에서 선택할 수 있습니다.
  + 저장 버튼을 누르면 연락처가 추가되고, Tab1에 적용되게 됩니다.

#### 구현 방법
+ 연락처 조회
  + SQLite를 사용해 외부 db를 만들어서 정보를 저장했습니다.
  + 연락처를 db에서 조회하고 이 연락처들을 Adapter의 ArrayList에 받아옵니다.
  + Adapter를 이용해서 Recycler View에 연락처를 보여줬습니다.

+ 연락처 추가
  + 연락처를 저장할 때는 사용자가 선택한 사진과 데이터를 SQLite 데이터베이스에 저장합니다.
  + 사진 저장시 크기를 줄이기 위해 bitmap.compress를 사용했습니다.
  
### Tab2 - Gallery
두 번째 탭에서는 이미지 갤러리를 구현했습니다.      

#### 기능 설명
+ 이미지를 클릭하면, 클릭한 이미지가 Full Screen으로 볼 수 있습니다.
+ Full Screen 상태에서 손가락으로 확대하면 Zoom-in이 가능합니다.

#### 구현 방법
+ 갤러리 구성
  + ScrollView와 GridView를 이용하여 전반적인 틀을 구성했습니다.
  + 이미지는 CENTER_CROP
  + Adapter를 이용하여 이미지 파일을 불러왔습니다.     
+ Full Screen
  + FullScreenActivity class에 대한 새로운 intent를 생성하고 putExtra()를 이용하여 선택된 이미지의 id 값을 넘겨줍니다.
  + setImageResource()를 이용하여 해당 id 값을 갖는 이미지로 src를 지정함으로써 선택된 이미지를 크게 보여줍니다.
+ Zoom-in
  + pinch-to-zoom Android library인 Álvaro Blanco Cabrero의 [Zoomy](https://github.com/imablanco/Zoomy)를 활용했습니다. 
  ```javascript
  Zoomy.Builder builder=new Zoomy.Builder(this)
                .target(imageView)
                .animateZooming(false)
                .enableImmersiveMode(false);

  builder.register();
  ```　

### Tab3 - Record
세 번째 탭에서는 
CalendarView를 이용하여 달력을 구현했고, 

#### 기능 설명
+ 

#### 구현 방법

## Team Member
Seong-jun Hong <mscj1004@kaist.ac.kr>        
Jung-eun Park <jungeun0831@postech.ac.kr>
