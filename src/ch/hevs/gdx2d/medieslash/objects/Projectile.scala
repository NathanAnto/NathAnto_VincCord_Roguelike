package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}
import scala.math._

class Projectile(startPos: Vector2) extends Object with DrawableObject {

  override var position: Vector2 = startPos
  override var collider: Circle = new Circle(startPos.x, startPos.y, 30)
  var xSpeed: Int = 7
  var ySpeed: Int = 7
  var xSpeedMob: Int = 13
  var ySpeedMob: Int = 13
  var angleTorwardsPlayer: Double = 45
  var diagoSpeed: Int = (xSpeed * 1.2 * math.cos(math.Pi / 4)).toInt
  // hache
  var hacheRadians = math.toRadians(180)
  var hache_cosTheta = math.cos(hacheRadians)
  var hache_sinTheta = math.sin(hacheRadians)
  var hache_translatedX = 0f
  var hache_translatedY = 0f
  var hache_rotatedX: Double = 0
  var hache_rotatedY: Double = 0

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  // fonction pour partir en fonction du player
  def move_projectil(left: Boolean, right: Boolean, up: Boolean, down: Boolean): Unit = {
    if(left && !right && !up && !down){
      position.x -= xSpeed
    }
    if(right && !left && !up && !down){
      position.x += xSpeed
    }
    if(up && !right && !left && !down){
      position.y += ySpeed
    }
    if(down && !right && !up && !left){
      position.y -= ySpeed
    }

    if(up && left){
      position.x -= diagoSpeed
      position.y += diagoSpeed
    }

    if(up && right){
      position.x += diagoSpeed
      position.y += diagoSpeed
    }

    if(down && left){
      position.x -= diagoSpeed
      position.y -= diagoSpeed
    }

    if(down && right){
      position.x += diagoSpeed
      position.y -= diagoSpeed
    }
    if(!left && !right && !down && !up){
      position.x += xSpeed
    }
  }

  def changeAngleAndSpeed(posX_player: Float,posY_player: Float): Unit = {
    angleTorwardsPlayer = toDegrees(atan2(posY_player - position.y,posX_player - position.x))
    xSpeed = (cos(toRadians(angleTorwardsPlayer)) * xSpeedMob).toInt
    ySpeed = (sin(toRadians(angleTorwardsPlayer)) * ySpeedMob).toInt
  }
  def move_proj_towards_player(): Unit = {
    position.x += xSpeed
    position.y += ySpeed

  }

  def initPosxy(): Unit = {
    position.x = player.position.x + 85
    position.y = player.position.y
  }
  def hache_player(): Unit = {
    hache_translatedX = position.x - player.position.x
    hache_translatedY = position.y - player.position.y

    hache_rotatedX = hache_translatedX * hache_cosTheta - hache_translatedY * hache_sinTheta
    hache_rotatedY = hache_translatedX * hache_sinTheta + hache_translatedY * hache_cosTheta
  }

  def hache_changePos(): Unit = {
    position.x = (hache_rotatedX + player.position.x).toInt
    position.y = (hache_rotatedY + player.position.y).toInt
    println(position.x)
    println(position.y)
  }

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    // TODO: Draw sprites
    g.setColor(Color.RED)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 32, 32, 0)
  }
}
