package challengelv2kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Kiosk : 키오스크 프로그램의 메뉴를 관리하고 사용자 입력을 처리하는 클래스
 */
public class Kiosk {
    // 속성 : Menu 객체들을 관리
    private final List<Menu> categoryMenu;
    private final Order order; // 장바구니 추가
    private UserType currentUserType; // 사용자 유형 추가
    private final Scanner sc;
    private final KioskView view; // view UI전용클래스 추가

    // 생성자 : Menu 리스트를 전달받음
    public Kiosk(List<Menu> categoryMenu) {
        // 방어적 복사
        this.categoryMenu = new ArrayList<>(categoryMenu);
        this.order = new Order();
        this.currentUserType = UserType.GENERAL;
        this.sc = new Scanner(System.in);
        this.view = new KioskView();
    }

    /**
     * 키오스크 프로그램 시작 메소드
     */
    public void start() {
        // 식당 환영 안내문
        view.printWelcomeMessage();

        // 주문 반복문(무한)
        while (true) {
            try {
                // 카테고리 매뉴 출력
                view.printCategoryMenu(categoryMenu, !order.isEmpty());

                // 메뉴 고르기
                int categoryChoice = sc.nextInt();


                // 0. 종료 안내
                if (categoryChoice == 0) {
                    break;
                }

                if (!order.isEmpty()) {
                    // 장바구니 목록이 있을때만 진행.
                    // 4. Orders (장바구니 확인 및 주문)
                    if (categoryChoice == 4) {
                        processOrderMenu();
                        continue;
                    }

                    // 5. Cancel (주문 취소)
                    if (categoryChoice == 5) {
                        cancelOrder();
                        continue;
                    }
                }

                // 선택 잘못된 입력 처리
                if (!isValidCategoryChoice(categoryChoice)) {
                    // 장바구니가 비어있으면 카테고리 메뉴 숫자 범위
                    if (order.isEmpty()) {
                        view.printInvalidInputMessage(0, categoryMenu.size());
                        continue;
                        // 장비구니가 있으면 카테고리 범위 메뉴 + 4, 5 까지 범위 확장
                    } else {
                        view.printInvalidInputMessage(0, categoryMenu.size() + 2);
                        continue;
                    }
                }

                // 선택된 Menu 객체 가져오기
                Menu selectMenus = findMenuAt(categoryChoice - 1);

                // 상세 메뉴 출력
                if (detailMenuSelection(selectMenus)) {
                    // 계속 주문 진행을 안한다면 종료
                    if (!askToContinue()) {
                        break;
                    }
                }

            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("숫자");
                sc.nextLine(); // 버퍼 비우기
            }
        }
        // 종료 안내
        view.printGoodbyeMessage();
        sc.close();
    }

    /**
     * 사용자 유형 선택 : Enum 활용
     */
    private void selectUserType() {
        // 사용자 유형 및 할인율 안내 _ 잘못 입력할 경우 일반으로 설정
        view.printUserTypeSelection();

        try {
            int userTypeChoice = sc.nextInt();

            switch (userTypeChoice) {
                case 1:
                    // 1. 국가유공자 선택 : 10% 할인
                    currentUserType = UserType.VETERAN;
                    break;
                case 2:
                    // 2. 군인 선택 : 5% 할인
                    currentUserType = UserType.SOLDIER;
                    break;
                case 3:
                    // 3. 학생 선택 : 3% 할인
                    currentUserType = UserType.STUDENT;
                    break;
                case 4:
                    // 4. 일반인 선택 : 0%
                    currentUserType = UserType.GENERAL;
                    break;
                default:
                    // 잘못 입력할때도 일반인
                    currentUserType = UserType.GENERAL;
                    break;
            }

            view.printUserTypeConfirmation(currentUserType);

        } catch (Exception e) {
            view.printInvalidUserTypeInput();
            currentUserType = UserType.GENERAL;
            sc.nextLine();
        }
    }

