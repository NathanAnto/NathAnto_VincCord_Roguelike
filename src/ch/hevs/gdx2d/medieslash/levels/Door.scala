package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.objects.{GameObject, Object, Player}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

class Door(var room: Room) extends Object {
  override var position: Vector2 = new Vector2()
  override var collider: Circle = new Circle(position.x, position.y, 25)
  var active: Boolean = false

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]
  var nextPlayerPos: Vector2 = _

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if (collider.overlaps(player.collider) && active) {
      RoomManager.removeRoomDoors()
      RoomManager.nextRoom(this.room)
      player.position = nextPlayerPos
    }

//    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN) // Draw collider
  }
}