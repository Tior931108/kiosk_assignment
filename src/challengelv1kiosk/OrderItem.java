package challengelv1kiosk;

/**
 * OrderItem : 장바구니에 담긴 개별 항목 (메뉴 + 수량)
 */
public class OrderItem {
    // 속성
    private final MenuItem menuItem;
    private final int quantity;

    // 생성자
    public OrderItem(MenuItem menuItem, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    // 기능

    public MenuItem menuItem() {
        return this.menuItem;
    }

    public int quantity() {
        return this.quantity;
    }

    /**
     * 해당 항목의 총 계산
     */
    public double calculatePrice() {
        return menuItem.getPrice() * quantity;
    }

    /**
     * 장바구니 항목 출력
     */
    public void printCartItem() {
        System.out.printf("%-16s | W %.1f | %d개 | 금액 : W %.1f\n",
                menuItem.getName(), menuItem.getPrice(), quantity, calculatePrice());
    }

}
