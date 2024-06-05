package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.RoomManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Mob(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var collider: Circle = new Circle(p.x, p.y, 35)
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _
  override var xSpeed: Int = 1
  override var ySpeed: Int = 1
  override var diagoSpeed: Int = (xSpeed * 2 * math.cos(math.Pi / 4)).toInt
  override var maxHp = 1
  override var hp = maxHp

  // type de mob
  var mob_type: String = "zombie"

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  tag = "mob"

  def move_fc(): Unit = {
    if(player.position.x <= position.x && player.position.y == position.y) {
      position.x -= xSpeed
      /*if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }*/
    }
    if(player.position.x >= position.x && player.position.y == position.y){
      position.x += xSpeed
      /*if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }*/
    }
    if(player.position.x == position.x && player.position.y >= position.y){
      position.y += ySpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }
    if(player.position.x == position.x && player.position.y <= position.y){
      position.y -= ySpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(player.position.x <= position.x && player.position.y >= position.y){
      position.x -= diagoSpeed
      position.y += diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(player.position.x >= position.x && player.position.y >= position.y){
      position.x += diagoSpeed
      position.y += diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(player.position.x <= position.x && player.position.y <= position.y){
      position.x -= diagoSpeed
      position.y -= diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(player.position.x >= position.x && player.position.y <= position.y){
      position.x += diagoSpeed
      position.y -= diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

  }
  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if(hp <= 0) return

    // TODO: Draw sprites
    g.setColor(Color.BLUE)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.RED)
    g.drawFilledRectangle(position.x, position.y, 48, 48, 0)
  }

  override def takeDamage(dmg: Int): Unit = {
    hp -= dmg
    if (hp <= 0) {
      RoomManager.mobDied(this)
      GameObject.destroyInstance(this)
    }
  }
}
