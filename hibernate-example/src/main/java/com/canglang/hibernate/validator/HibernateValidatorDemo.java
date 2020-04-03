package com.canglang.hibernate.validator;

import com.canglang.hibernate.validator.vo.Car;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author leitao.
 * @category
 * @time: 2020/3/27 0027-16:43
 * @version: 1.0
 * @description:
 **/
public class HibernateValidatorDemo {

    private static Validator validator;


    public static void main(String[] args) {
        printValidatorResult(validator.validate(new Car(null, 100, 2)));



    }

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static void printValidatorResult(Set<ConstraintViolation<Car>> constraintViolations) {
        for (ConstraintViolation<Car> violation : constraintViolations) {
            System.out.println(violation.getMessage());
        }
    }

}