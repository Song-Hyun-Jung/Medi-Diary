package ddwucom.mobile.ma02_20190972;

import java.io.Serializable;

public class PharmacyDto implements Serializable {
    String pharmHpid;
    String pharmName;
    String pharmTel;
    String pharmAddr;
    String pharmLat;
    String pharmLon;

    public String getPharmHpid() { return pharmHpid; }

    public void setPharmHpid(String pharmHpid) { this.pharmHpid = pharmHpid; }

    public String getPharmName() { return pharmName; }

    public void setPharmName(String pharmName) { this.pharmName = pharmName; }

    public String getPharmTel() { return pharmTel; }

    public void setPharmTel(String pharmTel) { this.pharmTel = pharmTel; }

    public String getPharmAddr() { return pharmAddr; }

    public void setPharmAddr(String pharmAddr) { this.pharmAddr = pharmAddr; }

    public String getPharmLat() { return pharmLat; }

    public void setPharmLat(String pharmLat) { this.pharmLat = pharmLat; }

    public String getPharmLon() { return pharmLon; }

    public void setPharmLon(String pharmLon) { this.pharmLon = pharmLon; }

    public PharmacyDto(){ }

    public PharmacyDto(String pharmHpid, String pharmName, String pharmTel, String pharmAddr, String pharmLat, String pharmLon) {
        this.pharmHpid = pharmHpid;
        this.pharmName = pharmName;
        this.pharmTel = pharmTel;
        this.pharmAddr = pharmAddr;
        this.pharmLat = pharmLat;
        this.pharmLon = pharmLon;
    }
}
