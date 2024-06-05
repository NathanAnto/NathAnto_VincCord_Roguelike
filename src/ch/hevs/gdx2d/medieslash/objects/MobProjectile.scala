package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class MobProjectile(mob: Mob, startPos: Vector2) extends Projectile(startPos) {

  var playerHit: Boolean = false

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if(getCollider(colliderRadius).overlaps(player.getCollider(player.colliderRadius))) {
      // player hit
      if(!playerHit) {
        player.takeDamage(mob.damage)
        playerHit = true
      }
    }
  }
}
