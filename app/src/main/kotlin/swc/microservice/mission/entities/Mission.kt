package swc.microservice.mission.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

@Serializable
data class Mission<out T : Waste>(
    val missionId: String,
    var truckId: String? = null,
    @Serializable(with = DateSerializer::class)
    val date: LocalDate = LocalDate.now(),
    val typeOfWaste: TypeOfWaste<out T>,
    val typeOfMission: TypeOfMission,
    val missionSteps: List<MissionStep>
) {
    fun isCompleted(): Boolean = missionSteps.all { it.completed }
}

object DateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }
}
