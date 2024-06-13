package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.{LevelManager, MapManager, RoomManager}
import ch.hevs.gdx2d.medieslash.upgrades.{PlayerLevel, XPManager}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Player(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _
  override var speed: Float = 2.5f
  override var diagoSpeed: Int = (speed * 1.2 * math.cos(math.Pi / 4)).toInt
  override var hitTimer: Float = 1f

  override var maxHp: Float = 20f
  override var hp: Float = maxHp

  var level: PlayerLevel = new PlayerLevel(10, XPManager.getRandomUpgrade())
  var xp: Float = 0f;

  var previousAnimation: Animation = currentAnimation

  var velocity: Vector2 = new Vector2(0,0)
  var attackSpeed: Float = 1f
  var tolerance: Double = 0.5

  tag = "player"
  damage = 1f

  def move_fc(left: Boolean, right: Boolean, up: Boolean, down: Boolean): Unit = {
    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    if(position.x < colliderRadius) position.x += speed
    if(position.x > currentRoom.width - colliderRadius) position.x -= speed
    if(position.y > currentRoom.height - colliderRadius) position.y -= speed
    if(position.y < colliderRadius) position.y += speed

    if(left && !right && !up && !down){
      position.x -= speed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }
    if(right && !left && !up && !down){
      position.x += speed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }
    if(up && !right && !left && !down){
      position.y += speed
    }
    if(down && !right && !up && !left){
      position.y -= speed
    }

    if(up && left){
      position.x -= diagoSpeed
      position.y += diagoSpeed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }

    if(up && right){
      position.x += diagoSpeed
      position.y += diagoSpeed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }

    if(down && left){
      position.x -= diagoSpeed
      position.y -= diagoSpeed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }

    if(down && right){
      position.x += diagoSpeed
      position.y -= diagoSpeed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }

    if(currentAnimation != animations("hit")) {
      previousAnimation = currentAnimation
    }
  }


  def move_controller(x: Double,y: Double): Unit = {
    if (y > tolerance) {
      if (x > tolerance) {
        position.x += diagoSpeed
        position.y -= diagoSpeed
        if (currentAnimation != animations("right")) {
          currentAnimation = animations("right")
        }
      } else if (x < -tolerance) {
        position.x -= diagoSpeed
        position.y -= diagoSpeed
        if (currentAnimation != animations("left")) {
          currentAnimation = animations("left")
        }
      } else {
        position.y -= speed
      }
    } else {
      if (y < -tolerance) {
        if (x > tolerance) {
          position.x += diagoSpeed
          position.y += diagoSpeed
          if (currentAnimation != animations("right")) {
            currentAnimation = animations("right")
          }
        } else if (x < -tolerance) {
          position.x -= diagoSpeed
          position.y += diagoSpeed
          if (currentAnimation != animations("left")) {
            currentAnimation = animations("left")
          }
        } else {
          position.y += speed
        }
      } else {
        if (x > tolerance) {
          position.x += speed
          if (currentAnimation != animations("right")) {
            currentAnimation = animations("right")
          }
        } else if (x < -tolerance) {
          position.x -= speed
          if (currentAnimation != animations("left")) {
            currentAnimation = animations("left")
          }
        }
      }
    }

    if(currentAnimation != animations("hit")) {
      previousAnimation = currentAnimation
    }
  }

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if(currentAnimation == animations("hit")) {
      currentAnimation = previousAnimation
    }

    g.setColor(Color.BLUE)

    g.draw(
      currentAnimation.playAnimation(),
      (position.x) - currentAnimation.SPRITE_WIDTH / 6,
      (position.y) - currentAnimation.SPRITE_HEIGHT / 6
    )
  }
}
