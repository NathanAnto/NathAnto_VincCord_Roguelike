package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.objects.{GameObject, Object, Player}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

class Door(var room: Room) extends Object {
  override var position: Vector2 = new Vector2()
  override var collider: Circle = new Circle(position.x, position.y, 25)
  var active: Boolean = true

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if (collider.overlaps(player.collider) && active) {
      RoomManager.removeRoomDoors()
      RoomManager.nextRoom(this.room)
      player.position.x = 800
      player.position.y = 640
      println(s"player entering door $this")
    }

    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)
  }
}