package ch.hevs.gdx2d.medieslash.levels

import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object LevelManager {

  var levels: ArrayBuffer[Level] = ArrayBuffer()
  private var currentLevelIndex: Int = 0
  private val MIN_ROOM_COUNT: Int = 4

  // TODO: Make usable maps
  var possibleRooms: Array[TiledMap] = Array(
//    new TmxMapLoader().load("src/ch/hevs/gdx2d/medieslash/maps/desert.tmx"),
    new TmxMapLoader().load("src/ch/hevs/gdx2d/medieslash/maps/template.tmx"),
  )

  def getCurrentLevel: Level = levels(currentLevelIndex)

  /**
   * Generates n levels
   * @param w width of a level
   * @param h height of a level
   * @param n number of levels
   * @param m max number of rooms in a level
   */
  def generateLevels(w: Int, h: Int, n: Int, m: Int): Unit = {
    for(i <- 0 until n) {
      val level = new Level(i+1, w, h)
      level.createRooms()

      val roomCount: Int = clamp(MIN_ROOM_COUNT + i, 0, m)
//      println(s"level ${level.id} has $roomCount rooms")

      val startRoom = level.getRoom(level.width / 2, level.height / 2)
      startRoom.isTraversable = true
      startRoom.setMap(
        possibleRooms(Random.between(0, possibleRooms.size))
      )

      level.currentRoom = startRoom

//      println(s"New room in pos ${startRoom.x},${startRoom.y}")
      primsFrontier(
        level.getRoom(startRoom.x, startRoom.y),
        visited = mutable.Set(),
        level, roomCount
      )

      levels.addOne(level)
    }
  }

  private def primsFrontier(location: Room, visited: mutable.Set[Room], level: Level, roomCount: Int): Unit = {
    visited += location

    // Get all neighbours that haven't been visited
    for(l <- location.neighbours){
      if(!visited.contains(l)) {
        level.frontiers += l
      }
    }
    val nextRoom: Room = level.getRandomFrontier
    if(nextRoom == null || visited.size >= roomCount) return

//    println(s"New room in pos ${nextRoom.x},${nextRoom.y}")
    visited += nextRoom
    nextRoom.setMap(
      possibleRooms(Random.between(0, possibleRooms.size))
    )

    // Set last generated room to boss room
    if(visited.size >= roomCount) {
      nextRoom.isBossRoom = true
    }

//    println(s"New room in pos ${nextRoom.x},${nextRoom.y}")

    // Find a neighbour that has already been visited
    for(n <- nextRoom.neighbours) {
      if(visited.contains(n)) {
        primsFrontier(nextRoom, visited, level, roomCount)
        return
      }
    }
  }

  def levelFinished(): Unit = {
    if(levels(currentLevelIndex).bossDefeated) {
      currentLevelIndex += 1
    }
  }

  private def clamp(value: Int, min: Int, max: Int): Int = Math.max(min, Math.min(max, value))
}
