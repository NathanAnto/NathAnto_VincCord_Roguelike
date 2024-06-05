package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.medieslash.objects.MobManager
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object LevelManager {

  var levels: ArrayBuffer[Level] = ArrayBuffer()
  private var currentLevelIndex: Int = 0
  private val MIN_ROOM_COUNT: Int = 4

  // TODO: Make usable maps
  private var possibleRooms: Array[TiledMap] = Array(
    new TmxMapLoader().load("src/ch/hevs/gdx2d/medieslash/maps/room1.tmx")
  )

  def getCurrentLevel: Level = levels(currentLevelIndex)

  def startLevel(): Unit = {
    MapManager.setNewMap(getCurrentLevel.currentRoom.map)
    println(s"Starting Level ${currentLevelIndex + 1}")
    println(s"Rooms: ${getCurrentLevel.roomCount}")
  }

  /**
   * Generates n levels
   *
   * @param w width of a level
   * @param h height of a level
   * @param n number of levels
   * @param m max number of rooms in a level
   */
  def generateLevels(w: Int, h: Int, n: Int, m: Int): Unit = {
    for (i <- 0 until n) {
      val level = new Level(i + 1, w, h)
      level.createRooms()
      level.roomCount = clamp(MIN_ROOM_COUNT + i, 0, m)

      val startRoom = level.getRoom(level.width / 2, level.height / 2)
      startRoom.isTraversable = true
      startRoom.setMap(
        possibleRooms(Random.between(0, possibleRooms.length))
      )

      level.currentRoom = startRoom

      primsFrontier(
        startRoom,
        visited = mutable.Set(startRoom),
        level, level.roomCount
      )

      for (r <- level.getRooms) {
        for(n <- r.neighbours) {
          r.createDoors(n, level)
        }
      }

      levels.addOne(level)
    }
  }

  private def primsFrontier(location: Room, visited: mutable.Set[Room], level: Level, roomCount: Int): Unit = {
    visited += location

    // Get all neighbours that haven't been visited
    for (l <- location.neighbours) {
      if (!visited.contains(l)) {
        level.frontiers += l
      }
    }

    val nextRoom: Room = level.getRandomFrontier
    if (nextRoom == null) return

    visited += nextRoom

    if (visited.size <= roomCount) {
      nextRoom.isTraversable = true

      nextRoom.setMap(
        possibleRooms(Random.between(0, possibleRooms.length))
      )

      // Set last generated room to boss room
      if (visited.size == roomCount) {
        nextRoom.isBossRoom = true
      }
    }

    // Find a neighbour that has already been visited
    for (n <- nextRoom.neighbours) {

      if (visited.contains(n)) {
        primsFrontier(nextRoom, visited, level, roomCount)
        return
      }
    }
  }

  def levelFinished(): Unit = {
    if (getCurrentLevel.getRooms.count(r => r.roomCleared) == LevelManager.getCurrentLevel.roomCount) {
      currentLevelIndex += 1
      if(currentLevelIndex == levels.length) {
        println("GAME FINISHED")
        // TODO: End game
        return
      }
      MobManager.upgradeMobs()
      startLevel()
    } else {
      for((s, d) <- getCurrentLevel.currentRoom.doors) {
        d.active = true
      }
    }

    //    if(levels(currentLevelIndex).bossDefeated) {
    //      currentLevelIndex += 1
    //    }
  }

  private def clamp(value: Int, min: Int, max: Int): Int = Math.max(min, Math.min(max, value))
}
