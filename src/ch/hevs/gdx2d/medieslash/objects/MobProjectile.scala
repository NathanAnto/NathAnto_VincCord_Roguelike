package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class MobProjectile(mob: Mob, startPos: Vector2) extends Projectile(startPos) {
  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)

    if(collider.overlaps(player.collider)) {
      // player hit
      player.takeDamage(mob.damage)
      GameObject.destroyInstance(this)
    }
  }
}
