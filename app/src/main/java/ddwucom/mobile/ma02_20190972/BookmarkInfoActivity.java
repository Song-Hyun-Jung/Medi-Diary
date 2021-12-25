package ddwucom.mobile.ma02_20190972;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookmarkInfoActivity  extends AppCompatActivity {

    final static String TAG = "HospitalBookmark";
    private static final int REQUEST_TAKE_PHOTO = 200;

    Intent intent;
    HospitalDBManager hospitalDBManager;

    TextView bmInfoName;
    TextView bmInfoTreatment;
    TextView bmInfoMonStart;
    TextView bmInfoTueStart;
    TextView bmInfoWedStart;
    TextView bmInfoThrStart;
    TextView bmInfoFriStart;
    TextView bmInfoSatStart;
    TextView bmInfoSunStart;
    TextView bmInfoHolStart;
    TextView bmInfoAddr;
    Button bmInfoTel;
    ImageView bmPicture;
    EditText bmMemo;

    HospitalDto hospital;

    private String mCurrentPhotoPath;
    int imgWidth;
    int imgHeight;
    int imgFlag = 0;

    Button btn_share;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_info);

        intent = getIntent();
        hospitalDBManager = new HospitalDBManager(this);

        bmInfoName = findViewById(R.id.bmInfoName);
        bmInfoTreatment = findViewById(R.id.bmInfoTreatment);
        bmInfoMonStart = findViewById(R.id.bmInfoMonStart);
        bmInfoTueStart = findViewById(R.id.bmInfoTueStart);
        bmInfoWedStart = findViewById(R.id.bmInfoWedStart);
        bmInfoThrStart = findViewById(R.id.bmInfoThrStart);
        bmInfoFriStart = findViewById(R.id.bmInfoFriStart);
        bmInfoSatStart = findViewById(R.id.bmInfoSatStart);
        bmInfoSunStart = findViewById(R.id.bmInfoSunStart);
        bmInfoHolStart = findViewById(R.id.bmInfoHolStart);
        bmInfoTel = findViewById(R.id.bmInfoTel);
        bmInfoAddr = findViewById(R.id.bmInfoAddr);
        bmPicture = findViewById(R.id.bmPicture);
        bmMemo = findViewById(R.id.bmMemo);
        btn_share = findViewById(R.id.btn_share);

        hospital = (HospitalDto) intent.getSerializableExtra("hospital");


        bmInfoName.setText(hospital.getHospitalName() + " | ");
        bmInfoTreatment.setText(hospital.getTreatment());
        if (hospital.getMonStart() != null) {
            bmInfoMonStart.setText("월요일 | " + hospital.getMonStart().substring(0, 2) + ":" + hospital.getMonStart().substring(2) + " ~ "
                    + hospital.getMonEnd().substring(0, 2) + ":" + hospital.getMonEnd().substring(2));
        } else {
            bmInfoMonStart.setText("월요일 | 진료 없음");
        }
        if (hospital.getTueStart() != null) {
            bmInfoTueStart.setText("화요일 | " + hospital.getTueStart().substring(0, 2) + ":" + hospital.getTueStart().substring(2) + " ~ "
                    + hospital.getTueEnd().substring(0, 2) + ":" + hospital.getTueEnd().substring(2));
        } else {
            bmInfoTueStart.setText("화요일 | 진료 없음");
        }
        if (hospital.getWedStart() != null) {
            bmInfoWedStart.setText("수요일 | " + hospital.getWedStart().substring(0, 2) + ":" + hospital.getWedStart().substring(2) + " ~ "
                    + hospital.getWedEnd().substring(0, 2) + ":" + hospital.getWedEnd().substring(2));
        } else {
            bmInfoWedStart.setText("수요일 | 진료 없음");
        }
        if (hospital.getThrStart() != null) {
            bmInfoThrStart.setText("목요일 | " + hospital.getThrStart().substring(0, 2) + ":" + hospital.getThrStart().substring(2) + " ~ "
                    + hospital.getThrEnd().substring(0, 2) + ":" + hospital.getThrEnd().substring(2));
        } else {
            bmInfoThrStart.setText("목요일 | 진료 없음");
        }
        if (hospital.getFriStart() != null) {
            bmInfoFriStart.setText("금요일 | " + hospital.getFriStart().substring(0, 2) + ":" + hospital.getFriStart().substring(2) + " ~ "
                    + hospital.getFriEnd().substring(0, 2) + ":" + hospital.getFriEnd().substring(2));
        } else {
            bmInfoFriStart.setText("금요일 | 진료 없음");
        }
        if (hospital.getSatStart() != null) {
            bmInfoSatStart.setText("토요일 | " + hospital.getSatStart().substring(0, 2) + ":" + hospital.getSatStart().substring(2) + " ~ "
                    + hospital.getSatEnd().substring(0, 2) + ":" + hospital.getSatEnd().substring(2));
        } else {
            bmInfoSatStart.setText("토요일 | 진료 없음");
        }
        if (hospital.getSunStart() != null) {
            bmInfoSunStart.setText("일요일 | " + hospital.getSunStart().substring(0, 2) + ":" + hospital.getSunStart().substring(2) + " ~ "
                    + hospital.getSunEnd().substring(0, 2) + ":" + hospital.getSunEnd().substring(2));
        } else {
            bmInfoSunStart.setText("일요일 | 진료 없음");
        }
        if (hospital.getHolidayStart() != null) {
            bmInfoHolStart.setText("공휴일 | " + hospital.getHolidayStart().substring(0, 2) + ":" + hospital.getHolidayStart().substring(2) + " ~ "
                    + hospital.getHolidayEnd().substring(0, 2) + ":" + hospital.getHolidayEnd().substring(2));
        } else {
            bmInfoHolStart.setText("공휴일 | 진료 없음");
        }

        bmInfoAddr.setText(hospital.getHospitalAddr());
        bmInfoTel.setText(hospital.getHospitalTel());
        bmMemo.setText(hospital.getMemo());

        Log.i(TAG, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        Log.i(TAG, getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        Log.d(TAG, String.valueOf(imgWidth));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmInfoTel:
                String tel = "tel:" + hospital.getHospitalTel();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                startActivity(intent);
                break;

            case R.id.btn_updateBm:
                boolean result = hospitalDBManager.modifyBookmarkMemo(hospital.getHpid(), bmMemo.getText().toString());
                if(result){
                    Toast.makeText(this, "수정 성공", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_deleteBm:
                hospitalDBManager.deleteBookmark(hospital.getHpid());
                Intent deleteIntent = new Intent(BookmarkInfoActivity.this, BookmarkActivity.class);
                startActivity(deleteIntent);
                break;

            case R.id.btn_addPic:
                dispatchTakePictureIntent();
                break;

            case R.id.btn_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");		// 고정 text
                String toSend = bmInfoName.getText().toString() + " " + bmInfoTreatment.getText().toString()
                        + "\n<진료시간>\n" + bmInfoMonStart.getText().toString() + "\n" +  bmInfoTueStart.getText().toString() + "\n" +
                        bmInfoWedStart.getText().toString() + "\n" +  bmInfoThrStart.getText().toString() + "\n" +
                        bmInfoFriStart.getText().toString() + "\n" +  bmInfoSatStart.getText().toString() + "\n" +
                        bmInfoSunStart.getText().toString() + "\n" +  bmInfoHolStart.getText().toString() + "\n"
                        + "주소: " + bmInfoAddr.getText().toString() + "\n전화번호: " + bmInfoTel.getText().toString()
                        + "\n<Memo>\n" + bmMemo.getText().toString() + "\n";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, toSend);
                startActivity(Intent.createChooser(sharingIntent, "병원 정보 공유"));
                break;

        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && imgFlag == 0) {
            ImageView img = (ImageView) findViewById(R.id.bmPicture);
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            Log.d(TAG, String.valueOf(imgWidth));
            setCurrentPic(hospital.getPicture());
        }

    }

    /*원본 사진 파일 저장*/
    private void dispatchTakePictureIntent() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                    Log.d(TAG, photoFile.getAbsolutePath());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "ddwucom.mobile.ma02_20190972.fileprovider",
                            photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }


    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = bmPicture.getWidth();
        int targetH = bmPicture.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bmPicture.setImageBitmap(bitmap);

        boolean result = hospitalDBManager.modifyBookmarkPic(hospital.getHpid(), mCurrentPhotoPath);
        if(result){
            Toast.makeText(this, "수정 성공", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentPic(String dbPic) {
        // Get the dimensions of the View
        int targetW = bmPicture.getWidth();
        int targetH = bmPicture.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(dbPic, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(dbPic, bmOptions);
        bmPicture.setImageBitmap(bitmap);
        imgFlag = 1;
    }


    /*현재 시간 정보를 사용하여 파일 정보 생성*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();

        }
    }
}
