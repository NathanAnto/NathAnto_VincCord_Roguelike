package ch.hevs.gdx2d.medieslash.levels

import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}

import scala.util.Random

class Level(val id: Int, val width: Int, val height: Int) {
  private var rooms: Set[Room] = Set()
  var currentRoom: Room = _

  var frontiers: Set[Room] = Set()
  var roomCount: Int = 0
  var bossDefeated: Boolean = false

  def createRooms(): Unit = {
    // Get all cells
    for (y <- 0 until height; x <- 0 until width) {
      val r = new Room(x, y)
      rooms += r
    }
    for(r <- rooms) r.findNeighbours(this)
  }

  def getRooms = rooms

  def getTraversableRooms = rooms.filter(r => r.isTraversable)

  def getRoom(x: Int, y: Int): Room = {
    if (isInDimensions(x, y)) {
      return rooms.find(r => r.x == x && r.y == y).get
    }
    null
  }

  /**
   * Used to get a frontier of the visited
   * cells, then removes it from the [[Set]]
   * @return a random frontier and removes it
   */
  def getRandomFrontier: Room = {
    if(frontiers.size <= 0) return null

    val num = Random.nextInt(frontiers.size)
    var f = frontiers.toIndexedSeq(num)
    frontiers -= f
    f
  }

  private def isInDimensions(x:Int, y:Int): Boolean =
    x < width && x >= 0 && y < height && y >= 0
}
