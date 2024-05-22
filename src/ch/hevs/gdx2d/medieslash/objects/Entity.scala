package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.medieslash.effects.Animation

import scala.collection.mutable

abstract class Entity extends GameObject with DrawableObject {
  var sprites: Spritesheet
  var animations: mutable.HashMap[String, Animation]
  var currentAnimation: Animation

  protected var maxHp: Int
  protected var hp: Int

  var damage: Int = 1

  override def draw(g: GdxGraphics): Unit = {
    collider.x = position.x
    collider.y = position.y
  }

  def takeDamage(dmg: Int): Unit = {
    // Do Effect
    hp -= dmg
    if (hp <= 0) {
      println("DEAD")
      GameObject.destroyInstance(this)
    }
  }
}
