package lk.ijse.pos.dto;

public class ItemDto {

    private String code;
    private String description;
    private Double unitPrice;
    private Double QtyOnHand;

    public ItemDto() {}

    public ItemDto(String code, String description, Double unitPrice, Double qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        QtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", QtyOnHand=" + QtyOnHand +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(Double qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }
}
