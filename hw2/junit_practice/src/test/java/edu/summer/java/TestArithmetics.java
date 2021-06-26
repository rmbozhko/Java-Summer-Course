package edu.summer.java;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

public class TestArithmetics {
    private static Arithmetics arithmetics;

    @BeforeAll
    public static void     init() {
        arithmetics = new Arithmetics();
    }

    @BeforeAll
    public static void      checkInitObject() {
        Assertions.assertNotNull(arithmetics, "Object not initialized");
    }

    @AfterAll
    public static void      checkNullObject() {
        arithmetics = null;
        Assertions.assertNull(arithmetics, "Object is still initialized");
    }

    @BeforeEach
    public void     runningTestMessage() {
        System.out.println("Running test");
    }

    @AfterEach
    public void     finishedTestMessage() {
        System.out.println("Finished test");
    }

    @Disabled // @Ignore
    @Test
    public void     testAdd() {
        Assertions.assertEquals(2D, arithmetics.add(1D, 1D));
        Assertions.assertEquals(4D, arithmetics.add(3D, 1D));
    }

    @Test
    public void     testDeduct() {
        Assertions.assertEquals(0D, arithmetics.deduct(1D, 1D));
    }

    @Test
    public void     testMult() {
        Assertions.assertEquals(1D, arithmetics.mult(1D, 1D));
    }

    @Test
    public void     testDiv() {
        Assertions.assertEquals(1D, arithmetics.div(1D, 1D));
    }

    @Test
    public void     testZeroDiv() {
        // a method for each possible exception thrown by method
        Assertions.assertThrows(java.lang.ArithmeticException.class, () -> arithmetics.div(1D, 0D));
    }

    @Test
    public void     testLessThan() {
        double a = 4;
        double b = 2;
        Assertions.assertTrue(arithmetics.greaterThan(a, b), String.format("%f is more than %f", a, b));
    }

    @Test
    public void     testMoreThan() {
        double a = 4;
        double b = 2;
        Assertions.assertFalse(arithmetics.greaterThan(b, a), String.format("%f is more than %f", a, b));
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void     testTimeout() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