    /**
     * 주문 취소 처리
     */
    private void cancelOrder() {
        if (order.isEmpty()) {
            view.printNoCancelOrder();
            return;
        }

        while (true) {
            try {
                view.printCancelHeader();
                // 장바구니 출력
                order.printCartItems();
                view.printAskCancelOrder();

                int cancelChoice = sc.nextInt();

                // 1. 전체 취소
                if (cancelChoice == 1) {
                    order.clearCart();
                    view.printOrderCancelled();
                    return;
                } else if (cancelChoice == 2) {
                    // 2. 특정 메뉴 제거
                    removeItemFromCart();
                    return;
                } else if (cancelChoice == 3) {
                    // 3. 장바구니 유지
                    view.printKeepCart();
                    return;
                } else {
                    // 잘못 입력 할 경우 1,2,3 중에 입력 안내
                    view.printInvalidChoice("1, 2, 3");
                }
            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("숫자");
                sc.nextLine();
            }
        }
    }

    /**
     * 주문 메뉴 처리
     */
    private void processOrderMenu() {
        if (order.isEmpty()) {
            view.printEmptyCart();
            return;
        }

        while (true) {
            try {
                view.printCartHeader();
                // 장바구니 출력
                order.printCartItems();
                view.printDivider();

                // 1. 주문하기 2. 메인 메뉴 돌아가기
                view.printAskOrderOrBack();

                int orderChoice = sc.nextInt();

                if (orderChoice == 1) {
                    // 사용자 할인 유형 선택
                    selectUserType();
                    // 할인 적용된 주문이거나 일반 주문 완료 및 총 금액 출력
                    printOrderConfirmation();
                    return;
                } else if (orderChoice == 2) {
                    view.printBackToMainMenu();
                    return;
                } else {
                    view.printInvalidChoice("1 또는 2");
                }
            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("숫자");
                sc.nextLine();
            }
        }
    }

    /**
     * 장바구니에서 특정 메뉴 제거 - 스트림 활용
     */
    private void removeItemFromCart() {
        while (true) {
            try {
                view.printAskRemoveMenuName();
                sc.nextLine();
                String menuName = sc.nextLine();

                // 제외할려는 메뉴가 맞는지 재확인
                boolean filtered = order.printCartFilterByName(menuName);

                // 입력한 메뉴 이름과 동일한경우
                if (filtered) {
                    // 메뉴 이름에 해당하는 장바구니 메뉴
                    OrderItem targetItem = order.findOrderItemByName(menuName);

                    // 현재 수량 표시
                    view.printCurrentQuantity(menuName, targetItem.quantity());

                    int removeQuantity = sc.nextInt();

                    // 수량 유효성 검사
                    // 수량은 0이하일수 없음
                    if (removeQuantity <= 0) {
                        view.printInvalidQuantity();
                        continue;
                    }

                    // 수량은 현재 장바구니에 있는 갯수보다 많을 수 없음
                    if (removeQuantity > targetItem.quantity()) {
                        view.printQuantityExceeded(removeQuantity, targetItem.quantity());
                        continue;
                    }

                    // 제거 전 원래 수량 저장
                    int originalQuantity = targetItem.quantity();

                    // 수량만큼 제거
                    order.removeOrderItemByQuantity(menuName, removeQuantity);

                    if (removeQuantity == originalQuantity) {
                        // 전체 제거된 경우
                        view.printMenuRemovedCompletely(menuName, removeQuantity);
                    } else {
                        // 일부만 제거된 경우 (남은 수량 = 원래 수량 - 제거 수량)
                        int remainingQuantity = originalQuantity - removeQuantity;
                        view.printMenuRemovedPartially(menuName, removeQuantity, remainingQuantity);
                    }
                    break;

                } else {
                    // 입력한 메뉴 이름과 동일하지 않은 경우
                    view.printMenuNotFound(menuName);
                    break;
                }
            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("질문에 맞는 (문자or숫자)");
            }
        }
    }

    /**
     * 행위 중심 : 상세 메뉴 선택 처리
     */
    private boolean detailMenuSelection(Menu menu) {
        while (true) {
            try {
                // 선택 상세 메뉴 출력
                view.printDetailMenu(menu);

                // 숫자 선택 (1,2,3,4....)
                int menuChoice = sc.nextInt();

                // 0 누를시 카테고리 메뉴로 복귀
                if (menuChoice == 0) {
                    view.printBackToMainMenu();
                    return false;
                }

                // 유효하지 않은 숫자 입력 처리
                if (!isValidMenuChoice(menuChoice, menu)) {
                    view.printInvalidInputMessage(0, menu.size());
                    continue;
                }

                // 선택한 메뉴
                MenuItem choiceMenu = menu.findMenuItem(menuChoice - 1);

                // 장바구니 추가 안내로 변경
                askToAddCart(choiceMenu);
                return true;
            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("숫자");
                sc.nextLine();
            }
        }
    }

