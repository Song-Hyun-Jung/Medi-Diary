package ddwucom.mobile.ma02_20190972;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class DrugXmlParser {
    private XmlPullParser parser;

    private enum TagType {NONE, ITEMNAME, ITEMSEQ, EFCYQESITM, USEMETHODQESITM, SEQESITM, DEPOSITMETHODQESITM, IMGLINK}

    private final static String FAULT_RESULT = "result";
    private final static String ITEM_TAG = "item";
    private final static String NAME_TAG = "itemName";
    private final static String SEQ_TAG = "itemSeq";
    private final static String EFFECT_TAG = "efcyQesitm";
    private final static String METHOD_TAG = "useMethodQesitm";
    private final static String SIDE_TAG = "seQesitm";
    private final static String DEPOSIT_TAG = "depositMethodQesitm";
    private final static String IMG_TAG = "itemImage";

    public DrugXmlParser(){
        try{
            parser = XmlPullParserFactory.newInstance().newPullParser();

        } catch(XmlPullParserException e){
            e.printStackTrace();
        }
    }

    public ArrayList<DrugDto> parse(String xml){
        ArrayList<DrugDto> resultList = new ArrayList();
        DrugDto drugDto = null;
        TagType tagType = TagType.NONE;

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
                            drugDto = new DrugDto();
                            drugDto.setImgLink("null");
                        }
                        else if(tag.equals(SEQ_TAG)){
                            if(drugDto != null) tagType = TagType.ITEMSEQ;
                        }
                        else if(tag.equals(NAME_TAG)){
                            if(drugDto != null) tagType = TagType.ITEMNAME;
                        }
                        else if(tag.equals(EFFECT_TAG)){
                            if(drugDto != null) tagType = TagType.EFCYQESITM;
                        }
                        else if(tag.equals(METHOD_TAG)){
                            if(drugDto != null) tagType = TagType.USEMETHODQESITM;
                        }
                        else if(tag.equals(SIDE_TAG)){
                            if(drugDto != null) tagType = TagType.SEQESITM;
                        }
                        else if(tag.equals(DEPOSIT_TAG)){
                            if(drugDto != null) tagType = TagType.DEPOSITMETHODQESITM;
                        }
                        else if(tag.equals(IMG_TAG)){
                            if(drugDto != null) tagType = TagType.IMGLINK;
                        }
                        else if(tag.equals(FAULT_RESULT)){
                            return null;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM_TAG)){
                            resultList.add(drugDto);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType){
                            case ITEMSEQ:
                                drugDto.setItemSeq(parser.getText());
                                break;
                            case ITEMNAME:
                                drugDto.setItemName(parser.getText());
                                break;
                            case EFCYQESITM:
                                parsingData = parser.getText();
                                temp = parsingData.replace("<p>", "");
                                afterRemove = temp.replace("</p>", "\n");
                                drugDto.setEffect(afterRemove);
                                break;
                            case USEMETHODQESITM:
                                parsingData = parser.getText();
                                temp = parsingData.replace("<p>", "");
                                afterRemove = temp.replace("</p>", "\n");
                                drugDto.setUseMethod(afterRemove);
                                break;
                            case SEQESITM:
                                parsingData = parser.getText();
                                temp = parsingData.replace("<p>", "");
                                afterRemove = temp.replace("</p>", "\n");
                                drugDto.setSideEffect(afterRemove);
                                break;
                            case DEPOSITMETHODQESITM:
                                parsingData = parser.getText();
                                temp = parsingData.replace("<p>", "");
                                afterRemove = temp.replace("</p>", "\n");
                                drugDto.setDepositMethod(afterRemove);
                                break;
                            case IMGLINK:
                                drugDto.setImgLink(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
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
