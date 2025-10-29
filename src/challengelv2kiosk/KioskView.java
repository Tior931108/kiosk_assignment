package challengelv2kiosk;

import java.util.List;

/**
 * KioskView : UI 출력만을 담당하는 클래스
 */
public class KioskView {
    /**
     * 환영 메시지 출력
     */
    public void printWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("어서오세요. 맛있다 버거러 오신걸 환영합니다.");
        System.out.println("저희 식당은 청결함과 친절함을 강조합니다.");
        System.out.println("많은 이용바랍니다.");
        System.out.println("=====================================");
    }

    /**
     * 카테고리 메뉴 출력
     */
    public void printCategoryMenu(List<Menu> categoryMenu, boolean hasCartItems) {
        System.out.println("=========[ SHAKESHACK MENU ]=========");
        System.out.println("맛있다 버거러 메뉴 입니다. 메뉴를 골라주세요.");

        // Menu 객체들의 카테고리 이름 출력
        for (int i = 0; i < categoryMenu.size(); i++) {
            System.out.println((i + 1) + ". " + categoryMenu.get(i).getCategoryName());
        }

        System.out.println("0. 종료");

        // 장바구니가 있는 경우에만 출력
        if (hasCartItems) {
            System.out.println("==========[ ORDER MENU ]==========");
            System.out.println("4. Orders     | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel     | 진행중인 주문을 취소합니다.");
        }
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    /**
     * 상세 메뉴 출력
     */
    public void printDetailMenu(Menu categoryMenu) {
        System.out.println("==========[ " + categoryMenu.getCategoryName().toUpperCase() + " MENU ]==========");
        System.out.println(categoryMenu.getCategoryName().toUpperCase() + " 메뉴 입니다. 원하는 음식을 골라주세요.");

        // Menu 객체의 메소드를 통해서 상세 메뉴 아이템 출력
        categoryMenu.printMenuItems();

        System.out.println("0. 뒤로가기");
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    /**
     * 선택한 메뉴 정보 출력
     */
    public void printSelectedMenu(MenuItem menuItem) {
        System.out.println("-------------------------------------");
        System.out.print("\n선택한 메뉴 : ");
        menuItem.printInfoInCart();
        System.out.println("\n-------------------------------------");
        System.out.println("\n위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인       2. 취소");
        System.out.print("입력: ");
    }

    /**
     * 주문 예정 내역 출력
     */
    public void printOrderPreview(Order order) {
        System.out.println("------------[주문 예정 내역]-----------");
        order.printCartItems();
        System.out.println("-------------------------------------");
    }

    /**
     * 장바구니 내역 출력
     */
    public void printCartHeader() {
        System.out.println("------------[장바구니 내역]------------");
    }

    /**
     * 취소 예정 내역 출력
     */
    public void printCancelHeader() {
        System.out.println("------------[취소 예정 내역]-----------");
    }

    /**
     * 최종 주문 내역 헤더 출력
     */
    public void printFinalOrderHeader() {
        System.out.println("------------[최종 주문 내역]-----------");
    }

    /**
     * 주문 완료 메시지 출력
     */
    public void printOrderCompleteMessage() {
        System.out.println("=======[ 주문이 완료되었습니다! ]========");
    }

    /**
     * 종료 메시지 출력
     */
    public void printGoodbyeMessage() {
        System.out.println("키오스크 프로그램을 종료합니다.");
        System.out.println("이용해주셔서 감사합니다");
    }

    /**
     * 사용자 유형 선택 안내 출력
     */
    public void printUserTypeSelection() {
        System.out.println("\n할인 정보를 입력해주세요. (잘못 입력하실경우 일반으로 설정됩니다.)");
        System.out.printf("1. %-8s : %s\n", UserType.VETERAN.displayName(), UserType.VETERAN.discountPercentage());
        System.out.printf("2. %-10s : %s\n", UserType.SOLDIER.displayName(), UserType.SOLDIER.discountPercentage());
        System.out.printf("3. %-10s : %s\n", UserType.STUDENT.displayName(), UserType.STUDENT.discountPercentage());
        System.out.printf("4. %-10s : %s\n", UserType.GENERAL.displayName(), UserType.GENERAL.discountPercentage());
        System.out.print("입력 : ");
    }

    /**
     * 사용자 유형 설정 완료 메시지
     */
    public void printUserTypeConfirmation(UserType userType) {
        System.out.printf("\n%s(으)로 설정되었습니다. (할인율: %s)\n",
                userType.displayName(),
                userType.discountPercentage());
    }

    // ============ 안내 메시지들 ============

    public void printBackToMainMenu() {
        System.out.println("메인 메뉴 선택으로 돌아갑니다.");
    }

    public void printContinueOrdering() {
        System.out.println("계속 주문 합니다.");
    }

    public void printNoSelectedMenu() {
        System.out.println("선택한 메뉴가 없습니다.");
    }

    public void printCancelled() {
        System.out.println("\n취소되었습니다.");
    }

    public void printOrderCancelled() {
        System.out.println("\n주문이 취소되었습니다.");
    }

    public void printKeepCart() {
        System.out.println("\n장바구니를 유지합니다.");
    }

    public void printBackToMainMenuFromOrder() {
        System.out.println("\n메인 메뉴로 돌아갑니다.");
    }

    public void printEmptyCart() {
        System.out.println("\n 장바구니가 비어 있습니다. 먼저 메뉴를 선택해주세요.");
    }

    public void printNoCancelOrder() {
        System.out.println("\n 취소할 주문이 없습니다.");
    }

    // ============ 질문/프롬프트 메시지들 ============

    public void printAskAddToCart() {
        System.out.println("\n위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인       2. 취소");
        System.out.print("입력: ");
    }

    public void printAskQuantity() {
        System.out.print("수량을 입력해주세요: ");
    }

    public void printAskContinueOrder() {
        System.out.println("\n추가 주문을 하시겠습니까?");
        System.out.print("최종 주문하시려면 0, 계속 추가 주문하시려면 아무 글자나 입력해주세요 : ");
    }

    public void printAskOrderOrBack() {
        System.out.println("\n1. 주문하기   2. 메인 메뉴로 돌아가기 ");
        System.out.print("입력: ");
    }

    public void printAskCancelOrder() {
        System.out.println("-------------------------------------");
        System.out.println("\n진행중인 주문을 취소하시겠습니까?");
        System.out.println("1. 전체 취소       2. 특정 메뉴 제외하기   3. 장바구니 유지하기");
        System.out.print("입력: ");
    }

    public void printAskRemoveMenuName() {
        System.out.println("\n제외할 메뉴 전체 이름을 입력해주세요");
        System.out.print("메뉴 이름 입력 : ");
    }

    public void printCurrentQuantity(String menuName, int quantity) {
        System.out.println("\n현재 '" + menuName + "' 수량: " + quantity + "개");
        System.out.print("제외할 수량을 입력해주세요 (전체 제외는 " + quantity + " 입력): ");
    }

    // ============ 에러 메시지들 ============

    public void printInvalidInputMessage(int min, int max) {
        System.out.println("잘못된 입력 입니다. " + min + "~" + max + " 사이의 숫자를 입력해주세요.");
    }

    public void printCorrectTypeOnlyMessage(String keyword) {
        System.out.println(keyword +"만 입력이 가능합니다. 다시 입력해주세요.");
    }

    public void printInvalidUserTypeInput() {
        System.out.println("\n잘못된 입력 입니다. 일반으로 설정됩니다.");
    }

    public void printInvalidQuantity() {
        System.out.println("\n수량은 1 이상이어야 합니다.");
    }

    public void printQuantityExceeded(int inputQuantity, int currentQuantity) {
        System.out.println("\n입력한 수량(" + inputQuantity + "개)이 장바구니의 수량("
                + currentQuantity + "개)보다 많습니다.");
    }

    public void printMenuNotFound(String menuName) {
        System.out.println("\n '" + menuName + "'(을)를 장바구니에서 찾을 수 없습니다.");
    }

    public void printInvalidChoice(String validOptions) {
        System.out.println("\n" + validOptions + " 중에 숫자를 입력해주세요.");
    }

    // ============ 성공 메시지들 ============

    public void printMenuRemovedCompletely(String menuName, int quantity) {
        System.out.println("\n'" + menuName + "' " + quantity
                + "개가 장바구니에서 완전히 제거되었습니다.");
    }

    public void printMenuRemovedPartially(String menuName, int removedQuantity, int remainingQuantity) {
        System.out.println("\n'" + menuName + "' " + removedQuantity
                + "개가 제거되었습니다. (남은 수량: "
                + remainingQuantity + "개)");
    }

    public void printDivider() {
        System.out.println("-------------------------------------");
    }
}
