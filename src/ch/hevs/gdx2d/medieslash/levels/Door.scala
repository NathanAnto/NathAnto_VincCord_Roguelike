package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.objects.{GameObject, Object, Player}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

class Door(var room: Room) extends Object {
  override var position: Vector2 = new Vector2()
  var active: Boolean = false

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]
  var nextPlayerPos: Vector2 = _
  private var colliderRadius = 20

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if (getCollider(colliderRadius).overlaps(player.getCollider(player.colliderRadius)) && active) {
      println(s"collided door $this")
      RoomManager.removeRoomDoors()
      player.position = nextPlayerPos
      RoomManager.nextRoom(this.room)
    }
  }
}