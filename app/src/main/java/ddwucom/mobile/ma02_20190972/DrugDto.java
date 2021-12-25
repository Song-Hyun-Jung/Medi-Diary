package ddwucom.mobile.ma02_20190972;

public class DrugDto {
    private String itemName; //제품명
    private String itemSeq; //품목코드
    private String effect; //효능
    private String useMethod; //사용법(복용법)
    private String sideEffect; //부작용
    private String depositMethod; //보관법
    private String imgLink; //이미지 링크

    public DrugDto(){}

    public DrugDto(String itemName, String itemSeq, String effect, String useMethod, String sideEffect, String depositMethod, String imgLink) {
        this.itemName = itemName;
        this.itemSeq = itemSeq;
        this.effect = effect;
        this.useMethod = useMethod;
        this.sideEffect = sideEffect;
        this.depositMethod = depositMethod;
        this.imgLink = imgLink;
    }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getItemSeq() { return itemSeq; }

    public void setItemSeq(String itemSeq) { this.itemSeq = itemSeq; }

    public String getEffect() { return effect; }

    public void setEffect(String effect) { this.effect = effect; }

    public String getUseMethod() { return useMethod; }

    public void setUseMethod(String useMethod) { this.useMethod = useMethod; }

    public String getSideEffect() { return sideEffect; }

    public void setSideEffect(String sideEffect) { this.sideEffect = sideEffect; }

    public String getDepositMethod() { return depositMethod; }

    public void setDepositMethod(String depositMethod) { this.depositMethod = depositMethod; }

    public String getImgLink() { return imgLink; }

    public void setImgLink(String imgLink) { this.imgLink = imgLink; }
}
