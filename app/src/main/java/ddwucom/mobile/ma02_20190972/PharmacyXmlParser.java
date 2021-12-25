package ddwucom.mobile.ma02_20190972;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class PharmacyXmlParser {
    private XmlPullParser parser;

    private enum TagType {NONE, PHARMHPID, PHARMNAME, PHARMTEL, PHARMADDR, PHARMLAT, PHARMLON}

    private final static String FAULT_RESULT = "result";
    private final static String PHARMITEM_TAG = "item";
    private final static String PHARMHPID_TAG = "hpid";
    private final static String PHARMNAME_TAG = "dutyName";
    private final static String PHARMTEL_TAG = "dutyTel1";
    private final static String PHARMADDR_TAG = "dutyAddr";
    private final static String PHARMLAT_TAG = "wgs84Lat";
    private final static String PHARMLON_TAG = "wgs84Lon";

    public PharmacyXmlParser(){
        try{
            parser = XmlPullParserFactory.newInstance().newPullParser();

        } catch(XmlPullParserException e){
            e.printStackTrace();
        }
    }

    public ArrayList<PharmacyDto> parse(String xml){
        ArrayList<PharmacyDto> resultList = new ArrayList();
        PharmacyDto pharmacyDto = null;
        PharmacyXmlParser.TagType tagType = PharmacyXmlParser.TagType.NONE;

        try{
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();

                        if(tag.equals(PHARMITEM_TAG)){
                            pharmacyDto = new PharmacyDto();
                        }
                        else if(tag.equals(PHARMNAME_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMNAME;
                        }
                        else if(tag.equals(PHARMADDR_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMADDR;
                        }
                        else if(tag.equals(PHARMHPID_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMHPID;
                        }
                        else if(tag.equals(PHARMTEL_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMTEL;
                        }
                        else if(tag.equals(PHARMLAT_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMLAT;
                        }
                        else if(tag.equals(PHARMLON_TAG)){
                            if(pharmacyDto != null) tagType = PharmacyXmlParser.TagType.PHARMLON;
                        }
                        else if(tag.equals(FAULT_RESULT)){
                            return null;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(PHARMITEM_TAG)){
                            resultList.add(pharmacyDto);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        switch(tagType){
                            case PHARMNAME:
                                pharmacyDto.setPharmName(parser.getText());
                                break;
                            case PHARMADDR:
                                pharmacyDto.setPharmAddr(parser.getText());
                                break;
                            case PHARMTEL:
                                pharmacyDto.setPharmTel(parser.getText());
                                break;
                            case PHARMHPID:
                                pharmacyDto.setPharmHpid(parser.getText());
                                break;
                            case PHARMLAT:
                                pharmacyDto.setPharmLat(parser.getText());
                                break;
                            case PHARMLON:
                                pharmacyDto.setPharmLon(parser.getText());
                                break;
                        }
                        tagType = PharmacyXmlParser.TagType.NONE;
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



