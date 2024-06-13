package ch.hevs.gdx2d.medieslash

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.MapManager.tiledLayer
import ch.hevs.gdx2d.medieslash.levels.{Door, LevelManager, MapManager}
import ch.hevs.gdx2d.medieslash.objects.{Entity, GameObject, Mob, MobManager, Object, Player, PlayerProjectile}
import ch.hevs.gdx2d.medieslash.ui.UIManager
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.controllers.{Controller, Controllers}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920,1080) {

  var dt: Float = 0f;

  var player: Player = _
  var proj_player: PlayerProjectile = _

  private var winImg: BitmapImage = null // img win
  private var loseImg: BitmapImage = null // img lose

  // Controller
  var ctrl: Controller = null
  var leftSickVal = Vector2.Zero.cpy // X,Y values of the left stick (POV)

  // temp move player
  var move_left: Boolean = false
  var move_rigt: Boolean = false
  var move_up: Boolean = false
  var move_down: Boolean = false

  // Move projectiles
  var proj_move_left: Boolean = false
  var proj_move_rigt: Boolean = false
  var proj_move_up: Boolean = false
  var proj_move_down: Boolean = false

  var f: MusicPlayer = _

  override def onInit(): Unit = {

    // Create player
    player = new Player(new Vector2(250, 250))

    winImg = new BitmapImage("data/images/loser.jpg")
    loseImg = new BitmapImage("data/images/loser.jpg")

    // Create animations
    val walkRightAnim: Animation = new Animation("data/images/player/player_run_right.png", 4, 24, 24)
    val walkLeftAnim: Animation = new Animation("data/images/player/player_run_left.png", 4, 24, 24)
    val idleRightAnim: Animation = new Animation("data/images/player/player_idle_right.png", 4, 24, 24)
    val idleLeftAnim: Animation = new Animation("data/images/player/player_idle_left.png", 4, 24, 24)
    val hitAnim: Animation = new Animation("data/images/player/player_hit.png", 1, 24, 24)

    player.animations.addOne("right", walkRightAnim)
    player.animations.addOne("left", walkLeftAnim)
    player.animations.addOne("idle_right", idleRightAnim)
    player.animations.addOne("idle_left", idleLeftAnim)
    player.animations.addOne("hit", hitAnim)

    player.currentAnimation = player.animations("right")

    // player projectile
    proj_player = new PlayerProjectile(new Vector2(player.position.x, player.position.y))

    // Controller
    if (Controllers.getControllers.size > 0) {
      ctrl = Controllers.getControllers.first
    }

    // Load the MP3
    f = new MusicPlayer("data/music/electroMedi.mp3")

    // Generate levels and rooms
    LevelManager.generateLevels(
      w = 3, h = 3,
      n = 5, m = 9
    )

    // Generate mobs used in rooms
    MobManager.generateMobs()

    // Start game
    LevelManager.startLevel()
  }

  /**
   * This method is called periodically by the engine
   *
   * @param g
   */
  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()

    if(LevelManager.gameWon) {
      UIManager.drawImage(g, winImg)
    }

    dt += Gdx.graphics.getDeltaTime

    // player projectile
    proj_player.move_projectil(proj_move_left,proj_move_rigt,proj_move_up,proj_move_down)
    proj_move_left = move_left
    proj_move_rigt = move_rigt
    proj_move_down = move_down
    proj_move_up = move_up

    if (dt > player.attackSpeed) {
      dt = 0
      proj_player.velocity.x = proj_player.nextVelocity.x
      proj_player.velocity.y = proj_player.nextVelocity.y

      proj_player.position.x = player.position.x
      proj_player.position.y = player.position.y
      proj_player.targetsHit = ArrayBuffer()
    }

    // move player
    player.move_fc(move_left, move_rigt, move_up, move_down)
    player.move_controller(leftSickVal.x, leftSickVal.y)

    // Render map
    MapManager.render(g)
    g.zoom(MapManager.zoom)

    // While player alive
    if (player.hp > 0) {
      for (obj <- GameObject.getGameobjects().toArray) {
        obj match {
          case player: Player =>
            // Camera follows the hero
            g.moveCamera(player.position.x, player.position.y, tiledLayer.getWidth * tiledLayer.getTileWidth, tiledLayer.getHeight * tiledLayer.getTileHeight)
            player.draw(g)
          case mob: Mob =>
            if (LevelManager.getCurrentLevel.currentRoom.mobs.contains(mob)) {
              mob.draw(g)
              mob.move_fc()
            }
          case e: Entity => e.draw(g)
          case o: Object => o.draw(g)
          case _ =>
        }
      }

      // UI
      UIManager.drawHealth(g, player)
      UIManager.drawTime(g)
      UIManager.drawLevel(g)
      UIManager.upgradeLabel(g, player)

      g.drawSchoolLogo()
      g.drawFPS()
    } else {
      UIManager.drawImage(g, loseImg)
    }

    // Play Music
    if(!f.isPlaying){
      f.loop()
    }
  }

  override def onKeyDown(keycode: Int): Unit = {
    keycode match {
      case Input.Keys.RIGHT => move_rigt = true
      case Input.Keys.LEFT => move_left = true
      case Input.Keys.UP => move_up = true
      case Input.Keys.DOWN => move_down = true
      case _ =>
    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    keycode match {
      case Input.Keys.RIGHT => move_rigt = false
      case Input.Keys.LEFT => move_left = false
      case Input.Keys.UP => move_up = false
      case Input.Keys.DOWN => move_down = false
      case _ =>
    }

  }
}

