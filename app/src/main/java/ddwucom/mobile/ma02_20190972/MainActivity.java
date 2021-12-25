package ddwucom.mobile.ma02_20190972;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    HospitalDBHelper dbHelper;
    final static int PERMISSION_REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        dbHelper = new HospitalDBHelper(this);
        dbHelper.getReadableDatabase();
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_hospital:
                intent = new Intent(this, HospitalActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_drug:
                intent = new Intent(this, FindDrugActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_myBookmark:
                intent = new Intent(this, BookmarkActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pharm:
                intent = new Intent(this, PharmacyActivity.class);
                startActivity(intent);
                break;
        }
    }

    //메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onMenuItemClick(MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_intro:
                AlertDialog.Builder introBuilder = new AlertDialog.Builder(MainActivity.this);
                introBuilder.setTitle("개발자 소개")
                        .setMessage("학과: 컴퓨터학과\n학번: 20190972\n이름: 송현정")
                        .setPositiveButton("확인", null)
                        .show();
                break;
            case R.id.menu_finish:
                AlertDialog.Builder finishBuilder = new AlertDialog.Builder(MainActivity.this);
                finishBuilder.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                break;
        }
    }


    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}