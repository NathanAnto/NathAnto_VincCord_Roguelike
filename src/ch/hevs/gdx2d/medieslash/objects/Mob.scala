package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Mob(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var collider: Circle = new Circle(p.x, p.y, 50)
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _

  override var maxHp = 20
  override var hp = maxHp

  tag = "mob"

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    // TODO: Draw sprites
    g.setColor(Color.RED)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 64, 64, 0)

    if(collider.overlaps(player.collider)) {
      println("colliing with player")
    }
  }
}
