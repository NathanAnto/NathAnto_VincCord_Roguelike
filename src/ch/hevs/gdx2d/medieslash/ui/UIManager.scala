package ch.hevs.gdx2d.medieslash.ui

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.levels.MapManager.tiledLayer
import ch.hevs.gdx2d.medieslash.levels.{LevelManager, MapManager}
import ch.hevs.gdx2d.medieslash.objects.Player
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Camera, Color}
import com.badlogic.gdx.math.Vector2

object UIManager {

  var dt: Float = 3f

  private var start_time = System.currentTimeMillis()

  def drawHealth(g: GdxGraphics, player: Player): Unit = {
    if(player.hp >= 5) g.setColor(Color.GREEN) else g.setColor(Color.RED)
    g.drawString(g.getCamera.position.x - 210,g.getCamera.position.y + 130,s"HP: ${player.hp.toInt.toString}")
  }

  def drawTime(g: GdxGraphics): Unit = {
    g.setColor(Color.WHITE)
    g.drawString(g.getCamera.position.x - 240,g.getCamera.position.y + 130,s"${(System.currentTimeMillis() - start_time) / 1000}")
  }

  def drawLevel(g: GdxGraphics): Unit = {
    g.setColor(Color.WHITE)
    val offset = 150
    g.drawString(g.getCamera.position.x + offset, g.getCamera.position.y + 130,s"Level ${LevelManager.getCurrentLevel.id}")
  }

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

  def drawImage(g: GdxGraphics, img: BitmapImage): Unit = {
    val pos = new Vector2(LevelManager.getCurrentLevel.currentRoom.width, LevelManager.getCurrentLevel.currentRoom.height)
    g.moveCamera(pos.x / 2, pos.y / 2, tiledLayer.getWidth * tiledLayer.getTileWidth, tiledLayer.getHeight * tiledLayer.getTileHeight)
    g.drawPicture(pos.x / 2 - 250, pos.y / 2 - 100, img)
    MapManager.zoom = 0.5f
  }
}
