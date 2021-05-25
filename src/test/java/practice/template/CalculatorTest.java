package practice.template;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void name() throws IOException {
        //given
        Calculator calculator = new Calculator();
        //when
        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
        //then
        assertThat(sum).isEqualTo(10);
    }
}