package ddwucom.mobile.ma02_20190972;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class HospitalXmlParser {
    private XmlPullParser parser;

    private enum TagType {NONE, HOSPITALNAME, HOSPITALADDR, HPID, HOSPITALTEL,
                        MONSTART, MONEND, TUESTART, TUEEND, WEDSTART, WEDEND, THRSTART, THREND, FRISTART, FRIEND,
                        SATSTART, SATEND, SUNSTART, SUNEND, HOLIDAYSTART, HOLIDAYEND,
                        HOSPITALLAT, HOSPITALLON}

    private final static String FAULT_RESULT = "result";
    private final static String ITEM_TAG = "item";
    private final static String HOSPITALNAME_TAG = "dutyName";
    private final static String HOSPITALADDR_TAG = "dutyAddr";
    private final static String HPID_TAG = "hpid";
    private final static String HOSPITALTEL_TAG = "dutyTel1";
    private final static String MONSTART_TAG = "dutyTime1s";
    private final static String MONEND_TAG = "dutyTime1c";
    private final static String TUESTART_TAG = "dutyTime2s";
    private final static String TUEEND_TAG = "dutyTime2c";
    private final static String WEDSTART_TAG = "dutyTime3s";
    private final static String WEDEND_TAG = "dutyTime3c";
    private final static String THRSTART_TAG = "dutyTime4s";
    private final static String THREND_TAG = "dutyTime4c";
    private final static String FRISTART_TAG = "dutyTime5s";
    private final static String FRIEND_TAG = "dutyTime5c";
    private final static String SATSTART_TAG = "dutyTime6s";
    private final static String SATEND_TAG = "dutyTime6c";
    private final static String SUNSTART_TAG = "dutyTime7s";
    private final static String SUNEND_TAG = "dutyTime7c";
    private final static String HOLIDAYSTART_TAG = "dutyTime8s";
    private final static String HOLIDAYEND_TAG = "dutyTime8c";
    private final static String HOSPITALLAT_TAG = "wgs84Lat";
    private final static String HOSPITALLON_TAG = "wgs84Lon";



    public HospitalXmlParser(){
        try{
            parser = XmlPullParserFactory.newInstance().newPullParser();

        } catch(XmlPullParserException e){
            e.printStackTrace();
        }
    }

    public ArrayList<HospitalDto> parse(String xml){
        ArrayList<HospitalDto> resultList = new ArrayList();
        HospitalDto hospitalDto = null;
        HospitalXmlParser.TagType tagType = HospitalXmlParser.TagType.NONE;

        String parsingData;
        String temp;
        String afterRemove;

        try{
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();

                        if(tag.equals(ITEM_TAG)){
                            hospitalDto = new HospitalDto();
                        }
                        else if(tag.equals(HOSPITALNAME_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOSPITALNAME;
                        }
                        else if(tag.equals(HOSPITALADDR_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOSPITALADDR;
                        }
                        else if(tag.equals(HPID_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HPID;
                        }
                        else if(tag.equals(HOSPITALTEL_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOSPITALTEL;
                        }
                        else if(tag.equals(MONSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.MONSTART;
                        }
                        else if(tag.equals(MONEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.MONEND;
                        }
                        else if(tag.equals(TUESTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.TUESTART;
                        }
                        else if(tag.equals(TUEEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.TUEEND;
                        }
                        else if(tag.equals(WEDSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.WEDSTART;
                        }
                        else if(tag.equals(WEDEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.WEDEND;
                        }
                        else if(tag.equals(THRSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.THRSTART;
                        }
                        else if(tag.equals(THREND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.THREND;
                        }
                        else if(tag.equals(FRISTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.FRISTART;
                        }
                        else if(tag.equals(FRIEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.FRIEND;
                        }
                        else if(tag.equals(SATSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.SATSTART;
                        }
                        else if(tag.equals(SATEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.SATEND;
                        }
                        else if(tag.equals(SUNSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.SUNSTART;
                        }
                        else if(tag.equals(SUNEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.SUNEND;
                        }
                        else if(tag.equals(HOLIDAYSTART_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOLIDAYSTART;
                        }
                        else if(tag.equals(HOLIDAYEND_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOLIDAYEND;
                        }
                        else if(tag.equals(HOSPITALLAT_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOSPITALLAT;
                        }
                        else if(tag.equals(HOSPITALLON_TAG)){
                            if(hospitalDto != null) tagType = HospitalXmlParser.TagType.HOSPITALLON;
                        }
                        else if(tag.equals(FAULT_RESULT)){
                            return null;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM_TAG)){
                            resultList.add(hospitalDto);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType){
                            case HOSPITALNAME:
                                hospitalDto.setHospitalName(parser.getText());
                                break;
                            case HOSPITALADDR:
                                hospitalDto.setHospitalAddr(parser.getText());
                                break;
                            case HOSPITALTEL:
                                hospitalDto.setHospitalTel(parser.getText());
                                break;
                            case HPID:
                                hospitalDto.setHpid(parser.getText());
                                break;
                            case MONSTART:
                                hospitalDto.setMonStart(parser.getText());
                                break;
                            case MONEND:
                                hospitalDto.setMonEnd(parser.getText());
                                break;
                            case TUESTART:
                                hospitalDto.setTueStart(parser.getText());
                                break;
                            case TUEEND:
                                hospitalDto.setTueEnd(parser.getText());
                                break;
                            case WEDSTART:
                                hospitalDto.setWedStart(parser.getText());
                                break;
                            case WEDEND:
                                hospitalDto.setWedEnd(parser.getText());
                                break;
                            case THRSTART:
                                hospitalDto.setThrStart(parser.getText());
                                break;
                            case THREND:
                                hospitalDto.setThrEnd(parser.getText());
                                break;
                            case FRISTART:
                                hospitalDto.setFriStart(parser.getText());
                                break;
                            case FRIEND:
                                hospitalDto.setFriEnd(parser.getText());
                                break;
                            case SATSTART:
                                hospitalDto.setSatStart(parser.getText());
                                break;
                            case SATEND:
                                hospitalDto.setSatEnd(parser.getText());
                                break;
                            case SUNSTART:
                                hospitalDto.setSunStart(parser.getText());
                                break;
                            case SUNEND:
                                hospitalDto.setSunEnd(parser.getText());
                                break;
                            case HOLIDAYSTART:
                                hospitalDto.setHolidayStart(parser.getText());
                                break;
                            case HOLIDAYEND:
                                hospitalDto.setHolidayEnd(parser.getText());
                                break;
                            case HOSPITALLAT:
                                hospitalDto.setHospitalLat(parser.getText());
                                break;
                            case HOSPITALLON:
                                hospitalDto.setHospitalLon(parser.getText());
                                break;
                        }
                        tagType = HospitalXmlParser.TagType.NONE;
                        break;

                }
                eventType = parser.next();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return resultList;

    }
}
