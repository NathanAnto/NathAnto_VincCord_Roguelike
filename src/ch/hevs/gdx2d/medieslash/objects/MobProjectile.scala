package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

class MobProjectile(mob: Mob, startPos: Vector2) extends Projectile(startPos) {

  var playerHit: Boolean = false

  override def draw(g: GdxGraphics): Unit = {
    if(playerHit) return

    if(getCollider(colliderRadius).overlaps(player.getCollider(player.colliderRadius))) {
      // player hit
      if(!playerHit) {
        println("Player hit")
        player.takeDamage(mob.damage)
        playerHit = true
      }
    }

    position.x += xSpeed
    position.y += ySpeed

    g.setColor(Color.RED)
    g.drawFilledCircle(position.x, position.y, colliderRadius, Color.GREEN)
    g.drawFilledRectangle(position.x, position.y, 20, 20, 0)
  }
}
