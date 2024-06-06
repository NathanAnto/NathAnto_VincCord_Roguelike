package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.RoomManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Mob(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _
  override var speed: Float = 1f
  override var diagoSpeed: Int = (speed * 2 * math.cos(math.Pi / 4)).toInt
  override var maxHp = 2
  override var hp = maxHp
  override var hitTimer: Float = 0f
  var velocity: Vector2 = new Vector2(1,0)
  var attackSpeed: Float = 2f

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  tag = "mob"

  override def toString: String = s"Mob [hp: $hp]"

  def move_fc(): Unit = {
    if(player.position.x <= position.x && player.position.y == position.y) {
      velocity.x = -speed
      /*if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }*/
    }
    if(player.position.x >= position.x && player.position.y == position.y){
      velocity.x = speed
      /*if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }*/
    }
    if(player.position.x == position.x && player.position.y >= position.y){
      velocity.y = speed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }
    if(player.position.x == position.x && player.position.y <= position.y){
      velocity.y = -speed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(player.position.x <= position.x && player.position.y >= position.y){
      velocity.x = -diagoSpeed
      velocity.y = diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(player.position.x >= position.x && player.position.y >= position.y){
      velocity.x = diagoSpeed
      velocity.y = diagoSpeed
      /*if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }*/
    }

    if(player.position.x <= position.x && player.position.y <= position.y){
      velocity.x = -diagoSpeed
      velocity.y = -diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

    if(player.position.x >= position.x && player.position.y <= position.y){
      velocity.x = diagoSpeed
      velocity.y = -diagoSpeed
      /*if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }*/
    }

  }
  override def draw(g: GdxGraphics): Unit = {
    if(hp <= 0) return

    if(getCollider(colliderRadius).overlaps(player.getCollider(player.colliderRadius))) {
      velocity.x = 0
      velocity.y = 0

      dealDamage()
    }

    position.x += velocity.x * speed
    position.y += velocity.y * speed

    // TODO: Draw sprites
    g.setColor(Color.BLUE)
    g.drawFilledCircle(position.x, position.y, colliderRadius, Color.RED)
    g.drawFilledRectangle(position.x, position.y, 48, 48, 0)
  }

  override def takeDamage(dmg: Float): Unit = {
    // Do Effect
    hp -= dmg
    if (hp <= 0) {
      RoomManager.mobDied(this)
    }
  }

  def dealDamage(): Unit = {
    hitTimer += Gdx.graphics.getDeltaTime

    if(hitTimer > attackSpeed) {
      // TODO: Attack animation
      println("Player hit")
      hitTimer = 0
      player.takeDamage(damage)
    }
  }
}
