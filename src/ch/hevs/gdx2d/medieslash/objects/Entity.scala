package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.medieslash.effects.Animation
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

abstract class Entity extends GameObject with DrawableObject {
  var sprites: Spritesheet
  var animations: mutable.HashMap[String, Animation]
  var currentAnimation: Animation
  var speed: Float
  var diagoSpeed: Int
  var hitTimer: Float
  var colliderRadius: Int = 30

  protected var maxHp: Float
  protected var hp: Float

  var damage: Float = 1f

//  override def toString: String =

  override def draw(g: GdxGraphics): Unit = { }

  def takeDamage(dmg: Float): Unit = {
    // Do Effect
    hp -= dmg
    if (hp <= 0) {
      GameObject.destroyInstance(this)
    }
  }
}
