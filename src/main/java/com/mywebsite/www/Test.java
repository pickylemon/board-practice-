package com.mywebsite.www;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.annotation.ElementType.*;

@MyAnno("on class")
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Test test = new Test();

//        메서드 가져오기
        System.out.println("1.메서드 가져오기");
        Method annoTest = Test.class.getMethod("annoTest", String.class);
        System.out.println("annoTest = " + annoTest);

        // 클래스에 적용된 애너테이션 출력하기
        System.out.println("2.클래스에 적용된 애너테이션 출력하기");
        Arrays.stream(test.getClass().getAnnotations()).forEach(System.out::println);

//        해당 클래스에 선언 된 메서드만 출력하기
        System.out.println("3.해당 클래스에 선언된 메서드만 출력하기(getDeclaredMethod)");
        Arrays.stream(Test.class.getDeclaredMethods()).forEach(System.out::println);

//        getMethods 는 물려받은 메서드까지 포함된다.
        System.out.println("4.모든메서드 출력(getMethods)");
        Arrays.stream(Test.class.getMethods()).forEach(System.out::println);

//        ----------------------------------------- 반복문 시작 ---------------------------------------
//        애너테이션이 붙은 메서드를 대상으로 하는 반복문입니다.
        Arrays.stream(Test.class.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(MyAnno.class)).forEach(method -> {
            // 메서드 이름 출력
//                    System.out.println(method.getName());
            // 메서드 자체에 선언된 어노테이션 찾기
//            System.out.println(Arrays.toString(method.getAnnotations())); // 덕지덕지 애너테이션을 붙였지만 메서드 자체를 적용대상으로 하는 애너테이션만 출력되는 것을 볼 수 있다.
//            System.out.println(method.isAnnotationPresent(MyAnno.class));
            // 메서드에 선언된 매개변수 가져오기
//                    System.out.println(Arrays.toString(method.getParameterTypes()));
            // 메서드에 선언된 제네릭 타입변수 가져오기.
//                    System.out.println(Arrays.toString(method.getTypeParameters()));
            // 제네릭 타입변수에 적용된 어노테이션 찾기.
//            TypeVariable<Method>[] typeParameters = method.getTypeParameters();
//            System.out.println("my method type param is = " + typeParameters[0]);
//            System.out.println(Arrays.toString(typeParameters[0].getAnnotations()));
            // 메서드의 매개변수에 붙은 어노테이션 출력하기
//            Arrays.stream(method.getParameterAnnotations()).map(Arrays::toString).forEach(System.out::println);
        });
//        --------------------------------- 반복문 종료 -------------------------------------


        // 깨달은점. 어노테이션의 적용대상이 여러곳으로 정의되어 있더라도 애너테이션이 적용되는 '범위'는 애너테이션이 사용된 위치의 대상 한 곳이다.
        // @MyAnno 애너테이션의 적용대상이 클래스, 메서드, 매개변수 이더라도 해당 애너테이션이 사용된 위치의 한 대상이 적용범위가 된다.
        // 이를테면 메서드 선언 위에 애너테이션을 사용하면 해당 애너테이션은 매개변수에는 적용되지 않는다.
    }

    @MyAnno("on method")
    public <@MyAnno("on Type Variable") T> void annoTest(@MyAnno("on param") String param) {
        System.out.println(param);
    }
}

// 갓 만든 따끈따끈한 애너테이션이에요.
@Target({TYPE, METHOD, TYPE_PARAMETER, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
    String value() default "hello";
}

//@Retention(RetentionPolicy.RUNTIME)
//@interface MyAnno2{
//    String value() default "nono";
//}