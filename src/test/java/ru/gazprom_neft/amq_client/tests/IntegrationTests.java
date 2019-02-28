package ru.gazprom_neft.amq_client.tests;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Integration tests.
 */
@RunWith(CamelSpringRunner.class)
@EnableAutoConfiguration
@ContextConfiguration("classpath:spring/camel-context.xml")
@TestPropertySource("classpath:/application-test.properties")
public class IntegrationTests {

    private static final List<String> MANDATORY_FIELDS = Arrays.asList("request.unitId", "request.beginDateTime", "request.endDateTime");

    @EndpointInject(uri = "direct:beforeToken")
    private ProducerTemplate generalRoute;


    //----------------------FUEL integration tests------------------------

    /**
     * Fuel integration test.
     */
    @Test
    public void fuelIntegrationTest() {

        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/02");
        headers.put("CamelServletContextPath", "/statistics/fuel");
        String query = MANDATORY_FIELDS.get(0) + "=" + "12345" + "&" + MANDATORY_FIELDS.get(1) + "=" + "2018/01/01" + "&" +
                MANDATORY_FIELDS.get(2) + "=" + "2018/01/02";
        headers.put("CamelHttpQuery", query);
        Object o = null;
        try {
            o = generalRoute.requestBodyAndHeaders("test", headers);
            System.out.println(o);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        Assert.assertNotNull(o);
    }


    //----------------------ODOMETER integration tests------------------------

    /**
     * Odometer integration test.
     */
    @Test
    public void odometerIntegrationTest() {

        Map<String, Object> headers = new HashMap<>();
        headers.put(MANDATORY_FIELDS.get(0), "12345");
        headers.put(MANDATORY_FIELDS.get(1), "2018/01/01");
        headers.put(MANDATORY_FIELDS.get(2), "2018/01/02");
        headers.put("CamelServletContextPath", "/statistics/odometer");
        String query = MANDATORY_FIELDS.get(0) + "=" + "12345" + "&" + MANDATORY_FIELDS.get(1) + "=" + "2018/01/01" + "&" +
                MANDATORY_FIELDS.get(2) + "=" + "2018/01/02";
        headers.put("CamelHttpQuery", query);
        Object o = null;
        try {
            o = generalRoute.requestBodyAndHeaders("test", headers);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        Assert.assertNotNull(o);
    }


    /**
     * Available ids v 1 integration test.
     */
//----------------------AVAILABLE_IDS ROUTE tests------------------------
    @Test
    public void availableIdsV1IntegrationTest() {

        Map<String, Object> headers = new HashMap<>();
        headers.put("CamelServletContextPath", "/api/units/availableIds");
        Object o = null;
        try {
            o = generalRoute.requestBodyAndHeaders("test", headers);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        Assert.assertNotNull(o);
    }

    /**
     * Available ids v 2 integration test.
     */
    @Test
    public void availableIdsV2IntegrationTest() {

        Map<String, Object> headers = new HashMap<>();
        headers.put("CamelServletContextPath", "/api/data/terminal/availableIds");
        Object o = null;
        try {
            o = generalRoute.requestBodyAndHeaders("test", headers);
        } catch (Exception ex) {
            Assert.assertNull(ex);
        }
        Assert.assertNotNull(o);
    }

}