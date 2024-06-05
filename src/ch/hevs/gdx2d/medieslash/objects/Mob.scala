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
  tag = "mob"
  override var maxHp = 1
  override var hp = maxHp
  // type de mod
  var mob_type: String = "zombie"


  tag = "mob"

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]
  def move_fc(posX_player: Float,posY_player: Float,posX_mob: Float,posY_mob: Float): Unit = {

    if(posX_player <= posX_mob && posY_player == posY_mob){
      position.x -= xSpeed
      /*if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }*/
    }
    if(posX_player >= posX_mob && posY_player == posY_mob){
      position.x += xSpeed
      /*if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }*/
    }
    if(posY_player >= posY_mob && posX_player == posX_mob){
      position.y += ySpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }
    if(posY_player <= posY_mob && posX_player == posX_mob){
      position.y -= ySpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(posX_player <= posX_mob && posY_player >= posY_mob){
      position.x -= diagoSpeed
      position.y += diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(posX_player >= posX_mob && posY_player >= posY_mob){
      position.x += diagoSpeed
      position.y += diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(posX_player <= posX_mob && posY_player <= posY_mob){
      position.x -= diagoSpeed
      position.y -= diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(posX_player >= posX_mob && posY_player <= posY_mob){
      position.x += diagoSpeed
      position.y -= diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

  }
  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    // TODO: Draw sprites
    g.setColor(Color.BLUE)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.RED)
    g.drawFilledRectangle(position.x, position.y, 48, 48, 0)

    if(collider.overlaps(player.collider)) {
//      println("colliding with player")
      RoomManager.mobDied(this)
      GameObject.destroyInstance(this)
    }
  }

  override def takeDamage(dmg: Int): Unit = {
    hp -= dmg
    if (hp <= 0) {
      println("DEAD")
      GameObject.destroyInstance(this)
    }
  }
}
