package challengelv2kiosk;

import java.util.ArrayList;
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

    // 생성자 : Menu 리스트를 전달받음
    public Kiosk(List<Menu> categoryMenu) {
        // 방어적 복사
        this.categoryMenu = new ArrayList<>(categoryMenu);
        this.order = new Order();
        this.currentUserType = UserType.GENERAL;
        this.sc = new Scanner(System.in);
    }

    /**
     * 키오스크 프로그램 시작 메소드
     */
    public void start() {
        // 식당 환영 안내문
        printWelcomeMessage();

        // 주문 반복문(무한)
        while (true) {
            try {
                // 카테고리 매뉴 출력
                printCategoryMenu();

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
                        printInvalidInputMessage(0, categoryMenu.size());
                        continue;
                        // 장비구니가 있으면 카테고리 범위 메뉴 + 4, 5 까지 범위 확장
                    } else {
                        printInvalidInputMessage(0, categoryMenu.size() + 2);
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
                printNumberOnlyMessage();
                sc.nextLine(); // 버퍼 비우기
            }
        }
        // 종료 안내
        printGoodbyeMessage();
        sc.close();
    }

    /**
     * 사용자 유형 선택 : Enum 활용
     */
    private void selectUserType() {
        System.out.println("\n할인 정보를 입력해주세요. (잘못 입력하실경우 일반으로 설정됩니다.)");
        System.out.printf("1. %-8s : %s\n", UserType.VETERAN.displayName(), UserType.VETERAN.discountPercentage());
        System.out.printf("2. %-10s : %s\n", UserType.SOLDIER.displayName(), UserType.SOLDIER.discountPercentage());
        System.out.printf("3. %-10s : %s\n", UserType.STUDENT.displayName(), UserType.STUDENT.discountPercentage());
        System.out.printf("4. %-10s : %s\n", UserType.GENERAL.displayName(), UserType.GENERAL.discountPercentage());
        System.out.print("입력 : ");

        try {
            int userTypeChoice = sc.nextInt();

            switch (userTypeChoice) {
                case 1:
                    currentUserType = UserType.VETERAN;
                    break;
                case 2:
                    currentUserType = UserType.SOLDIER;
                    break;
                case 3:
                    currentUserType = UserType.STUDENT;
                    break;
                case 4:
                    currentUserType = UserType.GENERAL;
                    break;
                default:
                    currentUserType = UserType.GENERAL;
                    break;
            }
            System.out.printf("\n%s(으)로 설정되었습니다. (할인율: %s)\n"
                    ,currentUserType.displayName()
                    ,currentUserType.discountPercentage());

        } catch (Exception e) {
            System.out.println("\n잘못된 입력 입니다. 일반으로 설정됩니다.");
            currentUserType = UserType.GENERAL;
            sc.nextLine();
        }
    }

    /**
     * 주문 취소 처리
     */
    private void cancelOrder() {
        if (order.isEmpty()) {
            System.out.println("\n 취소할 주문이 없습니다.");
            return;
        }

        while (true) {
            try {
                System.out.println("------------[취소 예정 내역]-----------");
                // 장바구니 출력
                order.printCartItems();
                System.out.println("-------------------------------------");

                System.out.println("\n진행중인 주문을 전체 취소하시겠습니까?");
                System.out.println("1. 네       2. 아니오");
                System.out.print("입력: ");

                int cancelChoice = sc.nextInt();

                if (cancelChoice == 1) {
                    order.clearCart();
                    System.out.println("\n주문이 취소되었습니다.");
                    return;
                } else if (cancelChoice == 2) {
                    System.out.println("\n장바구니를 유지합니다.");
                    return;
                } else {
                    System.out.println("\n1 또는 2를 입력해주세요.");
                }
            } catch (Exception e) {
                printNumberOnlyMessage();
                sc.nextLine();
            }
        }
    }

    /**
     * 주문 메뉴 처리
     */
    private void processOrderMenu() {
        if (order.isEmpty()) {
            System.out.println("\n 장바구니가 비어 있습니다. 먼저 메뉴를 선택해주세요.");
            return;
        }

        while (true) {
            try {
                System.out.println("------------[장바구니 내역]------------");
                // 장바구니 출력
                order.printCartItems();
                System.out.println("-------------------------------------");

                System.out.println("\n1. 주문하기   2. 메인 메뉴로 돌아가기  3. 특정 메뉴 제외하기");
                System.out.print("입력: ");

                int orderChoice = sc.nextInt();

                if (orderChoice == 1) {
                    // 사용자 할인 유형 선택
                    selectUserType();
                    // 할인 적용된 주문이거나 일반 주문 완료 및 총 금액 출력
                    printOrderConfirmation();
                    return;
                } else if (orderChoice == 2) {
                    System.out.println("\n메인 메뉴로 돌아갑니다.");
                    return;
                } else if (orderChoice == 3) {
                    // 특정 메뉴 제거
                    removeItemFromCart();
                    return;
                } else {
                    System.out.println("\n1,2,3 중에 숫자를 입력해주세요.");
                }
            } catch (Exception e) {
                printNumberOnlyMessage();
                sc.nextLine();
            }
        }
    }

    /**
     * 장바구니에서 특정 메뉴 제거 - 스트림 활용
     */
    private void removeItemFromCart() {
        System.out.println("\n제외할 메뉴 전체 이름을 입력해주세요");
        System.out.print("메뉴 이름 입력 : ");
        sc.nextLine(); // 버퍼 비우기
        String menuName = sc.nextLine();

        // 제외할려는 메뉴가 맞는지 재확인
        boolean filtered = order.printCartFilterByName(menuName);

        if(filtered) {
            // 입력한 메뉴 이름이 동일하다면 제거
            order.removeOrderItemByName(menuName);
            System.out.println("\n '" + menuName + "'(이)가 장바구니에서 제거되었습니다.");
        } else {
            System.out.println("\n '" + menuName + "'(을)를 장바구니에서 찾을 수 없습니다.");
        }
    }

    /**
     * 행위 중심 : 상세 메뉴 선택 처리
     */
    private boolean detailMenuSelection(Menu menu) {
        while (true) {
            try {
                // 선택 상세 메뉴 출력
                printDetailMenu(menu);

                // 숫자 선택 (1,2,3,4....)
                int menuChoice = sc.nextInt();

                // 0 누를시 카테고리 메뉴로 복귀
                if (menuChoice == 0) {
                    System.out.println("메인 메뉴 선택으로 돌아갑니다.");
                    return false;
                }

                // 유효하지 않은 숫자 입력 처리
                if (!isValidMenuChoice(menuChoice, menu)) {
                    printInvalidInputMessage(0, menu.size());
                    continue;
                }

                // 선택한 메뉴
                MenuItem choiceMenu = menu.findMenuItem(menuChoice - 1);

                // 장바구니 추가 안내로 변경
                askToAddCart(choiceMenu);
                return true;
            } catch (Exception e) {
                printNumberOnlyMessage();
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
                System.out.println("-------------------------------------");
                System.out.print("\n선택한 메뉴 : ");
                choiceMenu.printInfoInCart();
                System.out.println("\n-------------------------------------");
                System.out.println("\n위 메뉴를 장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인       2. 취소");
                System.out.print("입력: ");

                int cartChoice = sc.nextInt();

                if (cartChoice == 1) {
                    // 수량 입력
                    System.out.print("수량을 입력해주세요: ");
                    int quantity = sc.nextInt();

                    if (quantity <= 0) {
                        System.out.println("\n수량은 1 이상이어야 합니다.");
                        continue;
                    }

                    order.addItem(choiceMenu, quantity);
                    return;

                } else if (cartChoice == 2) {
                    System.out.println("\n취소되었습니다.");
                    return;
                } else {
                    System.out.println("\n1 또는 2를 입력해주세요.");
                }

            } catch (Exception e) {
                printNumberOnlyMessage();
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
                System.out.println("------------[주문 예정 내역]-----------");
                order.printCartItems();
                System.out.println("-------------------------------------");
                System.out.println("\n추가 주문을 하시겠습니까?");
                System.out.print("최종 주문하시려면 0, 계속 추가 주문하시려면 아무 글자나 입력해주세요 : ");
                int continueChoice = sc.nextInt();

                if (continueChoice == 0) {
                    // 사용자 할인 여부
                    selectUserType();
                    // 할인 적용된 주문이거나 일반인 주문 최종 내역
                    printOrderConfirmation();
                    return true;
                } else {
                    // 아무 숫자 입력할 경우
                    System.out.println("계속 주문 합니다.");
                    sc.nextLine();
                    return true;
                }
            // 장바구니 내역이 없고, 주문하지 않았는데 취소하는 경우,
            } else {
                System.out.println("선택한 메뉴가 없습니다.");
                sc.nextLine();
                return true;
            }
        // 임의의 글자 입력하는경우
        } catch (Exception e) {
            System.out.println("계속 주문 합니다.");
            sc.nextLine();
            return true;
        }
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


    // ================ UI 및 출력 관련 메소드 : 행위 중심 메소드 이름으로 변경 ====================
    private void printWelcomeMessage() {
        // 식당 안내문
        System.out.println("=====================================");
        System.out.println("어서오세요. 맛있다 버거러 오신걸 환영합니다.");
        System.out.println("저희 식당은 청결함과 친절함을 강조합니다.");
        System.out.println("많은 이용바랍니다.");
        System.out.println("=====================================");
    }

    /**
     * 카테고리 메뉴 출력 메소드
     */
    private void printCategoryMenu() {
        System.out.println("=========[ SHAKESHACK MENU ]=========");
        System.out.println("맛있다 버거러 메뉴 입니다. 메뉴를 골라주세요.");

        // Menu 객체들의 카테고리 이름 출력
        for (int i = 0; i < categoryMenu.size(); i++) {
            System.out.println((i + 1) + ". " + categoryMenu.get(i).getCategoryName());
        }

        System.out.println("0. 종료");
        // 장바구니가 있는 경우에만 출력
        if (!order.isEmpty()) {
            System.out.println("==========[ ORDER MENU ]==========");
            System.out.println("4. Orders     | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel     | 진행중인 주문을 취소합니다.");
        }
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    private void printGoodbyeMessage() {
        System.out.println("키오스크 프로그램을 종료합니다.");
        System.out.println("이용해주셔서 감사합니다");
    }

    /**
     * 상세 메뉴 출력 메소드
     */
    private void printDetailMenu(Menu categoryMenu) {
        System.out.println("==========[ " + categoryMenu.getCategoryName().toUpperCase() + " MENU ]==========");
        System.out.println(categoryMenu.getCategoryName().toUpperCase() + " 메뉴 입니다. 원하는 음식을 골라주세요.");

        // Menu 객체의 메소드를 통해서 상세 메뉴 아이템 출력
        categoryMenu.printMenuItems();

        System.out.println("0. 뒤로가기");
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    /**
     * 선택 메뉴 출력 메소드 - 할인 적용하여 최종 주문 출력
     */
    private void printOrderConfirmation() {

        System.out.println("------------[최종 주문 내역]-----------");
        if (currentUserType != UserType.GENERAL) {
            // 일반인이 아닌경우 할인내역 출력
            order.printCartDiscount(currentUserType);
        } else {
            // 일반인인 경우
            order.printCartItems();
        }
        System.out.println("=======[ 주문이 완료되었습니다! ]========");

        order.clearCart();
    }

    private void printInvalidInputMessage(int min, int max) {
        System.out.println("잘못된 입력 입니다. " + min + "~" + max + " 사이의 숫자를 입력해주세요.");
    }

    private void printNumberOnlyMessage() {
        System.out.println("숫자만 입력이 가능합니다. 다시 입력해주세요.");
    }
}
