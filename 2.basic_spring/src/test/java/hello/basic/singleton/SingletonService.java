package hello.basic.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {
    /*
      클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
      그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
      private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다.
     */

    private static final SingletonService instance = new SingletonService();
    /*
    자기 자신을 내부에 private static 가진다. 그럼 클래스 레벨로 올라가기 때문 딱 하나만 존재하게 된다.
    주로 내부 클래스를 정의하는 이유는 외부에서는 사용될 일이 없고 내부적으로 별도의 클래스를 정의를 필요로 할 때 사용한다.
    */

    public static SingletonService getInstance() {
        return instance;
        /*
            이렇게 하면 자바가 뜰 때
            static에 new SingletonService() 라고 되어 있네?
            -> 내부적으로 객체를 알아서 생성해서 instance 참조에 넣어놓음.
            그럼 자기 자신을 인스턴스에 하나 생성함.

            객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다.
            이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
        */
    }

    private SingletonService() {
        // 생성자를 private로 만들어 놓으면 외부에서 생성 불가
    }

     public void logic() {
         System.out.println("싱글턴 객체 로직 호출");
     }

}
