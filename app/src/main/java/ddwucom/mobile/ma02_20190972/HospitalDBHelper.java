package ddwucom.mobile.ma02_20190972;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HospitalDBHelper extends SQLiteOpenHelper {
    final static String TAG = "HospitalDB";
    final static String DB_NAME = "myHospital.db";

    public final static String TABLE_HOSPITAL = "myHospital_table";
    public final static String COL_ID = "_id";
    public final static String COL_HOSPITAL_HPID = "hpid";
    public final static String COL_HOSPITAL_ADDR = "hospitalAddr";
    public final static String COL_HOSPITAL_NAME = "hospitalName";
    public final static String COL_HOSPITAL_TEL = "hospitalTel";
    public final static String COL_MONSTART = "monStart";
    public final static String COL_MONEND = "monEnd";
    public final static String COL_TUESTART = "tueStart";
    public final static String COL_TUEEND = "tueEnd";
    public final static String COL_WEDSTART = "wedStart";
    public final static String COL_WEDEND = "wedEnd";
    public final static String COL_THRSTART = "thrStart";
    public final static String COL_THREND = "thrEnd";
    public final static String COL_FRISTART = "friStart";
    public final static String COL_FRIEND = "friEnd";
    public final static String COL_SATSTART = "satStart";
    public final static String COL_SATEND = "satEnd";
    public final static String COL_SUNSTART = "sunStart";
    public final static String COL_SUNEND = "sunEnd";
    public final static String COL_HOLIDAYSTART = "holidayStart";
    public final static String COL_HOLIDAYEND = "holidayEnd";
    public final static String COL_HOSPITALLAT = "hospitalLat";
    public final static String COL_HOSPITALLON = "hospitalLon";
    public final static String COL_TREATMENT = "treatment";
    public final static String COL_HOSPITAL_MEMO = "hospitalMemo";
    public final static String COL_HOSPITAL_PIC = "hospitalPicture";


    public HospitalDBHelper(Context context){super(context, DB_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //북마크 병원 테이블 생성
        String sql_myHospital = "CREATE TABLE " + TABLE_HOSPITAL + " (" +  COL_ID + " integer primary key autoincrement, " + COL_HOSPITAL_HPID + " TEXT, "
                + COL_HOSPITAL_ADDR + " TEXT, "+ COL_HOSPITAL_NAME + " TEXT, "+ COL_HOSPITAL_TEL + " TEXT, "
                + COL_MONSTART + " TEXT, " + COL_MONEND + " TEXT, " + COL_TUESTART + " TEXT, " + COL_TUEEND + " TEXT, "
                + COL_WEDSTART + " TEXT, " + COL_WEDEND + " TEXT, " + COL_THRSTART + " TEXT, " + COL_THREND + " TEXT, "
                + COL_FRISTART + " TEXT, " + COL_FRIEND + " TEXT, " + COL_SATSTART + " TEXT, " + COL_SATEND + " TEXT, "
                + COL_SUNSTART + " TEXT, " + COL_SUNEND + " TEXT, " + COL_HOLIDAYSTART + " TEXT, " + COL_HOLIDAYEND + " TEXT, "
                + COL_HOSPITAL_MEMO + " TEXT, " + COL_HOSPITAL_PIC + " TEXT, "
                + COL_HOSPITALLAT + " TEXT, " + COL_HOSPITALLON + " TEXT, " + COL_TREATMENT + " TEXT )";
        Log.d(TAG, sql_myHospital);
        db.execSQL(sql_myHospital);

        /*
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1111', '서울특별시 월곡동', '월곡병원', '02-1111-1111', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.111', '31.111', '내과');");

        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1112', '서울특별시 월곡동', '월곡병원1', '02-1111-1112', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.112', '31.112', '이비인후과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1113', '서울특별시 월곡동', '월곡병원2', '02-1111-1113', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.114', '31.114', '내과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1114', '서울특별시 월곡동', '월곡병원3', '02-1111-1114', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.118', '31.118', '피부과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1115', '서울특별시 월곡동', '월곡병원4', '02-1111-1115', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.121', '31.121', '치과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1116', '서울특별시 월곡동', '월곡병원5', '02-1111-1116', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.131', '31.131', '내과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1117', '서울특별시 월곡동', '월곡병원6', '02-1111-1117', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.135', '31.135', '이비인후과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1118', '서울특별시 월곡동', '월곡병원7', '02-1111-1118', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.137', '31.137', '피부과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1119', '서울특별시 월곡동', '월곡병원8', '02-1111-1119', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.142', '31.142', '내과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1110', '서울특별시 월곡동', '월곡병원9', '02-1111-1110', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.123', '31.123', '정형외과');");
        db.execSQL("insert into " + TABLE_HOSPITAL + " values (null, 'A1111', '서울특별시 월곡동', '월곡병원10', '02-1111-1111', " +
                "'0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1800', '0900', '1300', '0900', '1300', '0900', '1300', " +
                "null, null, '29.110', '31.110', '치과');");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOSPITAL);
        onCreate(db);
    }
}
