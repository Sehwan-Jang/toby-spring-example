package practice.template;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;
    private String path;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        path = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    void sum() throws IOException {
        assertThat(calculator.calcSum(path)).isEqualTo(10);
    }

    @Test
    void time() throws IOException {
        assertThat(calculator.calcTimes(path)).isEqualTo(24);
    }

    @Test
    void concat() throws IOException {
        assertThat(calculator.concatenate(path)).isEqualTo("1234");
    }
}