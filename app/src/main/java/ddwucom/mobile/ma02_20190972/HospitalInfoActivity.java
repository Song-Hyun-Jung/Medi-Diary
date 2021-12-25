package ddwucom.mobile.ma02_20190972;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HospitalInfoActivity extends AppCompatActivity {
    Intent intent;
    HospitalDBManager hospitalDBManager;

    TextView infoName;
    TextView infoTreatment;
    TextView infoMonStart;
    TextView infoTueStart;
    TextView infoWedStart;
    TextView infoThrStart;
    TextView infoFriStart;
    TextView infoSatStart;
    TextView infoSunStart;
    TextView infoHolStart;
    TextView infoAddr;
    TextView infoDistance;
    Button infoTel;

    String hospitalName;
    String treatment;
    String monStart;
    String monEnd;
    String tueStart;
    String tueEnd;
    String wedStart;
    String wedEnd;
    String thrStart;
    String thrEnd;
    String friStart;
    String friEnd;
    String satStart;
    String satEnd;
    String sunStart;
    String sunEnd;
    String holidayStart;
    String holidayEnd;
    String hospitalAddr;
    String hospitalTel;

    HospitalDto hospital;

    int stateFlag;
    ImageButton btn_addBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        intent = getIntent();
        hospitalDBManager = new HospitalDBManager(this);

        infoName = findViewById(R.id.infoName);
        infoTreatment = findViewById(R.id.infoTreatment);
        infoMonStart = findViewById(R.id.infoMonStart);
        infoTueStart = findViewById(R.id.infoTueStart);
        infoWedStart = findViewById(R.id.infoWedStart);
        infoThrStart = findViewById(R.id.infoThrStart);
        infoFriStart = findViewById(R.id.infoFriStart);
        infoSatStart = findViewById(R.id.infoSatStart);
        infoSunStart = findViewById(R.id.infoSunStart);
        infoHolStart = findViewById(R.id.infoHolStart);

        infoTel = findViewById(R.id.infoTel);
        infoAddr = findViewById(R.id.infoAddr);
        infoDistance = findViewById(R.id.infoDistance);

        btn_addBookmark = findViewById(R.id.btn_bookmark);

        hospital = (HospitalDto) intent.getSerializableExtra("hospital");

        hospitalName = hospital.getHospitalName();
        treatment = intent.getStringExtra("treatment");
        monStart = hospital.getMonStart();
        monEnd = hospital.getMonEnd();
        tueStart = hospital.getTueStart();
        tueEnd = hospital.getTueEnd();
        wedStart = hospital.getWedStart();
        wedEnd = hospital.getWedEnd();
        thrStart = hospital.getThrStart();
        thrEnd = hospital.getThrEnd();
        friStart = hospital.getFriStart();
        friEnd = hospital.getFriEnd();
        satStart = hospital.getSatStart();
        satEnd = hospital.getSatEnd();
        sunStart = hospital.getSunStart();
        sunEnd = hospital.getSunEnd();
        holidayStart = hospital.getHolidayStart();
        holidayEnd = hospital.getHolidayEnd();
        hospitalAddr = hospital.getHospitalAddr();
        hospitalTel = hospital.getHospitalTel();

        infoName.setText(hospitalName + " | ");
        infoTreatment.setText(treatment);
        if(monStart != null) {
            infoMonStart.setText("월요일 | " + monStart.substring(0, 2) + ":" + monStart.substring(2) + " ~ "
                    + monEnd.substring(0, 2) + ":" + monEnd.substring(2));
        }
        else{
            infoMonStart.setText("월요일 | 진료 없음");
        }
        if(tueStart != null) {
            infoTueStart.setText("화요일 | " + tueStart.substring(0, 2) + ":" + tueStart.substring(2) + " ~ "
                    + tueEnd.substring(0, 2) + ":" + tueEnd.substring(2));
        }
        else{
            infoTueStart.setText("화요일 | 진료 없음");
        }
        if(wedStart != null) {
            infoWedStart.setText("수요일 | " + wedStart.substring(0, 2) + ":" + wedStart.substring(2) + " ~ "
                     + wedEnd.substring(0, 2) + ":" + wedEnd.substring(2));
        }
        else{
            infoWedStart.setText("수요일 | 진료 없음");
        }
        if(thrStart != null) {
            infoThrStart.setText("목요일 | " + thrStart.substring(0, 2) + ":" + thrStart.substring(2) + " ~ "
                    + thrEnd.substring(0, 2) + ":" + thrEnd.substring(2));
        }
        else{
            infoThrStart.setText("목요일 | 진료 없음");
        }
        if(friStart != null) {
            infoFriStart.setText("금요일 | " + friStart.substring(0, 2) + ":" + friStart.substring(2) + " ~ "
                    + friEnd.substring(0, 2) + ":" + friEnd.substring(2));
        }
        else{
            infoFriStart.setText("금요일 | 진료 없음");
        }
        if(satStart != null) {
            infoSatStart.setText("토요일 | " + satStart.substring(0, 2) + ":" + satStart.substring(2)  + " ~ "
                    + satEnd.substring(0, 2) + ":" + satEnd.substring(2));
        }
        else{
            infoSatStart.setText("토요일 | 진료 없음");
        }
        if(sunStart != null) {
            infoSunStart.setText("일요일 | " + sunStart.substring(0, 2) + ":" + sunStart.substring(2)  + " ~ "
                    + sunEnd.substring(0, 2) + ":" + sunEnd.substring(2));
        }
        else{
            infoSunStart.setText("일요일 | 진료 없음");
        }
        if(holidayStart != null) {
            infoHolStart.setText("공휴일 | " + holidayStart.substring(0, 2) + ":" + holidayStart.substring(2)  + " ~ "
                    + holidayEnd.substring(0, 2) + ":" + holidayEnd.substring(2));
        }
        else{
            infoHolStart.setText("공휴일 | 진료 없음");
        }

        infoAddr.setText(hospitalAddr);
        infoTel.setText(hospitalTel);

        double currentLat = intent.getDoubleExtra("currentLat", 0);
        double currentLon = intent.getDoubleExtra("currentLon", 0);
        double distance = getDistance(currentLat, currentLon, Double.parseDouble(hospital.getHospitalLat()), Double.parseDouble(hospital.getHospitalLon()));
        infoDistance.setText("현재 위치에서 " + String.format( "%.0f", distance ) + "m 떨어져있습니다.");

        if(hospitalDBManager.existHospital(hospital.getHpid()) > 0){
            btn_addBookmark.setImageResource(R.drawable.bookmarkyellow);
        }

    }

    //현재 위치와 병원과의 거리차. m단위 반환
    public double getDistance(double lat1 , double lng1 , double lat2 , double lng2 ){
        double distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);

        distance = locationA.distanceTo(locationB);

        return distance;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infoTel:
                String tel = "tel:" + hospitalTel;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                startActivity(intent);
                break;

            case R.id.btn_bookmark:
                if(hospitalDBManager.existHospital(hospital.getHpid()) > 0){
                    Toast.makeText(this, "북마크에 등록되어있습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    hospitalDBManager.addHospitalBookmark(hospital, treatment);
                    btn_addBookmark.setImageResource(R.drawable.bookmarkyellow);
                    Intent goBookmarkIntent = new Intent(this, BookmarkActivity.class);
                    startActivity(goBookmarkIntent);
                }
                break;

        }
    }
}
