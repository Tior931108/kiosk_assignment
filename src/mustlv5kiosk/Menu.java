package mustlv5kiosk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Menu : MenuItem 클래스를 관리하는 클래스
 * - 카테고리 이름 (ex: Burger, Drink, Dessert)
 * - 해당 카테고리의 MenuItem 리스트
 */
public class Menu {
    // 속성
    private final String categoryName;
    private final List<MenuItem> menuItems;

    // 생성자
    public Menu(String categoryName, List<MenuItem> menuItems) {
        this.categoryName = categoryName;
        // 방어적 복사 : 외부에서 원본 리스트 조작 불가 하도록 설정
        this.menuItems = new ArrayList<>(menuItems);
    }

    // 행위 중심 메소드로 이름 변경
    /**
     * 카테고리 이름 조회
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 컬렉션을 직접 반환 하지 않고 , 일기 전용 뷰 제공
     */
    public List<MenuItem> menuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    /**
     * 행위 중심 : 메뉴 개수 조회
     */
    public int size() {
        return menuItems.size();
    }

    /**
     * 행위 중심 : 특정 인덱스 상세 메뉴 메소드
     * @param index 상세 메뉴 숫자 - 1
     * @return 상세 메뉴 리스트 반환
     */
    public MenuItem findMenuItem(int index) {
        if(index < 0 && index >= menuItems.size()) {
            throw new IndexOutOfBoundsException("잘못된 메뉴 숫자입니다.");
        }
        return menuItems.get(index);
    }


    /**
     * 해당 카테고리 모든 메뉴 출력 메소드
     */
    public void printMenuItems() {
        for (int i = 0; i < menuItems.size(); i++) {
            // 상세 메뉴 가져와서 메뉴 정보 (이름, 가격, 설명) 을 출력한다.
            menuItems.get(i).printInfo(i+1);
        }
    }

}
