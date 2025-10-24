package mustlv4kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lv 4. Menu 클래스 생성하기
 * - 버거 메뉴, 음료 메뉴 등 각 카테고리 내에 여러 menuItem을 포함한다.
 * - ListMenuItem>은 Kiosk 클래스가 관리하기에 적잘하지 않으므로 Menu 클래스가 관리한다.
 * - 여러 버거들을 포함하는 상위 개념 '버거'같은 카테고리 이름 필드를 갖는다.
 * - 메뉴 카테고리 이름을 반환하는 메서드가 구현되어야 한다.
 */
public class Main {
    public static void main(String[] args) {

        // 1, 각 카테고리 별 MenuItem 리스트 생성
        // 햄버거 메뉴
        List<MenuItem> burgers = new ArrayList<>(Arrays.asList(
                new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
                new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
                new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"),
                new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거")
        ));

        // 음료 메뉴
        List<MenuItem> drinks = new ArrayList<>(Arrays.asList(
                new MenuItem("Cola", 2.0, "시원한 콜라"),
                new MenuItem("Sprite", 2.0, "상쾌한 사이다"),
                new MenuItem("Coffee", 3.0, "진한 아메리카노")
        ));

        // 디저트 메뉴
        List<MenuItem> desserts = new ArrayList<>(Arrays.asList(
                new MenuItem("IceCream", 4.0, "부드러운 바닐라 아이스크림"),
                new MenuItem("Cake", 5.0, "달콤한 초콜릿 케이크"),
                new MenuItem("Cookie", 3.5, "겉은 바삭하고 안은 촉촉한 쿠키")
        ));


        // 2. Menu 객체 생성 (카테고리 이름 + MenuItem 리스트)
        Menu burgerMenu = new Menu("Burger", burgers);
        Menu drinkMenu = new Menu("Drink", drinks);
        Menu dessertMenu = new Menu("Dessert", desserts);

        // 3. Menu 객체들을 리스트 관리
        List<Menu> menus = new ArrayList<>(Arrays.asList(
                burgerMenu,
                drinkMenu,
                dessertMenu
        ));

        // 4. Kiosk 객체 생성 (세 개의 메뉴 리스트를 생성자로 전달)
        Kiosk kiosk = new Kiosk(menus);

        // 키오스크 프로그램 시작
        kiosk.start();
    }
}