    /**
     * 장바구니에 추가할지 확인
     */
    private void askToAddCart(MenuItem choiceMenu) {
        while (true) {
            try {
                // 선택한 메뉴를 장바구니에 추가할지 안내
                view.printSelectedMenu(choiceMenu);

                int cartChoice = sc.nextInt();

                // 장바구니에 추가
                if (cartChoice == 1) {
                    // 수량 입력
                    view.printAskQuantity();
                    int quantity = sc.nextInt();

                    // 수량 유효성 : 수량은 1이상이어야함
                    if (quantity <= 0) {
                        view.printInvalidQuantity();
                        continue;
                    }

                    // 입력한 수량만큼 상세 메뉴를 장바구니에 추가
                    order.addItem(choiceMenu, quantity);
                    return;


                } else if (cartChoice == 2) {
                    // 장바구니에 추가하지 않음
                    view.printCancelled();
                    return;
                } else {
                    // 잘못 입력
                    view.printInvalidChoice("1 또는 2");
                }

            } catch (Exception e) {
                view.printCorrectTypeOnlyMessage("숫자");
                sc.nextLine();
            }
        }
    }

    /**
     * 행위 중심 : 계속 주문 여부 확인
     */
    private boolean askToContinue() {
        try {
            // 장바구니가 있다면 목록 출력
            if (!order.isEmpty()) {
                // 주문 예정 내역 출력
                view.printOrderPreview(order);
                // 0 누르면 최종 주문 및 메인 메뉴 복귀 , 그외 아무 문자 누르면 바로 주문 안하고 메인 메뉴 복귀
                view.printAskContinueOrder();
                int continueChoice = sc.nextInt();

                if (continueChoice == 0) {
                    // 사용자 할인 여부
                    selectUserType();
                    // 할인 적용된 주문이거나 일반인 주문 최종 내역
                    printOrderConfirmation();
                    return true;
                } else {
                    // 아무 숫자 입력할 경우
                    view.printContinueOrdering();
                    sc.nextLine();
                    return true;
                }

            } else {
                // 장바구니 내역이 없고, 주문하지 않았는데 취소하는 경우,
                view.printNoSelectedMenu();
                sc.nextLine();
                return true;
            }

        } catch (Exception e) {
            // 임의의 글자 입력하는경우
            view.printContinueOrdering();
            sc.nextLine();
            return true;
        }
    }


    /**
     * 할인 적용하여 최종 주문 출력 하는 메소드
     */
    private void printOrderConfirmation() {
        view.printFinalOrderHeader();

        if (currentUserType != UserType.GENERAL) {
            // 일반인이 아닌경우 할인내역 출력
            order.printCartDiscount(currentUserType);
        } else {
            // 일반인인 경우
            order.printCartItems();
        }

        view.printOrderCompleteMessage();

        // 주문완료 후 장바구니 비우기
        order.clearCart();
    }

    /**
     * 행위 중심 : 카테고리 선택 유효성 검증
     *
     * @param choice 입력 숫자
     * @return 입력 숫자가 카테고리 메뉴 숫자 범위 안인지 확인
     */
    private boolean isValidCategoryChoice(int choice) {
        return choice >= 1 && choice <= categoryMenu.size();
    }

    /**
     * 행위 중심 : 상세 메뉴 선택 유효성 검증
     *
     * @param choice 입력 숫자
     * @param menu   선택한 카테고리 메뉴
     * @return 입력 숫자가 상세 메뉴 숫자 범위 안인지 확인
     */
    private boolean isValidMenuChoice(int choice, Menu menu) {
        return choice >= 1 && choice <= menu.size();
    }

    /**
     * 행위 중심 : 카테고리 메뉴 특정 위치의 조회
     */
    private Menu findMenuAt(int index) {
        return categoryMenu.get(index);
    }
}
