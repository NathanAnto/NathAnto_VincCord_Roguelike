package ch.hevs.gdx2d.medieslash.levels

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val x: Int, val y: Int) {
  var neighbours: Set[Room] = Set()

  var isTraversable: Boolean = false

  override def toString: String = s"Cell($x,$y)"

  def findNeighbours(level: Level): Unit = {
    // Random neighbour order
    val directions: Array[Room] = new Array(4)
    val indices = generateSequence(directions.length)

//    println(s"indices: ${indices.mkString(",")}")

    directions(indices(0)) = level.getRoom(x, y - 1) // TOP
    directions(indices(1)) = level.getRoom(x - 1, y) // LEFT
    directions(indices(2)) = level.getRoom(x, y + 1) // BOTTOM
    directions(indices(3)) = level.getRoom(x + 1, y) // RIGHT
    for(d <- directions) {
      if(d != null) neighbours += d
    }
  }

  /**
   * Generates list of numbers from 0 to 'length'
   * in random order
   * @param length size of sequence as an [[Int]]
   * @return [[Array]] of [[Int]]s
   */
  private def generateSequence(length: Int): Array[Int] = {
    val res: Array[Int] = Array.fill(length)(-1)
    var num = 0
    for((i,index) <- res.zipWithIndex) {
      do {
        num = Random.nextInt(length);
      } while (res.contains(num))
      res(index) = num;
    }
    res
  }
}
