package mustlv4kiosk;

import java.util.List;

/**
 * Menu : MenuItem 클래스를 관리하는 클래스
 * - 카테고리 이름 (ex: Burger, Drink, Dessert)
 * - 해당 카테고리의 MenuItem 리스트
 */
public class Menu {
    // 속성
    private String categoryName;
    private List<MenuItem> menuItems;

    // 생성자
    public Menu(String categoryName, List<MenuItem> menuItems) {
        this.categoryName = categoryName;
        this.menuItems = menuItems;
    }

    // 기능
    /**
     * 카테고리 getter
     * @return 카테고리 이름 반환
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 상세 매뉴 getter
     * @return 상세 메뉴 반환
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * 메뉴 아이템 개수 반환 메소드
     */
    public int getMenuItemsCount() {
        return menuItems.size();
    }

    /**
     * 특정 인덱스 상세 메뉴 메소드
     * @param index 상세 메뉴 숫자 - 1
     * @return 상세 메뉴 리스트 반환
     */
    public MenuItem getMenuItem(int index) {
        if(index >= 0 && index < menuItems.size()) {
            return menuItems.get(index);
        }
        return null;
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
