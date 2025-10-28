package challengelv2kiosk;

/**
 * UserType : 사용자 유형 Enum (할인율 관리)
 */
public enum UserType {
    VETERAN("국가유공자", 0.10),
    SOLDIER("군인", 0.05),
    STUDENT("학생", 0.03),
    GENERAL("일반", 0.00);

    private final String displayName;
    private final double discountRate;

    UserType(String displayName, double discountRate) {
        this.displayName = displayName;
        this.discountRate = discountRate;
    }

    public String displayName() {
        return displayName;
    }

    public double discountRate() {
        return discountRate;
    }

    /**
     * 할인 적용된 가격 계산
     */
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - discountRate);
    }

    /**
     * 할인율 퍼센트로 표시
     */
    public String discountPersantage() {
        return String.format("%.0f%%", discountRate * 100);
    }
}
