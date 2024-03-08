package hello.itemservice.domain.item;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@Bean으로 등록, componentscan에 인식 가능 및 역할 명시
@Repository
public class ItemRepository {
    //static을 통해 공유 변수, private을 통해 다른 클래스에서 접근 불가능
    //static은 클래스가 생성될 때 단 한번만 생성
    //final을 통해 재할당 불가능
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public ArrayList<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());


    }
    public void clearStore(){
        store.clear();
    }

}
