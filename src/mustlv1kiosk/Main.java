package mustlv1kiosk;

import java.util.Scanner;

/**
 * lv.1 기본적인 키오스크를 프로그래밍 해보자.
 * - 입력 처리와 간단한 흐름 제어
 * - Scanner 활용법, 조건문, 반복문을 재확인하며 입역 데이터를 처리하는 방법 강화
 */
public class Main {
    public static void main(String[] args) {
        // 스캐너 선언
        Scanner sc = new Scanner(System.in);

        // 식당 안내문
        System.out.println("=====================================");
        System.out.println("어서오세요. 맛있다 버거러 오신걸 환영합니다.");
        System.out.println("저희 식당은 청결함과 친절함을 강조합니다.");
        System.out.println("많은 이용바랍니다.");

        // 각 카테고리별 메뉴 (이름, 가격, 설명)
        // 메뉴 데이터 정의
        String[] categories = {"Burger", "Drink", "Dessert"};

        // 이름, 가격, 설명 형식이 동일한 메뉴가 여러개 이므로 2차원 배열로 선언
        String[][] burgers = {
                {"ShackBurger", "W 6.9", "토마토, 양상추, 쉑소스가 토핑된 치즈버거"},
                {"SmokeShack", "W 8.9", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"},
                {"Cheeseburger", "W 6.9", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"},
                {"Hamburger", "W 5.4", "비프패티를 기반으로 야채가 들어간 기본버거"}
        };

        String[][] drinks = {
                {"Cola", "W 2.0", "시원한 콜라"},
                {"Sprite", "W 2.0", "상쾌한 사이다"},
                {"Coffee", "W 3.0", "진한 아메리카노"}
        };

        String[][] desserts = {
                {"IceCream", "W 4.0", "부드러운 바닐라 아이스크림"},
                {"Cake", "W 5.0", "달콤한 초콜릿 케이크"},
                {"Cookie", "W 3.5", "겉은 바삭하고 안은 촉촉한 쿠키"}
        };

        // 주문 반복문(무한)
        while(true){
            // 반복문 (카테고리 선택이 유효하다면)
            // 메뉴 고르기
            printCategoryMenu(categories);
            int categoryChoice = sc.nextInt();

            // 선택 잘못된 입력 처리
            if (categoryChoice < 0 || categoryChoice > categories.length) {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                continue;
            }

            // 상세 메뉴 리스트 저장 배열 - 2차원 배열
            String[][] selectMenus;

            // 사용자가 선택한 메뉴 저장하는 배열
            // 2차원 배열중 선택한 메뉴를 새 배열에 복사하여 1차원 배열에 저장,
            // 복사 : 새로운 메모리에 복사하여 원본 데이터 수정 방지
            String[] choiceMenu = new String[3];

            // 배열 인덱스 : 상세 메뉴 숫자 입력값을 인덱스로 넣을 변수
            int index;

            switch (categoryChoice) {
                case 1:
                    while(true){
                        // 반복문 (메뉴 선택이 유효하다면)
                        // 1. 버거 선택 - 직접 참조 [ 같은 메모리를 가리킴 ]
                        selectMenus = burgers;
                        // 버거 상세 메뉴 출력
                        printDetailMenu(categories[categoryChoice - 1], selectMenus);
                        // 숫자 선택 (1,2,3,4....)
                        int burgerChoice = sc.nextInt();

                        // 0 누를시 카테고리 메뉴로 복귀
                        if (burgerChoice == 0) {
                            continue;
                        }

                        // 잘못된 입력 처리
                        if (burgerChoice < 0 || burgerChoice > selectMenus.length) {
                            System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                            continue;
                        }

                        // 선택한 숫자를 2차원 배열의 행 인덱스로 삽입
                        index = burgerChoice - 1;
                        // 선택한 숫자 입력과 2차원 배열에 저장된 메뉴 행 인덱스가 같은 메뉴정보를 1차원 배열에 저장
                        for (int i = 0; i < selectMenus[index].length; i++) {
                            choiceMenu[i] = selectMenus[index][i];
                        }

                        // 주문 완료 안내
                        // 선택한 메뉴 메뉴 이름과 가격 정보 출력
                        System.out.println("=======[ 주문이 완료되었습니다! ]========");
                        System.out.println("선택한 메뉴: " + choiceMenu[0]
                                + " | " + choiceMenu[1]);
                        System.out.println("=====================================");

                        break;
                    }
                case 2:
                    while(true){
                        // 2. 음료 선택
                        selectMenus = drinks;

                        break;
                    }
                case 3:
                    while(true){
                        // 3. 디저트 선택
                        selectMenus = desserts;

                        break;
                    }
                case 0:
                    // 프로그램 종료
                    break;
                default:
                    System.out.println("올바른 숫자를 입력해주세요.");
                    continue;
            }

            // 주문 프로그램 종료 안내
            System.out.println("키오스크를 종료하시겠습니까?");
            System.out.print("종료하려면 0, 계속 주문하시려면 아무 숫자나 입력해주세요 : ");
            int yesOrNo = sc.nextInt();
            if(yesOrNo == 0){
                // 0 입력시 종료
                break;
            }
        }
        System.out.println("키오스크 프로그램을 종료합니다.");
        System.out.println("이용해주셔서 감사합니다");
        sc.close();
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
     * @param detailMenus 상세 메뉴이름, 가격, 설명
     */
    public static void printDetailMenu(String categoryName, String[][] detailMenus) {
        System.out.println("==========[ " + categoryName.toUpperCase() + " MENU ]==========");
        System.out.println(categoryName.toUpperCase() + " 메뉴 입니다. 원하는 음식을 골라주세요.");
        for (int i = 0; i < detailMenus.length; i++) {
                System.out.println((i + 1) + ". " + detailMenus[i][0] // 상세 메뉴 이름
                        + " | " + detailMenus[i][1] // 상세 가격
                        + " | " + detailMenus[i][2]); // 상세 설명
        }
        System.out.println("0. 뒤로가기");
        System.out.println("=====================================");
        System.out.print("입력 : ");
    }
}
