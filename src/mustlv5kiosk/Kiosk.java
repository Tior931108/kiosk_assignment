package mustlv5kiosk;

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
    private final Scanner sc;

    // 생성자 : Menu 리스트를 전달받음
    public Kiosk(List<Menu> categoryMenu) {
        // 방어적 복사
        this.categoryMenu = new ArrayList<>(categoryMenu);
        this.sc = new Scanner(System.in);
    }

    /**
     * 키오스크 프로그램 시작 메소드
     */
    public void start() {
        // 식당 환영 안내문
        printWelcomeMessage();

        // 주문 반복문(무한)
        while(true){
            try {
                // 카테고리 매뉴 출력
                printCategoryMenu();

                // 메뉴 고르기
                int categoryChoice = sc.nextInt();

                // 종료 안내
                if(categoryChoice == 0){
                    break;
                }

                // 선택 잘못된 입력 처리
                if (!isValidCategoryChoice(categoryChoice)) {
                    printInvalidInputMessage(1, categoryMenu.size());
                    continue;
                }

                // 선택된 Menu 객체 가져오기
                Menu selectMenus = findMenuAt(categoryChoice - 1);

                // 상세 메뉴 출력
                if(detailMenuSelection(selectMenus)){
                    // 계속 주문 진행을 안한다면 종료
                    if(!askToContinue()) {
                        break;
                    }
                }

            } catch (Exception e) {
                System.out.println("숫자만 입력이 가능합니다. 다시 입력해주세요.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
        // 종료 안내
        printGoodbyeMessage();
        sc.close();
    }

    /**
     * 행위 중심 : 상세 메뉴 선택 처리
     */
    private boolean detailMenuSelection(Menu menu) {
        while(true){
            try {
                // 선택 상세 메뉴 출력
                printDetailMenu(menu);

                // 숫자 선택 (1,2,3,4....)
                int menuChoice = sc.nextInt();

                // 0 누를시 카테고리 메뉴로 복귀
                if (menuChoice == 0) {
                    System.out.println("카테고리 선택으로 돌아갑니다.");
                    return false;
                }

                // 유효하지 않은 숫자 입력 처리
                if (!isValidMenuChoice(menuChoice, menu)) {
                    printInvalidInputMessage(1, menu.size());
                    continue;
                }

                // 선택한 메뉴
                MenuItem choiceMenu = menu.findMenuItem(menuChoice - 1);

                // 주문 완료 안내
                // 선택한 메뉴 이름과 가격 정보 출력
                printOrderConfirmation(choiceMenu);
                return true;
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
            System.out.println("추가 주문을 하시겠습니까?");
            System.out.print("종료하려면 0, 계속 주문하시려면 아무 글자나 입력해주세요 : ");
            int continueChoice = sc.nextInt();
            return continueChoice != 0;
        } catch (Exception e) {
            System.out.println("계속 주문 합니다.");
            sc.nextLine();
            return true;
        }
    }


    /**
     * 행위 중심 : 카테고리 선택 유효성 검증
     * @param choice 입력 숫자
     * @return 입력 숫자가 카테고리 메뉴 숫자 범위 안인지 확인
     */
    private boolean isValidCategoryChoice(int choice) {
        return choice >= 1 && choice <= categoryMenu.size();
    }

    /**
     * 행위 중심 : 상세 메뉴 선택 유효성 검증
     * @param choice 입력 숫자
     * @param menu 선택한 카테고리 메뉴
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
        for (int i = 0; i < categoryMenu.size() ; i++) {
            System.out.println((i + 1) + ". " +  categoryMenu.get(i).getCategoryName());
        }

        System.out.println("0. 종료");
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
     * 선택 메뉴 출력 메소드
     */
    private void printOrderConfirmation(MenuItem choiceMenu) {
        System.out.println("=======[ 주문이 완료되었습니다! ]========");

        // 출력 서식 맞추기 (이스케이프 문자 활용)
        // %-16s : 메뉴 이름 왼쪽 정렬, 16칸 확보
        // % .1f : 실수 소수점 첫째자리까지 출력
        System.out.printf("선택한 메뉴: %-16s | W %.1f |\n",
                choiceMenu.getName(), choiceMenu.getPrice());

        System.out.println("=====================================");
    }

    private void printInvalidInputMessage(int min, int max) {
        System.out.println("잘못된 입력 입니다. " + min + "~" + max + " 사이의 숫자를 입력해주세요.");
    }

    private void printNumberOnlyMessage() {
        System.out.println("숫자만 입력이 가능합니다. 다시 입력해주세요.");
    }
}
