package challengelv2kiosk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order : 장바구니 (주문 관리) 클래스 + 람다&스트림 활용하기
 */
public class Order {
    // 속성
    private final List<OrderItem> orderItems;

    // 생성자
    public Order() {
        this.orderItems = new ArrayList<>();
    }

    // 기능

    /**
     * 행위 중심: 장바구니에 항목 추가
     */
    public void addItem(MenuItem menuItem, int quantity) {
        orderItems.add(new OrderItem(menuItem, quantity));
        System.out.println("\n" + menuItem.getName() + "(이)가 " + quantity + "개 장바구니에 추가되었습니다!");
    }

    /**
     * 읽기 전용 뷰
     */
    public List<OrderItem> cartItems() {
        return Collections.unmodifiableList(orderItems);
    }

    /**
     * 장바구니가 비었는지 확인
     */
    public boolean isEmpty() {
        return cartItems().isEmpty();
    }

    /**
     * 장바구니 항목 갯수
     */
    public int cartSize() {
        return cartItems().size();
    }

    /**
     * 총 금액 계산 - 스트림활용
     */
    public double calculateTotalPrice() {
        return orderItems.stream()
                .mapToDouble(OrderItem::calculatePrice)
                .sum();
    }

    /**
     * 할인 적용된 총 금액 계산 - 스트림활용
     */
    public double calculateTotalPriceDiscount(UserType userType) {
        return userType.applyDiscount(calculateTotalPrice());
    }

    /**
     * 특정 메뉴 이름으로 필터링하여 장바구니 출력 - 스트림활용
     */
    public boolean printCartFilterByName(String keyword) {
        List<OrderItem> filtered = orderItems.stream()
                .filter(orderItem -> orderItem.menuItem().getName().toLowerCase()
                        .equalsIgnoreCase(keyword.toLowerCase()))
                .collect(Collectors.toList());

        // 삭제할려는 장바구니 이름이 맞는지 확인, 있으면 true, 없으면(비어있으면) false
        return !filtered.isEmpty();
    }

    /**
     * 특정 메뉴 제거 - 스트림 활용
     */
    public void removeOrderItemByName(String menuName) {
        // 스트림을 사용하여 해당 메뉴를 제외한 리스트 생성
        List<OrderItem> filteredItems = orderItems.stream()
                .filter(orderItem -> !orderItem.menuItem().getName().equalsIgnoreCase(menuName))
                        .collect(Collectors.toList());

        // 원본 리스트를 교체
        orderItems.clear();
        orderItems.addAll(filteredItems);

    }

    /**
     * 장바구니 전체 출력 - 람다식 활용
     */
    public void printCartItems() {
        System.out.println("\n[ Orders ]");

        if (isEmpty()) {
            System.out.println("장바구니가 비어있습니다");
            return;
        }

        // 장바구나 개별 항목 출력
        orderItems.forEach(OrderItem::printCartItem);

        // 장바구니 전체 금액 출력
        System.out.println("\n[ Total ]");
        System.out.printf("최종 금액 : W %.1f\n", calculateTotalPrice());
    }

    /**
     * 할인 정보와 함꼐 장바구니 출력 - 스트림 활용
     */
    public void printCartDiscount(UserType userType) {
        System.out.println("\n[ Orders ]");

        if (isEmpty()) {
            System.out.println("장바구니가 비어있습니다");
            return;
        }

        // 장바구나 개별 항목 출력
        orderItems.forEach(OrderItem::printCartItem);

        // 본래 장바구니 가격
        double originalPrice = calculateTotalPrice();
        // 할인율 적용된 가격
        double discountedTotal = calculateTotalPriceDiscount(userType);
        // 얼마나 할인되는지 가격단위로 보여줌
        double discountAmount = originalPrice - discountedTotal;

        System.out.println("\n[ Total ]");
        System.out.printf("합계 : W %.1f\n", originalPrice);

        // 일반인이 아니라면
        if(userType != UserType.GENERAL) {
            System.out.printf("할인 (%s %s) : -W %.3f\n",
                    userType.displayName(),
                    userType.discountPercentage(),
                    discountAmount);
            System.out.printf("최종 금액 : W %.3f\n", discountedTotal);
        // 일반인이면
        } else  {
            System.out.printf("최종 금액 : W %.1f\n", originalPrice);
        }
    }

    /**
     * 주문 완료시 장바구니 전체 비움
     */
    public void clearCart() {
        orderItems.clear();
    }

}
