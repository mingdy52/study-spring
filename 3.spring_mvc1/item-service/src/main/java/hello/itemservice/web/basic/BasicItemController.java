package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName
                        , @RequestParam int price
                        , @RequestParam Integer quantity
                        , Model model
                       ) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model
                       ) {
        /*
            @ModelAttribute
                1. 요청 파라미터 처리
                    Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
                2. Model 추가
                    모델에 @ModelAttribute로 지정한 객체를 자동으로 넣어준다.
                    name 속성 생략 시 클래스 명을 첫글자만 소문자로 바꿔서 모델에 넣는다.
         */
        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, Model model) {
        /*
            웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송한다.
            상품 등록 폼에서 데이터를 입력하고 저장을 선택하면 ['POST /add' + 상품 데이터]를 서버로 전송한다.
            이 상태에서 새로 고침을 또 선택하면 마지막에 전송한 ['POST /add' + 상품 데이터]를 서버로 다시 전송한다.
            그래서 내용은 같은 데이터가 계속 쌓이게 된다..

            해결 방법 -> PRG(Post Redirect Get)
            상품 상세 화면으로 리다이렉트를 하면 상품 저장 후 실제 상품 상세 화면으로 다시 이동한다.
            즉, 마지막에 호출한 내용이 상품 상세 화면 [GET /items/{id}]가 된다.
         */
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
        // item.getId() 처럼 URL에 변수를 더해서 사용하는 것은 URL 인코딩이 안되기 때문에 위험하다.
    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
        // {itemId} 값이 redirectAttributes 에 넣은 attribute랑 치환된다.
        // 남는 애들은 쿼리 파라미터로 넘어간다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {
        itemRepository.update(itemId, item);
        model.addAttribute("item", item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // 의존성 주입이 완료된 후에 실행되어야 하는 메서드를 지정할 때 사용. 의존성 주입이 완료된 후에만 초기화 로직이 실행된다.
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

    }
}
