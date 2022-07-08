package swc.microservice.mission.usecases.managers

import swc.microservice.mission.entities.CollectionPoint
import swc.microservice.mission.entities.Dumpster

interface DumpsterManager {
    fun getDumpsters(): List<Dumpster>

    fun getCollectionPoints(): List<CollectionPoint>
}
