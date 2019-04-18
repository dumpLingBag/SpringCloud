package com.rngay.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.IOException;
import java.sql.Timestamp;

public class TimestampSerializer extends JsonSerializer<Timestamp> {
    private static FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(timestamp.getTime()));
    }
}
