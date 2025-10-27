package challengelv1kiosk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Order : 장바구니 (주문 관리) 클래스
 */
public class Order {
    // 속성
    private final List<OrderItem> orderItems;

    // 생성자
    public Order(List<OrderItem> orderItems) {
        this.orderItems = new ArrayList<>();
    }

    // 기능
    /**
     * 장바구니가 비었는지 확인
     */
    public boolean isEmpty() {
        return orderItems.isEmpty();
    }

    /**
     * 장바구니 항목 갯수
     */
    public int cartSize() {
        return orderItems.size();
    }

    /**
     * 총 금액 계산
     */
    public double calculateTotalPrice() {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.calculatePrice();
        }
        return total;
    }

    /**
     * 장바구니 전체 충력
     */
    public void printCartItems() {
        System.out.println("\n[ Orders ]");

        if(isEmpty()) {
            System.out.println("장바구니가 비어있습니다");
            return;
        }

        // 장바구나 개별 항목 출력
        for (OrderItem orderItem : orderItems) {
            orderItem.printCartItem();
        }

        // 장바구니 전체 금액 출력
        System.out.println("\n[ Orders ]");
        System.out.printf("W %.1f\n",  calculateTotalPrice());
    }

    /**
     * 주문 완료시 장바구니 전체 비움
     */
    public void clearCart() {
        orderItems.clear();
    }

    /**
     * 읽기 전용 뷰
     */
    public List<OrderItem> cartItems() {
        return Collections.unmodifiableList(orderItems);
    }
}
