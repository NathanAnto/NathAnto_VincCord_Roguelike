package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable
import scala.math.{atan2, toDegrees}

class WarlockMob(pos: Vector2) extends Mob(pos) {
  var projectile: MobProjectile = new MobProjectile(this, pos)
  var projectileRespawn: Float = 5f
  maxHp = 1f
  hp = maxHp

  animations = mutable.HashMap()

  val idleRightAnim: Animation = new Animation("data/images/warlock/warlock_idle_right.png", 4, 24, 24)
  val idleLeftAnim: Animation = new Animation("data/images/warlock" +
    "/warlock_idle_left.png", 4, 24, 24)

  animations.addOne("idle_right", idleRightAnim)
  animations.addOne("idle_left", idleLeftAnim)

  override def toString: String = s"Warlock ${super.toString}"

  override def move_fc(): Unit = {
    if(player.position.x < position.x) {
      currentAnimation = animations("idle_left")
    }

    if (player.position.x > position.x) {
      currentAnimation = animations("idle_right")
    }
  }

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

    g.draw(
      currentAnimation.playAnimation(),
      position.x - currentAnimation.SPRITE_WIDTH / 6,
      position.y - currentAnimation.SPRITE_HEIGHT / 6
    )
  }
}
