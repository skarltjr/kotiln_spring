package com.example.demo.commons.errors

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.validation.Errors
import java.io.IOException



@JsonComponent
class ErrorSerializer : JsonSerializer<Errors>() {
    override fun serialize(errors: Errors?, gen: JsonGenerator?, p2: SerializerProvider?) {
        gen?.writeFieldName("errors")
        gen?.writeStartArray()
        errors?.fieldErrors?.forEach { it->
            try {
                gen?.writeStartObject()
                gen?.writeStringField("field", it.field)
                gen?.writeStringField("objectName", it.objectName)
                gen?.writeStringField("code", it.code)
                gen?.writeStringField("defaultMessage", it.defaultMessage)
                val rejectedValue: Any? = it.rejectedValue
                if (rejectedValue != null) {
                    gen?.writeStringField("rejectedValue", rejectedValue.toString())
                }

                gen?.writeEndObject()
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }

        errors?.globalErrors?.forEach { it ->
            try {
                gen?.writeStartObject()
                gen?.writeStringField("objectName", it.objectName);
                gen?.writeStringField("code", it.code);
                gen?.writeStringField("defaultMessage", it.defaultMessage);
                gen?.writeEndObject();
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }

        gen?.writeEndArray()
    }
}
