package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.medieslash.levels.MapManager.{doorLayers, tiledLayer}
import ch.hevs.gdx2d.medieslash.objects.{GameObject, Mob}
import com.badlogic.gdx.math.Vector2

object RoomManager {
  def getDoors(): Unit = {
    val currentRoom = LevelManager.getCurrentLevel.currentRoom

    try {
      for (i <- 0 until tiledLayer.getWidth) {
        for (j <- 0 until tiledLayer.getHeight) {
          val tileN = doorLayers("N").getCell(i, j)
          val tileS = doorLayers("S").getCell(i, j)
          val tileE = doorLayers("E").getCell(i, j)
          val tileW = doorLayers("W").getCell(i, j)

          val pos = MapManager.tileToPosition(i, j)
          val OFFSET: Int = 150

          if (tileN != null) {
            val neighbour = LevelManager.getCurrentLevel.getRoom(currentRoom.x, currentRoom.y - 1)
            if (MapManager.isDoor(tileN.getTile) && neighbour != null) {
              if (neighbour.isTraversable) {
                val door = currentRoom.doors("N")
                door.position = pos
                door.nextPlayerPos = new Vector2(currentRoom.width/2, OFFSET)
              }
            }
          }
          if (tileS != null) {
            val neighbour = LevelManager.getCurrentLevel.getRoom(currentRoom.x, currentRoom.y + 1)
            if (MapManager.isDoor(tileS.getTile) && neighbour != null) {
              if (neighbour.isTraversable) {
                val door = currentRoom.doors("S")
                door.position = pos
                door.nextPlayerPos = new Vector2(currentRoom.width/2, currentRoom.height - OFFSET)
              }
            }
          }
          if (tileE != null) {
            val neighbour = LevelManager.getCurrentLevel.getRoom(currentRoom.x + 1, currentRoom.y)
            if (MapManager.isDoor(tileE.getTile) && neighbour != null) {
              if (neighbour.isTraversable) {
                val door = currentRoom.doors("E")
                door.position = pos
                door.nextPlayerPos = new Vector2(OFFSET, currentRoom.height/2)
              }
            }
          }
          if (tileW != null) {
            val neighbour = LevelManager.getCurrentLevel.getRoom(currentRoom.x - 1, currentRoom.y)
            if (MapManager.isDoor(tileW.getTile) && neighbour != null) {
              if (neighbour.isTraversable) {
                val door = currentRoom.doors("W")
                door.position = pos
                door.nextPlayerPos = new Vector2(currentRoom.width - OFFSET, currentRoom.height/2)
              }
            }
          }
        }
      }
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
  }

  def mobDied(mob: Mob): Unit = {
    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    currentRoom.mobs -= mob
    if(currentRoom.mobs.isEmpty) {
      println("Room cleared")
      currentRoom.roomCleared = true

      LevelManager.levelFinished()
    }
  }

  def removeRoomDoors(): Unit = {
    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    val trash_pos = new Vector2(0,0)
    // Remove doors from room before
    for((s, d) <- currentRoom.doors) {
      d.position = trash_pos
    }
  }

  def nextRoom(room: Room): Unit = {
    LevelManager.getCurrentLevel.currentRoom = room
    MapManager.setNewMap(room.map)
  }
}
