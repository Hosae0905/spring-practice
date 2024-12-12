package project.springbasic.singleton;

public class StatefulService {

//    private int price;      // 상태를 유지하는 필드

//    public void order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price;     // 여기가 문제
//    }

    // 필드가 공유되는 문제를 해결
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
