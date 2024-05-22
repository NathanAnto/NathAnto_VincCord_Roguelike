package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.math.{Circle, Vector2}

abstract class Object extends GameObject with DrawableObject {
  override var position: Vector2
  override var collider: Circle

  override def draw(g: GdxGraphics): Unit = {
    collider.x = position.x
    collider.y = position.y
  }
}
