package ddwucom.mobile.ma02_20190972;

import java.io.Serializable;

public class HospitalDto implements Serializable {
    //D001:내과, D002:소아청소년과, D003:신경과, D004:정신건강의학과, D005:피부과, D006:외과, D007:흉부외과, D008:정형외과, D009:신경외과, D010:성형외과, D011:산부인과, D012:안과, D013:이비인후과, D014:비뇨기과, D016:재활의학과, D017:마취통증의학과, D018:영상의학과, D019:치료방사선과, D020:임상병리과, D021:해부병리과, D022:가정의학과, D023:핵의학과, D024:응급의학과, D026:치과, D034:구강악안면외과
    long _id;
    String hpid; //일련번호
    String hospitalAddr; //병원 주소
    String hospitalName; //병원명
    String hospitalTel; //전화번호
    String monStart; //월요일 진료시작
    String monEnd; //월요일 진료마감
    String tueStart; //화요일 진료시작
    String tueEnd; //화요일 진료마감
    String wedStart; //수요일 진료시작
    String wedEnd; //수요일 진료마감
    String thrStart; //목요일 진료시작
    String thrEnd; //목요일 진료마감
    String friStart; //금요일 진료시작
    String friEnd; //금요일 진료마감
    String satStart; //토요일 진료시작
    String satEnd; //토요일 진료마감
    String sunStart; //일요일 진료시작
    String sunEnd; //일요일 진료마감
    String holidayStart; //공휴일 진료시작
    String holidayEnd; //공휴일 진료마감
    String hospitalLat; //병원 위도
    String hospitalLon; //병원 경도
    String treatment; //진료과목
    String memo; //메모
    String picture; //사진

    public HospitalDto(){ }

    public HospitalDto(long _id, String hpid, String hospitalAddr, String hospitalName, String hospitalTel, String monStart, String monEnd, String tueStart, String tueEnd, String wedStart, String wedEnd, String thrStart, String thrEnd, String friStart, String friEnd, String satStart, String satEnd, String sunStart, String sunEnd, String holidayStart, String holidayEnd, String hospitalLat, String hospitalLon) {
        this._id = _id;
        this.hpid = hpid;
        this.hospitalAddr = hospitalAddr;
        this.hospitalName = hospitalName;
        this.hospitalTel = hospitalTel;
        this.monStart = monStart;
        this.monEnd = monEnd;
        this.tueStart = tueStart;
        this.tueEnd = tueEnd;
        this.wedStart = wedStart;
        this.wedEnd = wedEnd;
        this.thrStart = thrStart;
        this.thrEnd = thrEnd;
        this.friStart = friStart;
        this.friEnd = friEnd;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.sunStart = sunStart;
        this.sunEnd = sunEnd;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.hospitalLat = hospitalLat;
        this.hospitalLon = hospitalLon;
    }

    public HospitalDto(long _id, String hpid, String hospitalAddr, String hospitalName, String hospitalTel, String monStart, String monEnd, String tueStart, String tueEnd, String wedStart, String wedEnd, String thrStart, String thrEnd, String friStart, String friEnd, String satStart, String satEnd, String sunStart, String sunEnd, String holidayStart, String holidayEnd, String hospitalLat, String hospitalLon, String treatment) {
        this._id = _id;
        this.hpid = hpid;
        this.hospitalAddr = hospitalAddr;
        this.hospitalName = hospitalName;
        this.hospitalTel = hospitalTel;
        this.monStart = monStart;
        this.monEnd = monEnd;
        this.tueStart = tueStart;
        this.tueEnd = tueEnd;
        this.wedStart = wedStart;
        this.wedEnd = wedEnd;
        this.thrStart = thrStart;
        this.thrEnd = thrEnd;
        this.friStart = friStart;
        this.friEnd = friEnd;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.sunStart = sunStart;
        this.sunEnd = sunEnd;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.hospitalLat = hospitalLat;
        this.hospitalLon = hospitalLon;
        this.treatment = treatment;
    }

    public HospitalDto(long _id, String hpid, String hospitalAddr, String hospitalName, String hospitalTel, String monStart, String monEnd, String tueStart, String tueEnd, String wedStart, String wedEnd, String thrStart, String thrEnd, String friStart, String friEnd, String satStart, String satEnd, String sunStart, String sunEnd, String holidayStart, String holidayEnd, String hospitalLat, String hospitalLon, String treatment, String memo, String picture) {
        this._id = _id;
        this.hpid = hpid;
        this.hospitalAddr = hospitalAddr;
        this.hospitalName = hospitalName;
        this.hospitalTel = hospitalTel;
        this.monStart = monStart;
        this.monEnd = monEnd;
        this.tueStart = tueStart;
        this.tueEnd = tueEnd;
        this.wedStart = wedStart;
        this.wedEnd = wedEnd;
        this.thrStart = thrStart;
        this.thrEnd = thrEnd;
        this.friStart = friStart;
        this.friEnd = friEnd;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.sunStart = sunStart;
        this.sunEnd = sunEnd;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.hospitalLat = hospitalLat;
        this.hospitalLon = hospitalLon;
        this.treatment = treatment;
        this.memo = memo;
        this.picture = picture;
    }

