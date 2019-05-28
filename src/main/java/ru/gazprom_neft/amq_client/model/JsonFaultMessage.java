package ru.gazprom_neft.amq_client.model;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Класс-обёртка любого исключения
 */
@Data
@Log4j
public class JsonFaultMessage {

    private Integer code;
    private String description;
    private String cause;

    /**
     * Конструктор класса
     *
     * @param code        - код исключения
     * @param description - описание исключения
     * @param cause       - причина исключения
     */
    public JsonFaultMessage(Integer code, String description, Throwable cause) {
        this.code = code;
        this.description = description;
        if (cause != null) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {
                ExceptionUtils.printRootCauseStackTrace(cause, ps);
            } catch (UnsupportedEncodingException e) {
                log.error(e);
            }

            this.cause = new String(baos.toByteArray(), StandardCharsets.UTF_8).replace("\r\n\t", " ").replace("\r\n", " ").replace("\n\t", " ").replace("\n", " ");
        }
    }
}
