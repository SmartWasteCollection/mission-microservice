package swc.microservice.mission.drivers.database

import com.mongodb.ConnectionString
import com.mongodb.client.MongoCollection
import io.github.cdimascio.dotenv.dotenv
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollectionOfName
import org.litote.kmongo.setValue
import swc.microservice.mission.entities.Mission
import swc.microservice.mission.entities.Waste
import swc.microservice.mission.usecases.managers.MissionManager
import java.util.UUID

class MissionDatabaseManager : MissionManager {
    private companion object {
        val dotenv = dotenv {
            ignoreIfMissing = true
        }
        val CONNECTION_STRING: String = dotenv["MONGO_CONNECTION_STRING"]
        const val DATABASE: String = "smart-waste-collection"
        const val COLLECTION: String = "missions"
    }

    private val collection: MongoCollection<Mission<Waste>> =
        KMongo.createClient(ConnectionString(CONNECTION_STRING)).getDatabase(DATABASE).getCollectionOfName(
            COLLECTION
        )

    override fun completeMissionStep(missionId: String): Mission<Waste>? = this.getMissionById(missionId)?.let {
        it.missionSteps[it.missionSteps.indexOfFirst { s -> !s.completed }].completed = true
        this.collection.updateOne(
            Mission<Waste>::missionId eq missionId,
            setValue(Mission<Waste>::missionSteps, it.missionSteps)
        )
        return this.getMissionById(missionId)
    }

    override fun getMissions(): List<Mission<Waste>> = this.collection.find().toList()

    override fun getMissionById(missionId: String): Mission<Waste>? =
        this.collection.find(Mission<Waste>::missionId eq missionId).first()

    override fun assignTruckToMission(missionId: String, truckId: String): Mission<Waste>? {
        this.collection.updateOne(Mission<Waste>::missionId eq missionId, setValue(Mission<Waste>::truckId, truckId))
        return this.getMissionById(missionId)
    }

    override fun createMission(mission: Mission<Waste>): String {
        this.collection.insertOne(mission)
        return mission.missionId
    }

    override fun createNewMissionId(): String = "Mission-${UUID.randomUUID()}"
}
