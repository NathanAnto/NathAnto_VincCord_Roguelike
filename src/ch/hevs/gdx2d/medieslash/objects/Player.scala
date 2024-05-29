package ch.hevs.gdx2d.medieslash.objects
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Player(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var collider: Circle = new Circle(p.x, p.y, 50)
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _

  override var maxHp: Int = 20
  override var hp: Int = maxHp

  tag = "player"

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    g.setColor(Color.BLUE)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)

    g.draw(
      currentAnimation.playAnimation(),
      (position.x) - currentAnimation.SPRITE_WIDTH / 2,
      (position.y) - currentAnimation.SPRITE_HEIGHT / 2
    )
    //      g.drawFilledRectangle(position.x, position.y, 64, 64, 0)
  }

  //  override def takeDamage(dmg: Int): Unit
}
