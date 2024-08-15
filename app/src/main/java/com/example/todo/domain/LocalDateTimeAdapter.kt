
import com.example.todo.domain.LocalDateTimeConverter
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {


    override fun write(out: JsonWriter?, value: LocalDateTime?) {
        value?.let { out!!.value(LocalDateTimeConverter.fromLocalDateTime(it)) } ?: out!!.nullValue()
    }

    override fun read(`in`: JsonReader?): LocalDateTime? {
        if (`in`?.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        return LocalDateTimeConverter.toLocalDateTime(`in`?.nextString())
    }
}