package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.math.Vector2

class PlayerProjectile(startPos: Vector2) extends Projectile(startPos) {

  override def draw(g: GdxGraphics): Unit = {
    super.draw(g)
    for(mob: GameObject <- GameObject.getObjectsByTag("mob")) {
      if(collider.overlaps(mob.collider)) {
        // Mob hit
        mob.asInstanceOf[Mob].takeDamage(player.damage)
        GameObject.destroyInstance(this)
      }
    }
  }
}
