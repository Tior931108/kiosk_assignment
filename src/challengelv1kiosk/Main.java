package challengelv1kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lv.1 장바구니 및 구매하기 기능을 추가하기
 * - 장바구니 생성 및 관리 기능
 * - 장바구니 출력 및 금액 계산
 * - 장바구니 담기 기능
 * - 주문 기능
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
        List<Menu> categoryMenu = new ArrayList<>(Arrays.asList(
                burgerMenu,
                drinkMenu,
                dessertMenu
        ));

        // 4. Kiosk 객체 생성 (세 개의 메뉴 리스트를 생성자로 전달)
        Kiosk kiosk = new Kiosk(categoryMenu);

        // 키오스크 프로그램 시작
        kiosk.start();
    }
}
