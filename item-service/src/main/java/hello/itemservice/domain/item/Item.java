package hello.itemservice.domain.item;

import lombok.Data;

@Data // get, set, tosrtring, .. 등의 역할으 해준다.
public class Item {
 // 상품에는 상품 id, 상품명, 가격, 수량이 존재한다
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;


    // 디폴트 생성자
    public Item() {
    }
    // 생성자
    public Item(String itemName , Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
