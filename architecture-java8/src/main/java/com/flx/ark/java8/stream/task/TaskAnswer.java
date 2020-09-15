package com.flx.ark.java8.stream.task;


import com.flx.ark.java8.lambda.task.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskAnswer {

    static List<Employee> employees = Arrays.asList(

            new Employee("tom",18,5001.99, Employee.Status.FREE),
            new Employee("army",38,1002.99, Employee.Status.BUSY),
            new Employee("jack",8,2003.99,Employee.Status.VOCATION),
            new Employee("wang",16,7004.99, Employee.Status.FREE),
            new Employee("lucy",8,9005.99, Employee.Status.BUSY)

    );



    public static void main(String[] args) {

//        taskOne();
//        taskTwo();
        taskThree();

    }

    private static void taskThree() {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
        transactions.stream()
                .filter(t->t.getYear()==2011)
                .sorted((x,y)->Integer.compare(x.getValue(),y.getValue()))
                .forEach(System.out::println);

        //2. 交易员都在哪些不同的城市工作过？
        transactions.stream()
                .map(t->t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);

        //3. 查找所有来自剑桥的交易员，并按姓名排序
        transactions.stream()
                .map(t->t.getTrader())
                .distinct()
                .filter(t->t.getCity().equals("Cambridge"))
                .sorted((x,y)->x.getName().compareTo(y.getName()))
                .forEach(System.out::println);

        //4. 返回所有交易员的姓名字符串，按字母顺序排序
        transactions.stream()
                .map(t->t.getTrader().getName())
                .sorted()
                .forEach(System.out::print);
        System.out.println();
        transactions.stream()
                .map(t->t.getTrader().getName())
                .sorted()
                .reduce("",String::concat);
        System.out.println("按照姓名的每个字符排序");
        transactions.stream()
                .map(t->t.getTrader().getName())
                .flatMap(TaskAnswer::filterCharacter)
                .sorted()
                .forEach(System.out::print);

        //5. 有没有交易员是在米兰工作的？
        boolean milan = transactions.stream()
                .allMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println("有没有交易员在米兰的："+milan);

        //6. 打印生活在剑桥的交易员的所有交易额
        Optional<Integer> sum = transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .map(e -> e.getValue())
                .reduce(Integer::sum);

        //7. 所有交易中，最高的交易额是多少
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compare);

        //8. 找到交易额最小的交易
        Optional<Transaction> min = transactions.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
    }

    private static Stream<Character> filterCharacter(String source){
        List<Character> list = new ArrayList<>();
        for (Character c:source.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    /**
     * 2.怎样用map和reduce方法数一数流中由多少个Employee？
     */
    private static void taskTwo() {

        Optional<Integer> sum = employees.stream()
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(sum.get());

    }

    /**
     * 1.给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
     */
    private static void taskOne() {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        List<Integer> collect = list.stream()
                .map(x -> x * x * x)
                .collect(Collectors.toList());
        System.out.println(collect);
    }



}
