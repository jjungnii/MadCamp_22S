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
  + GridView를 이용하여 전반적인 틀을 구성했습니다.
  + 이미지는 CENTER_CROP
  + Adapter를 이용하여 이미지 파일을 불러왔습니다.     
+ Full Screen
  + FullScreenActivity class에 대한 새로운 intent를 생성하고 putExtra()를 이용하여 선택된 이미지의 id 값을 넘겨줍니다.
  + setImageResource()를 이용하여 해당 id 값을 갖는 이미지로 src를 지정함으로써 선택된 이미지를 크게 보여줍니다.
+ Zoom-in
  + pinch-to-zoom Android library인 Álvaro Blanco Cabrero의 [Zoomy](https://github.com/imablanco/Zoomy)를 활용했습니다. 
    __FullScreenActivity.java__
    ```javascript
    Zoomy.Builder builder=new Zoomy.Builder(this)
                  .target(imageView)
                  .animateZooming(false)
                  .enableImmersiveMode(false);

    builder.register();
    ```　


### Tab3 - Record
세 번째 탭에서는 달력에 매일 사진과 글로 기록을 남길 수 있는 공간을 제작했습니다.

#### 기능 설명
달력의 날짜를 클릭하면 달력 아래에 해당 일자와 저장된 이미지 및 글을 볼 수 있으며, 수정 및 삭제가 가능합니다.       
 *이미지가 저장되어있지 않을 경우에는 기본 이미지 아이콘이 보입니다.
+ 이미지
  + EDIT IMAGE 버튼을 누르면, 이미지 수정 화면으로 전환되어 이미지를 추가 및 수정할 수 있습니다.
    + IMAGE FROM CAMERA 버튼을 눌러 휴대전화의 카메라 어플을 사용해 찍은 사진을 삽입할 수 있습니다.
    + IMAGE FROM GALLERY 버튼을 눌러 휴대전화의 갤러리에 있는 사진을 삽입할 수 있습니다.
    + CANCEL 버튼을 눌러 이미지 수정을 그만둘 수 있습니다.
    + 사진을 추가한 뒤에는 SAVE 버튼을 눌러 선택한 이미지로 수정하여 저장할 수 있습니다.
  + 삭제
    + DEL IMAGE 버튼을 눌러 저장된 사진을 삭제할 수 있습니다.
+ 텍스트
  + 이미지 옆의 'Enter your record...'가 적힌 공간을 눌러 텍스트를 적을 수 있고, SAVE TEXT 버튼을 눌러 저장할 수 있습니다.
  + 이미 텍스트가 저장되어있을 경우 EDIT TEXT 버튼을 눌러 텍스트를 수정할 수 있습니다. 
+ 어플을 종료하더라도 데이터는 계속 저장되어 있습니다. 

#### 구현 방법
+ 달력 제작
  + CalendarView를 이용하여 달력을 제작했고, onSelectedDayChange()를 override하여 날짜가 바뀔 때마다 날짜에 맞는 사진과 글을 불러옵니다.
+ 데이터(이미지, 텍스트) 저장 및 불러오기
  + FileInputStream과 FileOutputStream을 이용합니다.
  + 파일명을 'YYYY-MM-DD.png' 또는 'YYYY-MM-DD.txt'로 설정하여 날짜에 따라 저장되도록 합니다.
  + 이미 사진이 존재하는데 이미지를 수정하는 경우, 새로운 png 파일로 덮어씀으로써 수정이 이루어집니다.
  + 이미지를 불러올 때에는 Bitmap의 이용을 위해 BufferedInputStream을 추가적으로 사용합니다.
+ 이미지 수정
  + EditImage class의 intent를 생성하고 startActivityForResult()를 사용하여 이미지 수정 화면으로 전환합니다. 
  + onActivityResult()를 override함으로써 이미지 수정 화면에서 SAVE 버튼을 눌렀을 때 수정된 이미지가 탭3 메인 화면에 바로 반영됩니다.
+ 카메라와 갤러리 어플 이용하여 이미지 삽입
  + 각각에 대해 requestCode를 다르게 선언한 뒤, startActivityForResult()함수를 이용하여 어플을 실행시킵니다.
  + onActivityResult()를 통해 어플로부터 이미지 데이터를 받아 삽입합니다. 
  + 어플 실행 코드     
    __EditImage.java__
    ```javascript
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;
    
    public void openCamera(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }
    
    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQUEST_CODE);
    }
    ```
  + 이미지 데이터 받고 삽입하는 코드.      
    __EditImage.java__
    ```javascript
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                bitmap_tmp = BitmapFactory.decodeStream(imageStream);
                selectedImageView.setImageBitmap(bitmap_tmp);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap_tmp = imageBitmap;
            selectedImageView.setImageBitmap(bitmap_tmp);
        }
    }
    ```
+ 텍스트 관련 화면 조작
  + 각 object의 기본 visibility값을 INVISIBLE로 설정해둔 뒤, setVisibility()로 값을 바꿉니다. 
  + 텍스트를 수정할 때에는 EditText object와 SAVE TEXT 버튼이 보이도록, 텍스트를 저장한 뒤에는 TextView와 EDIT TEXT 버튼이 보이도록 조작합니다. 


## Team Member
Seong-jun Hong <mscj1004@kaist.ac.kr>        
Jung-eun Park <jungeun0831@postech.ac.kr>
