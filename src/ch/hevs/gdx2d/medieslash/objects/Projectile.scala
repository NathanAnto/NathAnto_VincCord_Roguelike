package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

class Projectile(startPos: Vector2) extends Object with DrawableObject {

  override var position: Vector2 = startPos
  override var collider: Circle = new Circle(startPos.x, startPos.y, 10)

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    // TODO: Draw sprites
    g.setColor(Color.RED)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 64, 64, 0)
  }
}
