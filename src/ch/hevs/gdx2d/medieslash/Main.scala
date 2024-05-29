package ch.hevs.gdx2d.medieslash

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.MapManager.tiledLayer
import ch.hevs.gdx2d.medieslash.levels.{LevelManager, MapManager}
import ch.hevs.gdx2d.medieslash.objects.{Entity, GameObject, Mob, Player}
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

object Main {
  def main(args: Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920, 1080) {

  var player: Player = _
  var mob: Mob = _

  override def onInit(): Unit = {
    setTitle("MedieSlash")

    LevelManager.generateLevels(
      w = 3, h = 3,
      n = 5, m = 9
    )

//    MapManager.setNewMap(LevelManager.getCurrentLevel.currentRoom.map)

    // Create player
    player = new Player(new Vector2(200, 540))
    val walkRightAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_right.png", 4)
    val walkLeftAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_left.png", 4)

    player.animations.addOne("right", walkRightAnim)
    player.animations.addOne("left", walkLeftAnim)

    player.currentAnimation = player.animations("right")

    // Create mob
    mob = new Mob(new Vector2(700, 500))
  }

  /**
   * This method is called periodically by the engine
   * @param g
   */
  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()

    // Camera follows the hero
//    g.zoom(MapManager.zoom)
//    g.moveCamera(player.position.x, player.position.y, tiledLayer.getWidth * tiledLayer.getTileWidth, tiledLayer.getHeight * tiledLayer.getTileHeight)
//    MapManager.render(g)

    for (obj <- GameObject.getGameobjects().toArray) {
      obj match {
        case entity: Entity =>
          entity.draw(g)
        case _ =>
          print()
      }
    }

    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    println("Key pressed")
    keycode match {
      case Input.Keys.RIGHT =>
        player.position.add(50, 0)
        if(player.currentAnimation != player.animations("right"))
          player.currentAnimation = player.animations("right")

      case Input.Keys.LEFT =>
        player.position.add(-50, 0)
        if(player.currentAnimation != player.animations("left"))
          player.currentAnimation = player.animations("left")

      case Input.Keys.UP =>
        player.position.add(0, 50)

      case Input.Keys.DOWN =>
        player.position.add(0, -50)
      case _ =>

    }
  }

  // Manage keyboard events
  override def onKeyUp(keycode: Int): Unit = {
    super.onKeyUp(keycode)
  }
}