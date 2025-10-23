package mustlv3kiosk;

/**
 * MenuItem : 개별 메뉴 아이템을 포현 하는 클래스
 */
public class MenuItem {

    // 속성
    private String name;        // 메뉴 이름
    private double price;       // 메뉴 가격
    private String description; // 메뉴 설명
    private String category; // 메뉴 설명

    // 생성자
    public MenuItem(String name, double price, String description, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    // 기능
    // 메뉴 정보 출력 메서드
    public void printInfo(int index) {
        System.out.printf("%d, %-16s | W %.1f | %s\n",
                index, name, price, description);
    }
}
