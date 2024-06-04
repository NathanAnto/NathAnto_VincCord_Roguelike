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
  override var xSpeed: Int = 4
  override var ySpeed: Int = 4
  override var diagoSpeed: Int = (xSpeed * 1.2 * math.cos(math.Pi / 4)).toInt

  override var maxHp: Int = 10
  override var hp: Int = maxHp
  var tolerance: Double = 0.5





  def move_fc(left: Boolean, right: Boolean, up: Boolean, down: Boolean): Unit = {
    if(left && !right && !up && !down){
      position.x -= xSpeed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }
    if(right && !left && !up && !down){
      position.x += xSpeed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }
    if(up && !right && !left && !down){
      position.y += ySpeed
      if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }
    }
    if(down && !right && !up && !left){
      position.y -= ySpeed
      if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }
    }

    if(up && left){
      position.x -= diagoSpeed
      position.y += diagoSpeed
      if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }
    }

    if(up && right){
      position.x += diagoSpeed
      position.y += diagoSpeed
      if (currentAnimation != animations("up")) {
        currentAnimation = animations("up")
      }
    }

    if(down && left){
      position.x -= diagoSpeed
      position.y -= diagoSpeed
      if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }
    }

    if(down && right){
      position.x += diagoSpeed
      position.y -= diagoSpeed
      if (currentAnimation != animations("down")) {
        currentAnimation = animations("down")
      }
    }

  }

  def move_controller(x: Double,y: Double): Unit = {

    if (y > tolerance) {
      if (x > tolerance) {
        position.x += diagoSpeed
        position.y -= diagoSpeed
        if (currentAnimation != animations("down")) {
          currentAnimation = animations("down")
        }
      } else if (x < -tolerance) {
        position.x -= diagoSpeed
        position.y -= diagoSpeed
        if (currentAnimation != animations("down")) {
          currentAnimation = animations("down")
        }
      } else {
        position.y -= ySpeed
        if (currentAnimation != animations("down")) {
          currentAnimation = animations("down")
        }
      }
    } else {
      if (y < -tolerance) {
        if (x > tolerance) {
          position.x += diagoSpeed
          position.y += diagoSpeed
          if (currentAnimation != animations("up")) {
            currentAnimation = animations("up")
          }
        } else if (x < -tolerance) {
          position.x -= diagoSpeed
          position.y += diagoSpeed
          if (currentAnimation != animations("up")) {
            currentAnimation = animations("up")
          }
        } else {
          position.y += ySpeed
          if (currentAnimation != animations("up")) {
            currentAnimation = animations("up")
          }
        }
      } else {
        if (x > tolerance) {
          position.x += xSpeed
          if (currentAnimation != animations("right")) {
            currentAnimation = animations("right")
          }
        } else if (x < -tolerance) {
          position.x -= xSpeed
          if (currentAnimation != animations("left")) {
            currentAnimation = animations("left")
          }
        }
      }
    }


  }


  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    g.setColor(Color.BLUE)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)


    g.draw(
      currentAnimation.playAnimation(),
      position.x - currentAnimation.SPRITE_WIDTH / 2,
      position.y - currentAnimation.SPRITE_HEIGHT / 2
    )
    //    g.drawFilledRectangle(position.x, position.y, 64, 64, 0)
  }

  //  override def takeDamage(dmg: Int): Unit
}
