package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.levels.LevelManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

class PlayerProjectile(startPos: Vector2) extends Projectile(startPos) {
  override def draw(g: GdxGraphics): Unit = {
    if(targetsHit.nonEmpty) return
    super.draw(g)

    for(m: GameObject <- GameObject.getObjectsByTag("mob")) {
      val mob = m.asInstanceOf[Mob]
      if(LevelManager.getCurrentLevel.currentRoom.mobs.contains(mob)) {
        if(getCollider(colliderRadius).overlaps(mob.getCollider(mob.colliderRadius))) {
          mob.takeDamage(player.damage)
          targetsHit += mob
        }
      }
    }
  }
}
