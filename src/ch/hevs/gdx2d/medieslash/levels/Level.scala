package ch.hevs.gdx2d.medieslash.levels

class Level(val id: Int, val width: Int, val height: Int) {
  protected var rooms: Set[Room] = Set()

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

  private def isInDimensions(x:Int, y:Int): Boolean =
    x < width && x >= 0 &&
      y < height && y >= 0
}
