package challengelv1kiosk;

/**
 * MenuItem : 개별 메뉴 아이템을 포현 하는 클래스
 */
public class MenuItem {

    // 속성 _ final 불변성 보장하기
    private final String name;        // 메뉴 이름
    private final double price;       // 메뉴 가격
    private final String description; // 메뉴 설명

    // 생성자
    public MenuItem(String name, double price, String description) {
        validatePrice(price);
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * 가격 유효성 검즘 메소드
     */
    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
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

    // 행위 중심 메소드
    // 메뉴 정보 출력 메서드
    public void printInfo(int index) {
        System.out.printf("%d, %-16s | W %.1f | %s\n",
                index, name, price, description);
    }

    // 메뉴 정보 출력 메서드 (인덱스 없음)
    public void printInfoInCart() {
        System.out.printf("%-16s | W %.1f | %s\n",
                name, price, description);
    }

    // 행위 중심 : 설정한 가격이 예산 내인지 확인하는 메소드
    public boolean isPossiblePrice(double budget) {
        return this.price <= budget;
    }
}
