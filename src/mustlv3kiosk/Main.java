package mustlv3kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * lv.3 객체 지향 설계를 적용하여 순서 제어를 클래스로 관리하기
 * - 객체 지향을 학습하고, 데이터를 구조적으로 관리하며 프로그램을 설계하는 방법을 익힙니다.
 * - main 함수에서 관리하던 전체 순서 제어를 Kiosk 클래스를 통해 관리합니다.
 */
public class Main {
    public static void main(String[] args) {

        // 햄버거 메뉴
        List<MenuItem> burgers = new ArrayList<>(Arrays.asList(
                new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거", "Burger"),
                new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", "Burger"),
                new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", "Burger"),
                new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거", "Burger")
        ));

        // 음료 메뉴
        List<MenuItem> drinks = new ArrayList<>(Arrays.asList(
                new MenuItem("Cola", 2.0, "시원한 콜라", "drink"),
                new MenuItem("Sprite", 2.0, "상쾌한 사이다", "drink"),
                new MenuItem("Coffee", 3.0, "진한 아메리카노", "drink")
        ));

        // 디저트 메뉴
        List<MenuItem> desserts = new ArrayList<>(Arrays.asList(
                new MenuItem("IceCream", 4.0, "부드러운 바닐라 아이스크림",  "dessert"),
                new MenuItem("Cake", 5.0, "달콤한 초콜릿 케이크", "dessert"),
                new MenuItem("Cookie", 3.5, "겉은 바삭하고 안은 촉촉한 쿠키", "dessert")
        ));

        // Kiosk 객체 생성 (세 개의 메뉴 리스트를 생성자로 전달)
        Kiosk kiosk = new Kiosk(burgers, drinks, desserts);

        // 키오스크 프로그램 시작
        kiosk.start();

    }
}
