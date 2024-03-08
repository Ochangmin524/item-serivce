package hello.itemservice.web.item;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.web.item.form.ItemSaveForm;
import hello.itemservice.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item/items";
    }

    //PathVariable로 넘어온 상품ID로 상품을 조회하고, 모델에 담아둔다. 그리고 item 뷰 템플릿을 호출한다.
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/item";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute Item item) {
        return "item/addItemForm";
    }


    /**
     * Item을 생성하고 requestParam에 파라미터를 가져와서
     * item에 집어넣는 과정을 자동화해준다.
     *
     * model.addAttribute(item); 자동 추가, 생략 가능
     */

    /**
     * PRG - Post/Redirect/Get
     */

    /**
     *RedirectAttributes
     */
    //파라미터 정보를 가져와서 repository에 넣어서 item 뷰를 반환한다
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity()!= null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "item/addItemForm";

        }

        //성공로직
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice((form.getPrice()));
        item.setQuantity((form.getQuantity()));

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";


    }


    // 수정에 필요한 정보를 조회하고 모델에 담아 뷰를 호출한다.
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/editItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item")
                       ItemUpdateForm form, BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000)
                bindingResult.reject("totalPriceMin" , new Object[]{10000, resultPrice}, null);

        }

        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "item/editItemForm";
        }

        Item itemParam = new Item();
        itemParam.setItemName(form.getItemName());
        itemParam.setPrice(form.getPrice());
        itemParam.setQuantity(form.getQuantity());

        itemRepository.update(itemId, itemParam);
        return "redirect:/items/{itemId}";
    }





}
