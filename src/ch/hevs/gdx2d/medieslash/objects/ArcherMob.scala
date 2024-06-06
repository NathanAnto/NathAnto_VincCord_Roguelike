package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.math.{atan2, toDegrees}

class ArcherMob(pos: Vector2) extends Mob(pos) {
  var projectile: MobProjectile = new MobProjectile(this, pos)
  var projectileRespawn: Float = 5f
  maxHp = 1f
  hp = maxHp

  override def toString: String = s"Archer ${super.toString}"

  override def move_fc(): Unit = {}

  override def draw(g: GdxGraphics): Unit = {
    if(hp <= 0) return

    hitTimer += Gdx.graphics.getDeltaTime

    if(hitTimer >= projectileRespawn) {
      hitTimer = 0
      projectile.playerHit = false
      projectile.position.x = position.x
      projectile.position.y = position.y
      projectile.getAngleToTarget(player)
    }

    // TODO: Draw sprites
    g.setColor(Color.BLUE)
    g.drawFilledCircle(position.x, position.y, colliderRadius, Color.RED)
    g.drawFilledRectangle(position.x, position.y, 48, 48, 0)
  }
}
