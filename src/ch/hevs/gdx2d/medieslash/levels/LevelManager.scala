package ch.hevs.gdx2d.medieslash.levels

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object LevelManager {

  var levels: ArrayBuffer[Level] = ArrayBuffer()

  /**
   * Generate n levels
   * @param n number of levels
   * @param m max number of rooms in a level
   * @return n levels generated
   */
  def generateLevels(x: Int,y: Int, n: Int, m: Int): Unit = {
    for(i <- 1 to n) {
      val level = new Level(i, x, y)
      level.createRooms()

      var roomCount: Int = clamp(2 + i, 0, m)
      println(s"level ${level.id} has $roomCount rooms")

      val newRoom = level.getRoom(level.width / 2, level.height / 2)
      newRoom.isTraversable = true

      println(s"New room in pos ${newRoom.x},${newRoom.y}")
      backtracker(
        level.getRoom(newRoom.x, newRoom.y),
        visited = mutable.Set(),
        level, roomCount
      )

      levels.addOne(level)
    }
  }

  private def backtracker(location: Room, visited: mutable.Set[Room], level: Level, roomCount: Int): Unit = {
    visited += location

    for(pos <- location.neighbours) {
      if(!visited.contains(pos) && pos != null && level.getTraversableRooms.size < roomCount) {
        println(s"New room in pos ${pos.x},${pos.y}")
        val newRoom = level.getRoom(pos.x, pos.y)
        newRoom.isTraversable = true
        backtracker(pos, visited, level, roomCount)
      }
    }
  }

  private def clamp(value: Int, min: Int, max: Int): Int = Math.max(min, Math.min(max, value))
}
