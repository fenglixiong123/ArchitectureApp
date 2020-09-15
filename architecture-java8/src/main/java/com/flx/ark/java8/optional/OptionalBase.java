package com.flx.ark.java8.optional;

import com.flx.noodle.java8.lambda.task.Employee;

import java.util.Optional;

/**
 *
 * Optional
 *
 * of(T t)--创建一个Optional 实例
 * empty--创建一个空的Optional实例
 * ofNullable(T t)--若t不为空，创建Optional实例，否则创建空实例
 * isPresent()判断是否包含值
 * orElse(T t)--如果调用包含值，则返回值，否则返回t
 * orElseGet(Supplier s)--包含值返回值，否则返回s获取的值
 * map(Function f)--如果优质返回处理后的Optional，否则返回empty、
 * flatmap
 */
public class OptionalBase {

    public static void main(String[] args) {

        testOptional();

        testOptionalAdv();
    }

    //Optional高级应用
    private static void testOptionalAdv() {

        //获取男人心中女神的名字
        Man man = new Man();
        String goddessName = getGoddessName(Optional.ofNullable(man));
        System.out.println(goddessName);

    }

    private static String getGoddessName(Optional<Man> man){
        return man.orElse(new Man())
                .getGoddess()
                .orElse(new Goddess("cang lao shi"))
                .getName();
    }

    //Optional基础语法练习
    private static void testOptional() {

        //of(T t)--参数为非空值
        Optional<Employee> employeeOp1 = Optional.of(new Employee("王小二",22,99.00, Employee.Status.BUSY));
        System.out.println(employeeOp1.get());

        //构建空对象
        Optional<Employee> employeeOp2 = Optional.empty();
        System.out.println(employeeOp2.isPresent());

        //构建一个未知对象
        Employee employee = new Employee("jack",22,35.00);
        Optional<Employee> employeeOp3 = Optional.ofNullable(null);
        //orElse如果为null使用默认值
        Double salary = employeeOp3.orElse(new Employee("mary",18,999)).getSalary();
        System.out.println("salary:"+salary);

        //map
        Optional<Employee> employeeOp4 = Optional.ofNullable(new Employee("lucy",22,8888));
        Optional<String> optionalS = employeeOp4.map(e -> e.getName());

    }

}
