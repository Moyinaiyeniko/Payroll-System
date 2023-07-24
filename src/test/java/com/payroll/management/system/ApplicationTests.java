package com.payroll.management.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ApplicationTests {
	Calculator calculator =new Calculator();


	@Test//this is junit
	void itShouldAddNumbers() {
		//given
		int numberOne = 20;
		int numberTwo = 10;

		//when
		int result = calculator.add(numberOne,numberTwo);

		//then
		assertThat(result).isEqualTo(30);//this is assertJ
	}

	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}
}