    public HospitalDto(String hpid, String hospitalAddr, String hospitalName, String hospitalTel, String monStart, String monEnd, String tueStart, String tueEnd, String wedStart, String wedEnd, String thrStart, String thrEnd, String friStart, String friEnd, String satStart, String satEnd, String sunStart, String sunEnd, String holidayStart, String holidayEnd, String hospitalLat, String hospitalLon, String treatment, String memo, String picture) {
        this.hpid = hpid;
        this.hospitalAddr = hospitalAddr;
        this.hospitalName = hospitalName;
        this.hospitalTel = hospitalTel;
        this.monStart = monStart;
        this.monEnd = monEnd;
        this.tueStart = tueStart;
        this.tueEnd = tueEnd;
        this.wedStart = wedStart;
        this.wedEnd = wedEnd;
        this.thrStart = thrStart;
        this.thrEnd = thrEnd;
        this.friStart = friStart;
        this.friEnd = friEnd;
        this.satStart = satStart;
        this.satEnd = satEnd;
        this.sunStart = sunStart;
        this.sunEnd = sunEnd;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.hospitalLat = hospitalLat;
        this.hospitalLon = hospitalLon;
        this.treatment = treatment;
        this.memo = memo;
        this.picture = picture;
    }

    public long get_id() { return _id; }

    public void set_id(long _id) { this._id = _id; }

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public String getHospitalAddr() {
        return hospitalAddr;
    }

    public void setHospitalAddr(String hospitalAddr) {
        this.hospitalAddr = hospitalAddr;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalTel() {
        return hospitalTel;
    }

    public void setHospitalTel(String hospitalTel) {
        this.hospitalTel = hospitalTel;
    }

    public String getMonStart() {
        return monStart;
    }

    public void setMonStart(String monStart) {
        this.monStart = monStart;
    }

    public String getMonEnd() {
        return monEnd;
    }

    public void setMonEnd(String monEnd) {
        this.monEnd = monEnd;
    }

    public String getTueStart() {
        return tueStart;
    }

    public void setTueStart(String tueStart) {
        this.tueStart = tueStart;
    }

    public String getTueEnd() {
        return tueEnd;
    }

    public void setTueEnd(String tueEnd) {
        this.tueEnd = tueEnd;
    }

    public String getWedStart() {
        return wedStart;
    }

    public void setWedStart(String wedStart) {
        this.wedStart = wedStart;
    }

    public String getWedEnd() {
        return wedEnd;
    }

    public void setWedEnd(String wedEnd) {
        this.wedEnd = wedEnd;
    }

    public String getThrStart() {
        return thrStart;
    }

    public void setThrStart(String thrStart) {
        this.thrStart = thrStart;
    }

    public String getThrEnd() {
        return thrEnd;
    }

    public void setThrEnd(String thrEnd) {
        this.thrEnd = thrEnd;
    }

    public String getFriStart() {
        return friStart;
    }

    public void setFriStart(String friStart) {
        this.friStart = friStart;
    }

    public String getFriEnd() {
        return friEnd;
    }

    public void setFriEnd(String friEnd) {
        this.friEnd = friEnd;
    }

    public String getSatStart() {
        return satStart;
    }

    public void setSatStart(String satStart) {
        this.satStart = satStart;
    }

    public String getSatEnd() {
        return satEnd;
    }

    public void setSatEnd(String satEnd) {
        this.satEnd = satEnd;
    }

    public String getSunStart() {
        return sunStart;
    }

    public void setSunStart(String sunStart) {
        this.sunStart = sunStart;
    }

    public String getSunEnd() {
        return sunEnd;
    }

    public void setSunEnd(String sunEnd) {
        this.sunEnd = sunEnd;
    }

    public String getHolidayStart() {
        return holidayStart;
    }

    public void setHolidayStart(String holidayStart) {
        this.holidayStart = holidayStart;
    }

    public String getHolidayEnd() {
        return holidayEnd;
    }

    public void setHolidayEnd(String holidayEnd) {
        this.holidayEnd = holidayEnd;
    }

    public String getHospitalLat() {
        return hospitalLat;
    }

    public void setHospitalLat(String hospitalLat) {
        this.hospitalLat = hospitalLat;
    }

    public String getHospitalLon() {
        return hospitalLon;
    }

    public void setHospitalLon(String hospitalLon) {
        this.hospitalLon = hospitalLon;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }
}
