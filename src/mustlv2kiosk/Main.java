package mustlv2kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * lv.2 객체 지향 설계를 적용하여 햄버거 메뉴를 클래스로 관리하기
 * - 객체 지향을 학습하고, 데이터를 구조적으로 관리하며 프로그램을 설계하는 방법을 익힙니다.
 * - 햄버거 메뉴를 MenuItem 클래스와 List를 통해 관리
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


        // 컬렉션 add() 기본 방법도 있지만,
        // 메뉴 동적으로 변동 및 삭제 가능성이 있기에 다른 방법인 new ArrayList<>() + Arrays.asList() 조합을 적용
        List<MenuItem> burgers = new ArrayList<>(Arrays.asList(
                new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
                new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
                new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"),
                new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거")
        ));

        List<MenuItem> drinks = new ArrayList<>(Arrays.asList(
                new MenuItem("Cola", 2.0, "시원한 콜라"),
                new MenuItem("Sprite", 2.0, "상쾌한 사이다"),
                new MenuItem("Coffee", 3.0, "진한 아메리카노")
        ));


        List<MenuItem> desserts = new ArrayList<>(Arrays.asList(
                new MenuItem("IceCream", 4.0, "부드러운 바닐라 아이스크림"),
                new MenuItem("Cake", 5.0, "달콤한 초콜릿 케이크"),
                new MenuItem("Cookie", 3.5, "겉은 바삭하고 안은 촉촉한 쿠키")
        ));


        // 주문 반복문(무한)
        while(true){
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
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                continue;
            }

            // 선택된 메뉴 리스트
            List<MenuItem> selectMenus;

            switch (categoryChoice) {
                case 1:
                    selectMenus = burgers;
                    break;
                case 2:
                    selectMenus =  drinks;
                    break;
                case 3:
                    selectMenus = desserts;
                    break;
                default:
                    System.out.println("올바른 숫자를 입력해주세요.");
                    continue;
            }

            // 상세 메뉴 선택
            while(true){
                // 반복문 (메뉴 선택이 유효하다면)

                // 버거 상세 메뉴 출력
                printDetailMenu(categories[categoryChoice - 1], selectMenus);
                // 숫자 선택 (1,2,3,4....)
                int meniChoice = sc.nextInt();

                // 0 누를시 카테고리 메뉴로 복귀
                if (meniChoice == 0) {
                    System.out.println("카테고리 선택으로 돌아갑니다.");
                    break;
                }

                // 잘못된 입력 처리
                if (meniChoice < 0 || meniChoice > selectMenus.size()) {
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                    continue;
                }

                // 선택한 메뉴
                MenuItem chosenMenu = selectMenus.get(meniChoice - 1);

                // 주문 완료 안내
                // 선택한 메뉴 이름과 가격 정보 출력
                printChoiceMenu(chosenMenu);

                break;
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
