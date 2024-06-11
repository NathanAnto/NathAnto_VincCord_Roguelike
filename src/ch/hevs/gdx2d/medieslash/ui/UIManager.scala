package ch.hevs.gdx2d.medieslash.ui

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.objects.Player
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, Color}

object UIManager {

  var dt: Float = 3f

  def upgradeLabel(g: GdxGraphics, player: Player): Unit = {
    dt += Gdx.graphics.getDeltaTime
    if(dt < 3) {
      g.setColor(Color.GREEN)
      g.drawString(
        player.position.x- 50,
        player.position.y + (dt*10),
        s"LEVEL UP"
      )
    }
  }
}
