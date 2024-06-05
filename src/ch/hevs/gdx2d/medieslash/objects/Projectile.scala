package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable.ArrayBuffer
import scala.math._

class Projectile(startPos: Vector2) extends Object with DrawableObject {
  override var position: Vector2 = startPos
  var speed: Int = 5
  var speedMob: Int = 2
  var angleTorwardsPlayer: Double = 45
  var diagoSpeed: Int = (speed * 1.2 * math.cos(math.Pi / 4)).toInt
  var colliderRadius = 10
  var velocity: Vector2 = new Vector2(1,0)
  var nextVelocity: Vector2 = new Vector2(1,0)
  var targetsHit: ArrayBuffer[Entity] = ArrayBuffer()

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  // fonction pour partir en fonction du player
  def move_projectil(left: Boolean, right: Boolean, up: Boolean, down: Boolean): Unit = {
    if(left && !right && !up && !down){
      nextVelocity.x = -speed
    }
    if(right && !left && !up && !down){
      nextVelocity.x = speed
    }
    if(up && !right && !left && !down){
      nextVelocity.y = speed
    }
    if(down && !right && !up && !left){
      nextVelocity.y = -speed
    }

    if(up && left){
      nextVelocity.x = -diagoSpeed
      nextVelocity.y = diagoSpeed
    }

    if(up && right){
      nextVelocity.x = diagoSpeed
      nextVelocity.y = diagoSpeed
    }

    if(down && left){
      nextVelocity.x = -diagoSpeed
      nextVelocity.y = -diagoSpeed
    }

    if(down && right){
      nextVelocity.x = diagoSpeed
      nextVelocity.y = -diagoSpeed
    }

    nextVelocity.nor()
  }

  def changeAngleAndSpeed(): Unit = {
    angleTorwardsPlayer = toDegrees(atan2(player.position.y - position.y, player.position.x - position.x))
    velocity.x = (cos(toRadians(angleTorwardsPlayer)) * speedMob).toInt
    velocity.y = (sin(toRadians(angleTorwardsPlayer)) * speedMob).toInt
  }

  def move_proj_towards_player(): Unit = {
    position.x += speed
    position.y += speed
  }

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    position.x += velocity.x * speed
    position.y += velocity.y * speed

    // TODO: Draw sprites
    g.setColor(Color.RED)
    g.drawFilledCircle(position.x, position.y, colliderRadius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 20, 20, 0)
  }
}
