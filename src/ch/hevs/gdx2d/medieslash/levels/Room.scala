package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.medieslash.levels.MapManager.{doorLayers, tiledLayer}
import ch.hevs.gdx2d.medieslash.objects.Mob
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val x: Int, val y: Int) {
  var doors: mutable.HashMap[String, Door] = new mutable.HashMap[String, Door]
  var neighbours: Set[Room] = Set()
  var mobs: ArrayBuffer[Mob] = ArrayBuffer()
  var map: TiledMap = _

  var width: Float = 0f
  var height: Float = 0f

  var isTraversable: Boolean = false
  var roomCleared: Boolean = false
  var isBossRoom: Boolean = false

  override def toString: String = s"Room($x,$y) [$isTraversable]"

  def setMap(map: TiledMap): Unit = {
    this.map = map
  }

  private def generateMobs(): Unit = {
    if(true) { // if !isBossRoom
      for (i <- 0 until 1) {
        mobs += new Mob(new Vector2(Random.between(100, width-100), Random.between(100, height-100)))
      }
    } else {
      // mobs += new Boss()
    }
  }

  def createDoors(n: Room, level: Level): Unit = {
    if(!n.isTraversable) return
//    println(s"Creating door from $this to $n")
    if (n == level.getRoom(x, y - 1)) // North room
      doors("N") = new Door(n)
    if (n == level.getRoom(x, y + 1)) // South room
      doors("S") = new Door(n)
    if (n == level.getRoom(x + 1, y)) // East room
      doors("E") = new Door(n)
    if (n == level.getRoom(x - 1, y)) // West room
      doors("W") = new Door(n)
  }

  def findNeighbours(level: Level): Unit = {
    val directions: Array[Room] = new Array(4)

    directions(0) = level.getRoom(x, y - 1) // TOP (North)
    directions(1) = level.getRoom(x, y + 1) // BOTTOM (South)
    directions(2) = level.getRoom(x + 1, y) // RIGHT (East)
    directions(3) = level.getRoom(x - 1, y) // LEFT (West)

    for (d <- directions) {
      if (d != null) {
        neighbours += d
      }
    }
  }

  def showTraversableDoors(level: Level): Unit = {
    for (n <- neighbours) {
      if (n.isTraversable) {
        if(n == level.getRoom(x, y - 1)) doorLayers("N").setVisible(true)
        if(n == level.getRoom(x, y + 1)) doorLayers("S").setVisible(true)
        if(n == level.getRoom(x + 1, y)) doorLayers("E").setVisible(true)
        if(n == level.getRoom(x - 1, y)) doorLayers("W").setVisible(true)
      }
    }

    if(!roomCleared) {
      generateMobs()
    }
  }
}
