package ddwucom.mobile.ma02_20190972;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class HospitalDBManager {
    final static String TAG = "HospitalDB";

    HospitalDBHelper hospitalDBHelper = null;
    Cursor cursor = null;

    public HospitalDBManager(Context context) { hospitalDBHelper = new HospitalDBHelper(context); }

    public ArrayList<HospitalDto> getAllBookmarks() {
        ArrayList myBookmarkList = new ArrayList();

        SQLiteDatabase db = hospitalDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HospitalDBHelper.TABLE_HOSPITAL, null);

        while(cursor.moveToNext()) {
            String hpid = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_HPID));
            String hospitalName = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_NAME));
            String hospitalAddr = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_ADDR));
            String hospitalTel  = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_TEL));
            String monStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_MONSTART));
            String monEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_MONEND));
            String tueStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_TUESTART));
            String tueEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_TUEEND));
            String wedStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_WEDSTART));
            String wedEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_WEDEND));
            String thrStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_THRSTART));
            String thrEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_THREND));
            String friStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_FRISTART));
            String friEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_FRIEND));
            String satStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_SATSTART));
            String satEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_SATEND));
            String sunStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_SUNSTART));
            String sunEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_SUNEND));
            String holidayStart = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOLIDAYSTART));
            String holidayEnd = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOLIDAYEND));
            String hospitalLat = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITALLAT));
            String hospitalLon = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITALLON));
            String treatment = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_TREATMENT));
            String memo = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_MEMO));
            String picture = cursor.getString(cursor.getColumnIndexOrThrow(HospitalDBHelper.COL_HOSPITAL_PIC));

            Log.d(TAG, hospitalName);

            myBookmarkList.add ( new HospitalDto (hpid, hospitalAddr, hospitalName, hospitalTel,
                    monStart, monEnd, tueStart, tueEnd, wedStart, wedEnd, thrStart, thrEnd, friStart, friEnd,
                    satStart, satEnd, sunStart, sunEnd, holidayStart, holidayEnd, hospitalLat, hospitalLon,
                    treatment, memo, picture) );
        }

        cursor.close();
        hospitalDBHelper.close();
        return myBookmarkList;
    }

    //북마크에 병원 추가
    public void addHospitalBookmark(HospitalDto myHospital, String treatment){
        SQLiteDatabase db = hospitalDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HospitalDBHelper.COL_HOSPITAL_HPID, myHospital.getHpid());
        values.put(HospitalDBHelper.COL_HOSPITAL_ADDR, myHospital.getHospitalAddr());
        values.put(HospitalDBHelper.COL_HOSPITAL_NAME, myHospital.getHospitalName());
        values.put(HospitalDBHelper.COL_HOSPITAL_TEL, myHospital.getHospitalTel());
        values.put(HospitalDBHelper.COL_MONSTART, myHospital.getMonStart());
        values.put(HospitalDBHelper.COL_MONEND, myHospital.getMonEnd());
        values.put(HospitalDBHelper.COL_TUESTART, myHospital.getTueStart());
        values.put(HospitalDBHelper.COL_TUEEND, myHospital.getTueEnd());
        values.put(HospitalDBHelper.COL_WEDSTART, myHospital.getWedStart());
        values.put(HospitalDBHelper.COL_WEDEND, myHospital.getWedEnd());
        values.put(HospitalDBHelper.COL_THRSTART, myHospital.getThrStart());
        values.put(HospitalDBHelper.COL_THREND, myHospital.getThrEnd());
        values.put(HospitalDBHelper.COL_FRISTART, myHospital.getFriStart());
        values.put(HospitalDBHelper.COL_FRIEND, myHospital.getFriEnd());
        values.put(HospitalDBHelper.COL_SATSTART, myHospital.getSatStart());
        values.put(HospitalDBHelper.COL_SATEND, myHospital.getSatEnd());
        values.put(HospitalDBHelper.COL_SUNSTART, myHospital.getSunStart());
        values.put(HospitalDBHelper.COL_SUNEND, myHospital.getSunEnd());
        values.put(HospitalDBHelper.COL_HOLIDAYSTART, myHospital.getHolidayStart());
        values.put(HospitalDBHelper.COL_HOLIDAYEND, myHospital.getHolidayEnd());
        values.put(HospitalDBHelper.COL_HOSPITALLAT, myHospital.getHospitalLat());
        values.put(HospitalDBHelper.COL_HOSPITALLON, myHospital.getHospitalLon());
        values.put(HospitalDBHelper.COL_TREATMENT, treatment);

        db.insert(HospitalDBHelper.TABLE_HOSPITAL, null, values);
        hospitalDBHelper.close();
    }

    //이미 북마크에 존재하는지 확인
    public int existHospital(String hpid){
        SQLiteDatabase db = hospitalDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HospitalDBHelper.TABLE_HOSPITAL + " WHERE hpid = ? ", new String[]{hpid});
        int exist = 0;
        exist = cursor.getCount();
        Log.d(TAG, String.valueOf(exist));
        return exist;
    }

    //북마크 삭제
    public int deleteBookmark(String hpid){
        SQLiteDatabase db = hospitalDBHelper.getWritableDatabase();
        String whereClause = "hpid=?";
        String[] whereArgs = new String[]{hpid};

        int deleteCount = 0;
        deleteCount = db.delete(HospitalDBHelper.TABLE_HOSPITAL, whereClause, whereArgs);
        return deleteCount;
    }

    //북마크 메모 업데이트
    public boolean modifyBookmarkMemo(String hpid, String newMemo){
        SQLiteDatabase sqLiteDatabase = hospitalDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(hospitalDBHelper.COL_HOSPITAL_MEMO, newMemo);

        String whereClause = HospitalDBHelper.COL_HOSPITAL_HPID+ "=?";
        String[] whereArgs = new String[] {hpid};
        int result = sqLiteDatabase.update(HospitalDBHelper.TABLE_HOSPITAL, row, whereClause, whereArgs);
        hospitalDBHelper.close();

        if(result > 0) return true;
        return false;
    }
   //북마크 사진 업데이트
    public boolean modifyBookmarkPic(String hpid, String newPic){
        SQLiteDatabase sqLiteDatabase = hospitalDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(hospitalDBHelper.COL_HOSPITAL_PIC, newPic);

        String whereClause = HospitalDBHelper.COL_HOSPITAL_HPID+ "=?";
        String[] whereArgs = new String[] {hpid};
        int result = sqLiteDatabase.update(HospitalDBHelper.TABLE_HOSPITAL, row, whereClause, whereArgs);
        hospitalDBHelper.close();

        if(result > 0) return true;
        return false;
    }
}
