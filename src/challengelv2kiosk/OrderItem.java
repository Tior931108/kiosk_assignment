package challengelv2kiosk;

/**
 * OrderItem : 장바구니에 담긴 개별 항목 (메뉴 + 수량)
 */
public class OrderItem {
    // 속성 _ 가변 객체
    private final MenuItem menuItem;
    private int quantity; // 수량 조절을 위함.

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

    public void increaseQuantity(int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("증가 수량은 1이상이어야 합니다.");
        }
        this.quantity += amount;
    }

    /**
     * 수량 감소 메소드
     *
     * @param quantity 감소 시킬 수량
     * @return 수량이 0이 되면 true, 아니면 false
     */
    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("감소 수량은 1 이상이어야 합니다.");
        }
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("감소 수량이 현재 수량보다 많습니다.");
        }

        this.quantity -= quantity;
    }

    /**
     * 해당 항목의 총 계산
     */
    public double calculatePrice() {
        return menuItem().getPrice() * quantity();
    }

    /**
     * 장바구니 항목 출력
     */
    public void printCartItem() {
        System.out.printf("%-16s | W %.1f | %d개 | 금액 : W %.1f\n",
                menuItem.getName(), menuItem.getPrice(), quantity(), calculatePrice());
    }

}
