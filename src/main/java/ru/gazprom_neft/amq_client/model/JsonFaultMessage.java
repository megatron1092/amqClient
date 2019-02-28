package ru.gazprom_neft.amq_client.model;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;


/**
 * The type Json fault message.
 */
@Data
public class JsonFaultMessage {


    private Integer code;
    private String description;
    private String cause;


    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Instantiates a new Json fault message.
     *
     * @param code        the code
     * @param description the description
     * @param cause       the cause
     */
    public JsonFaultMessage(Integer code, String description, Throwable cause) {
        this.code = code;
        this.description = description;
        if (cause != null) {
            this.cause = ExceptionUtils.getStackTrace(cause).replace("\r\n\t", " ").replace("\r\n", " ").replace("\n\t", " ");
        }
    }


}
