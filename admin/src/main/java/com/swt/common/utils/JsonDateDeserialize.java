package com.swt.common.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeserialize extends JsonDeserializer<Date> {
    private String[] patterns = {"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss","yyyy-MM-dd"};

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateAsString = jp.getText();
        Date parseDate = null;
        try {
            parseDate = DateUtils.parseDate(dateAsString, patterns);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getCause());
        }

        return parseDate;
    }
}