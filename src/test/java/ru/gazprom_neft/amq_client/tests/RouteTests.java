package ru.gazprom_neft.amq_client.tests;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Route tests.
 */
public class RouteTests extends CamelSpringTestSupport {

    private static final List<String> MANDATORY_FIELDS = Arrays.asList("request.unitId", "request.beginDateTime", "request.endDateTime");

    @Override
    public String isMockEndpointsAndSkip() {
        return "((direct:beforeToken)|(direct:beforeScout)|(direct:finish))";
    }

    @Override
    protected AbstractXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:spring/camel-context.xml");
    }

    /**
     * Fuel validation test, only unitId is passed
     *
     * @throws Exception the exception
     */
//----------------------FUEL validation tests------------------------
    @Test
    public void fuelValidationTestUnitId() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        template.sendBodyAndHeader("direct://fuelValidation", "test", MANDATORY_FIELDS.get(0), "12345");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, only beginDateTime is passed
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTestBeginDateTime() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedHeaderReceived("Status", 1);
        testEndpoint.expectedMessageCount(1);
        template.sendBodyAndHeader("direct://fuelValidation", "test", MANDATORY_FIELDS.get(1), "2018/01/01");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, only endDateTime is passed
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTestEndDateTime() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        template.sendBodyAndHeader("direct://fuelValidation", "test", MANDATORY_FIELDS.get(2), "2018/01/01");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, unitId and beginDate are passed.
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTestUnitIdAndBeginDate() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        template.sendBodyAndHeaders("direct://fuelValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, unitId and endDate are passed.
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTestUnitIdAndEndDate() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/01");
        template.sendBodyAndHeaders("direct://fuelValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, 1 incorrect date format.
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTest1IncorrectDateFormat() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018-01-01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/01");
        template.sendBodyAndHeaders("direct://fuelValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test, 2 incorrect dates format.
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTest2IncorrectDatesFormat() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018-01-01");
        headers.put(MANDATORY_FIELDS.get(2), "2018-01-01");
        template.sendBodyAndHeaders("direct://fuelValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Fuel validation test all correct.
     *
     * @throws Exception the exception
     */
    @Test
    public void fuelValidationTestAllCorrect() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedBodiesReceived("test");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(0)).isEqualTo("12345");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(1)).isEqualTo("2018/01/01");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(2)).isEqualTo("2018/01/02");
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/02");
        try {
            template.sendBodyAndHeaders("direct://fuelValidation", "test", headers);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, only unitId is passed
     *
     * @throws Exception the exception
     */
//----------------------ODOMETER validation tests------------------------
    @Test
    public void odometerValidationTestUnitId() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        template.sendBodyAndHeader("direct://odometerValidation", "test", MANDATORY_FIELDS.get(0), "12345");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test,  only beginDateTime is passed
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTestBeginDateTime() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        template.sendBodyAndHeader("direct://odometerValidation", "test", MANDATORY_FIELDS.get(1), "2018/01/01");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, only endDateTime is passed
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTestEndDateTime() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        template.sendBodyAndHeader("direct://odometerValidation", "test", MANDATORY_FIELDS.get(2), "2018/01/01");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, unitId and beginDate are passed.
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTestUnitIdAndBeginDate() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        template.sendBodyAndHeaders("direct://odometerValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, unitId and endDate are passed.
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTestUnitIdAndEndDate() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/01");
        template.sendBodyAndHeaders("direct://odometerValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, 1 incorrect date format.
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTest1IncorrectDateFormat() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018-01-01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/01");
        template.sendBodyAndHeaders("direct://odometerValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, 2 incorrect dates format.
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTest2IncorrectDatesFormat() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedHeaderReceived("Status", 1);
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018-01-01");
        headers.put(MANDATORY_FIELDS.get(2), "2018-01-01");
        template.sendBodyAndHeaders("direct://odometerValidation", "test", headers);
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Odometer validation test, all correct.
     *
     * @throws Exception the exception
     */
    @Test
    public void odometerValidationTestAllCorrect() throws Exception {
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:beforeToken");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedBodiesReceived("test");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(0)).isEqualTo("12345");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(1)).isEqualTo("2018/01/01");
        testEndpoint.message(0).header(MANDATORY_FIELDS.get(2)).isEqualTo("2018/01/02");
        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/02");
        try {
            template.sendBodyAndHeaders("direct://odometerValidation", "test", headers);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        testEndpoint.assertIsSatisfied();
    }

    //----------------------TOKEN ROUTE tests------------------------

    /**
     * Token test.
     *
     * @throws Exception the exception
     */
    @Test
    public void tokenTest() throws Exception {
        MockEndpoint testEndpoint = getMockEndpoint("mock:direct:beforeScout");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.expectedBodiesReceived("test");
        testEndpoint.message(0).header("Authorization").isEqualTo("testToken");
        template.sendBodyAndHeader("direct://token", "test", "testHeader", "skjdgfhwsjhd");
        testEndpoint.assertIsSatisfied();
    }

    /**
     * Scout test.
     *
     * @throws Exception the exception
     */
//----------------------SCOUT ROUTE tests------------------------
    @Test
    public void scoutTest() throws Exception {

        template.sendBodyAndHeader("direct:scout", "test", "testHeader", "skjdgfhwsjhd");
        MockEndpoint testEndpoint =
                getMockEndpoint("mock:direct:finish");
        testEndpoint.expectedMessageCount(1);
        testEndpoint.message(0).body().isEqualTo("test completed");
        testEndpoint.assertIsSatisfied();

    }
}