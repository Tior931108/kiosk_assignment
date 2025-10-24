package mustlv3kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Kiosk : 키오스크 프로그램의 메뉴를 관리하고 사용자 입력을 처리하는 클래스
 */
public class Kiosk {
    // MenuItem을 관리하는 메소드
    private List<MenuItem> burgers;
    private List<MenuItem> drinks;
    private List<MenuItem> desserts;
    private Scanner sc;

    // 생성자 : main 함수에서 menuItem의 각각 burgers, drinks, desserts를 전달받음
    public Kiosk(List<MenuItem> burgers, List<MenuItem> drinks,List<MenuItem> desserts) {
        this.burgers = burgers;
        this.drinks = drinks;
        this.desserts = desserts;
        this.sc = new Scanner(System.in);
    }

    /**
     * 키오스크 프로그램 시작 메소드
     */
    public void start() {
        // 식당 안내문
        System.out.println("=====================================");
        System.out.println("어서오세요. 맛있다 버거러 오신걸 환영합니다.");
        System.out.println("저희 식당은 청결함과 친절함을 강조합니다.");
        System.out.println("많은 이용바랍니다.");
        System.out.println("=====================================\n");

        String[] categories = {"Burger", "Drink", "Dessert"};

        // 주문 반복문(무한)
        while(true){
            try {
                // 반복문 (카테고리 선택이 유효하다면)
                // 메뉴 고르기
                printCategoryMenu(categories);
                int categoryChoice = sc.nextInt();

                // 종료 안내
                if(categoryChoice == 0){
                    break;
                }

                // 선택 잘못된 입력 처리
                if (categoryChoice < 0 || categoryChoice > categories.length) {
                    System.out.println("잘못된 입력입니다. 1~" + categories.length + " 사이의 숫자를 선택해주세요.");
                    continue;
                }

                // 선택된 메뉴 리스트
                List<MenuItem> selectMenus = getMeunsByCategory(categoryChoice);


                // 상세 메뉴 선택
                boolean orderCompleted = false;

                // 반복문 (메뉴 선택이 유효하다면)
                while(!orderCompleted){
                    try {
                        // 선택 상세 메뉴 출력
                        printDetailMenu(categories[categoryChoice - 1], selectMenus);
                        // 숫자 선택 (1,2,3,4....)
                        int menuChoice = sc.nextInt();

                        // 0 누를시 카테고리 메뉴로 복귀
                        if (menuChoice == 0) {
                            System.out.println("카테고리 선택으로 돌아갑니다.");
                            break;
                        }

                        // 유효하지 않은 숫자 입력 처리
                        if (menuChoice < 0 || menuChoice > selectMenus.size()) {
                            System.out.println("잘못된 입력입니다. 1~" + selectMenus.size() + " 사이의 숫자를 선택해주세요.");
                            continue;
                        }

                        // 선택한 메뉴
                        MenuItem choiceMenu = selectMenus.get(menuChoice - 1);

                        // 주문 완료 안내
                        // 선택한 메뉴 이름과 가격 정보 출력
                        printChoiceMenu(choiceMenu);
                        orderCompleted = true;

                    } catch (InputMismatchException e) {
                        System.out.println("숫자만 입력이 가능합니다. 다시 입력해주세요.");
                        sc.nextLine(); // 버퍼 비우기
                    }
                }

                // 주문 완료 후 계속 진행 여부 확인
                if (orderCompleted) {
                    try {
                        // 주문 프로그램 종료 안내
                        System.out.println("추가 주문을 하시겠습니까?");
                        System.out.print("종료하려면 0, 계속 주문하시려면 아무 글자나 입력해주세요 : ");
                        int continueChoice = sc.nextInt();
                        if(continueChoice == 0){
                            // 0 입력시 종료
                            break;
                        }
                        System.out.println();

                    } catch (InputMismatchException e) {
                        System.out.println("계속 주문합니다.");
                        sc.nextLine(); // 버퍼 비우기
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("숫자만 입력이 가능합니다. 다시 입력해주세요.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
        System.out.println("키오스크 프로그램을 종료합니다.");
        System.out.println("이용해주셔서 감사합니다");
        sc.close();
    }

    /**
     * 각 카테고리 번호에 맞는 상세 메뉴 리스트를 반환
     * @param categoryChoice
     * @return
     */
    private List<MenuItem> getMeunsByCategory(int categoryChoice) {
        switch (categoryChoice) {
            case 1:
                return burgers;
            case 2:
                return drinks;
            case 3:
                return desserts;
            default:
                return new ArrayList<>();
        }
    }

    /**
     * 카테고리 메뉴 출력 메소드
     * @param categories 카테고리 메뉴들
     */
    public static void printCategoryMenu(String[] categories) {
        System.out.println("=========[ SHAKESHACK MENU ]=========");
        System.out.println("맛있다 버거러 메뉴 입니다. 메뉴를 골라주세요.");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.println("0. 종료");
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    /**
     * 상세 메뉴 출력 메소드
     * @param categoryName 공통 메뉴 이름
     * @param detailMenus 상세 메뉴이름, 가격, 설명 - 컬렉션
     */
    public static void printDetailMenu(String categoryName, List<MenuItem> detailMenus) {
        System.out.println("==========[ " + categoryName.toUpperCase() + " MENU ]==========");
        System.out.println(categoryName.toUpperCase() + " 메뉴 입니다. 원하는 음식을 골라주세요.");
        // 컬렉션 사이즈 만큼 반복
        for (int i = 0; i < detailMenus.size(); i++) {
            // 상세 메뉴 컬렉션 값 가져오기
            detailMenus.get(i).printInfo(i+1);
        }
        System.out.println("0. 뒤로가기");
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }

    /**
     * 선택 메뉴 출력 메소드
     * @param choiceMenu 선택 메뉴 1차원 배열
     */
    public static void printChoiceMenu(MenuItem choiceMenu) {
        System.out.println("=======[ 주문이 완료되었습니다! ]========");

        // 출력 서식 맞추기 (이스케이프 문자 활용)
        // %-16s : 메뉴 이름 왼쪽 정렬, 16칸 확보
        // % .1f : 실수 소수점 첫째자리까지 출력
        System.out.printf("선택한 메뉴: %-16s | W %.1f |\n",
                choiceMenu.getName(), choiceMenu.getPrice());

        System.out.println("=====================================");
    }
}
