package ddwucom.mobile.ma02_20190972;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class PharmacyActivity extends AppCompatActivity implements OnMapReadyCallback {
    final static String TAG = "PharmActivity";
    final static int PERMISSION_REQ_CODE = 100;

    private GoogleMap mGoogleMap;
    private LocationManager locationManager;
    LatLng currentLoc;
    private PharmacyActivity.AddressResultReceiver addressResultReceiver;
    TextView tvPharmMyLocation;
    String Q0;
    String Q1;

    ArrayList<PharmacyDto> resultList;
    PharmacyXmlParser parser;

    ArrayList<Marker> markerList = new ArrayList();
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;

    //약국 정보 커스텀 다이얼로그
    Dialog pharmInfoDialog;
    TextView dlPharmName;
    TextView dlPharmAddr;
    Button dlPharmTel;
    Button dlOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        tvPharmMyLocation = (TextView) findViewById(R.id.tvPharmMyLocation);
        tvPharmMyLocation.setText("현재 위치");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(checkPermission())
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 500, locationListener);

        addressResultReceiver = new PharmacyActivity.AddressResultReceiver(new Handler());

        mapLoad();

        //지도에 표시할 아이콘 크기
        bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.drugicon);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);

        //약국 정보 다이얼로그
        pharmInfoDialog = new Dialog(PharmacyActivity.this);
        pharmInfoDialog.setContentView(R.layout.pharm_dialog);

    }

    /*구글맵을 멤버변수로 로딩*/
    private void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.pharmMap);
        mapFragment.getMapAsync(this);      // 매배변수 this: MainActivity 가 OnMapReadyCallback 을 구현하므로

    }

    private void getPlaceDetail(String title){
        PharmacyDto pharmacyDto = null;
        for(PharmacyDto p : resultList){
            if(p.getPharmName().equals(title)){
                pharmacyDto = p;
                showPharmDialog(pharmacyDto.getPharmName(), pharmacyDto.getPharmAddr(), pharmacyDto.getPharmTel());
                break;
            }
        }

    }

    public void showPharmDialog(String name, String addr, String tel){
        dlPharmName = pharmInfoDialog.findViewById(R.id.dlPharmName);
        dlPharmAddr = pharmInfoDialog.findViewById(R.id.dlPharmAddr);
        dlPharmTel = pharmInfoDialog.findViewById(R.id.dlPharmTel);
        dlOk = pharmInfoDialog.findViewById(R.id.dlOk);
        dlPharmName.setText(name);
        dlPharmAddr.setText(addr);
        dlPharmTel.setText(tel);
        pharmInfoDialog.show();
        dlPharmTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = "tel:" + dlPharmTel.getText();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                startActivity(intent);
            }
        });
        dlOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pharmInfoDialog.dismiss();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(checkPermission()) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationClickListener(locationClickListener);
        }

        currentLoc = new LatLng(37.606537, 127.041758);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                getPlaceDetail(marker.getTitle());
            }
        });
    }

    GoogleMap.OnMyLocationClickListener locationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(Location location) {
                    currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                    startAddressService(currentLoc);
                }
            };

    LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
            startAddressService(currentLoc);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService(LatLng latLng) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LAT_DATA_EXTRA, latLng.latitude);
        intent.putExtra(Constants.LNG_DATA_EXTRA, latLng.longitude);
        startService(intent);
    }

    /* 위도/경도 → 주소 변환 ResultReceiver */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addressOutput = null;
            String address = null;
            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null)
                    return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                String[] splitAddress = addressOutput.split(" ");
                Q0 = splitAddress[1];
                Q1 = splitAddress[2];
                address = Q0 + " " + Q1;
                if (addressOutput == null)
                    address = "";
                tvPharmMyLocation.setText("현재 위치: " + address);
                Log.d(TAG, addressOutput);
                Log.d(TAG, address);
                String apiAddress = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire";
                String query1 = Q0;
                String query2 = Q1;


                new PharmacyActivity.PharmacyAsyncTask().execute(apiAddress, query1, query2);

            } else{
                tvPharmMyLocation.setText(R.string.no_address_found);
            }
        }
    }


    class PharmacyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String query1 = strings[1];
            String query2 = strings[2];
            String apiURL = null;

            try{
                StringBuilder urlBuilder = new StringBuilder(address);
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=CWrcYagrwEq%2FhRKG2ZkksDsrla7VXjk4Mj6707whjJ%2BcJqOB93U%2FTG0lDkMwD7DIxq%2Bl0hf9YRSgyDgQGg5D0g%3D%3D");
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("500", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode(query1, "UTF-8"));
                if(query2.charAt(query2.length() - 1) == '시' || query2.charAt(query2.length() - 1) == '군' || query2.charAt(query2.length() - 1) == '구')
                    urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode(query2, "UTF-8"));

                apiURL = urlBuilder.toString();
                Log.d(TAG, apiURL);
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String result = downloadContents(apiURL);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            parser = new PharmacyXmlParser();
            resultList = parser.parse(result);     // 오픈API 결과의 파싱 수행
            mGoogleMap.clear();
            if(markerList != null) markerList.clear();
            markerList = new ArrayList<>();
            if (resultList.size() == 0) {
                Toast.makeText(PharmacyActivity.this, "No data!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    for (PharmacyDto p : resultList) {
                        Log.d(TAG, p.getPharmName());
                        if(p.getPharmLat() != null && p.getPharmLon() != null) {
                            LatLng pharmLatLng = new LatLng(Double.parseDouble(p.getPharmLat()), Double.parseDouble(p.getPharmLon()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(pharmLatLng);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                            markerOptions.title(p.getPharmName());
                            markerList.add(mGoogleMap.addMarker(markerOptions));
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(PharmacyActivity.this, "검색 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }


    }

    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(100000);
        conn.setConnectTimeout(100000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

    /* 주소(address)에 접속하여 문자열 데이터를 수신한 후 반환 */
    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
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
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
                mapLoad();
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
