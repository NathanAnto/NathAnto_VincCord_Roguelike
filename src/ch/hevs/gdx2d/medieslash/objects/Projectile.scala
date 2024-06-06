package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer
import scala.math._

class Projectile(startPos: Vector2) extends Object with DrawableObject {
  override var position: Vector2 = startPos
  var speed: Int = 5
  var projSpeed: Int = 10
  var diagoSpeed: Int = (speed * 1.2 * math.cos(math.Pi / 4)).toInt
  var colliderRadius = 10
  var velocity: Vector2 = new Vector2(1,0)
  var nextVelocity: Vector2 = new Vector2(1,0)
  var targetsHit: ArrayBuffer[Entity] = ArrayBuffer()

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  var xSpeed: Int = 0
  var ySpeed: Int = 0

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

  private var angleTorwardsPlayer = 0.0

  def getAngleToTarget(target: Entity): Unit = {
    angleTorwardsPlayer = toDegrees(atan2(target.position.y - position.y, target.position.x - position.x))
    xSpeed = (cos(toRadians(angleTorwardsPlayer)) * projSpeed).toInt
    ySpeed = (sin(toRadians(angleTorwardsPlayer)) * projSpeed).toInt
    println(xSpeed, ySpeed)
  }

  override def draw(g: GdxGraphics): Unit = {
    position.x += velocity.x * speed
    position.y += velocity.y * speed

    // TODO: Draw sprites
    g.setColor(Color.RED)
    g.drawFilledCircle(position.x, position.y, colliderRadius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 20, 20, 0)
  }
}
