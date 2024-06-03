package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.{LevelManager, MapManager, RoomManager}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Player(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var collider: Circle = new Circle(p.x, p.y, 30)
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _

  override var maxHp: Int = 20
  override var hp: Int = maxHp

  var speed = new Vector2(0, 0)

  tag = "player"

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    g.setColor(Color.BLUE)
    g.drawFilledCircle(collider.x, collider.y, collider.radius, Color.GREEN) // Draw collider

    position.x += speed.x
    position.y += speed.y

    g.draw(
      currentAnimation.playAnimation(),
      (position.x) - currentAnimation.SPRITE_WIDTH / 2,
      (position.y) - currentAnimation.SPRITE_HEIGHT / 2
    )

//    if (!MapManager.isWalkable(MapManager.getTile(position, 0, 0))) {
//      speed.x = 0
//      speed.y = 0
//    }

    if (MapManager.isDoor(MapManager.getTile(position, 0, 0))) {
      println("Walking into door")
//      if(RoomManager.roomCleared()) {
//        RoomManager.getNextRoom
//      }
    }
  }

  //  override def takeDamage(dmg: Int): Unit
}
