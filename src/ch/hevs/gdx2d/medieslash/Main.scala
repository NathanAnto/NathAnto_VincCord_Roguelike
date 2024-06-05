package ch.hevs.gdx2d.medieslash

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.MapManager.tiledLayer
import ch.hevs.gdx2d.medieslash.levels.{Door, LevelManager, MapManager}
import ch.hevs.gdx2d.medieslash.objects.{ArcherMob, GameObject, Mob, MobManager, MobProjectile, Player, PlayerProjectile, Projectile}
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.controllers.{Controller, Controllers}
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer

object Main {
  def main(args: Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920, 1080) {

  var player: Player = _
  var dt: Float = 0f;

  var proj_player: PlayerProjectile = _
  var proj_mob4: MobProjectile = _
  var compte: Double = 0
  var compteProjectil: Int = 10
  var compteProjectilMob: Int = 10

  // Controller
  var ctrl: Controller = null
  var leftSickVal = Vector2.Zero.cpy // X,Y values of the left stick (POV)

  // img
  private var imgBitmap: BitmapImage = null

  // temp move player
  var move_left: Boolean = false
  var move_rigt: Boolean = false
  var move_up: Boolean = false
  var move_down: Boolean = false

  // temp move projectil
  var proj_move_left: Boolean = false
  var proj_move_rigt: Boolean = false
  var proj_move_up: Boolean = false
  var proj_move_down: Boolean = false

  override def onInit(): Unit = {
    setTitle("MedieSlash")

    // Create player
    player = new Player(new Vector2(500, 500))
    val walkRightAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_right.png", 4)
    val walkLeftAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_left.png", 4)
    val walkupAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_up.png", 4)
    val walkdownAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_down.png", 4)

    player.animations.addOne("right", walkRightAnim)
    player.animations.addOne("left", walkLeftAnim)
    player.animations.addOne("up", walkupAnim)
    player.animations.addOne("down", walkdownAnim)

    player.currentAnimation = player.animations("right")

    // player projectile
    proj_player = new PlayerProjectile(new Vector2(player.position.x,player.position.y))

    // Controller
    if (Controllers.getControllers.size > 0){
      ctrl = Controllers.getControllers.first
      println(s"Controllleer: $ctrl")
    }

    LevelManager.generateLevels(
      w = 3, h = 3,
      n = 5, m = 9
    )

    MobManager.generateMobs()

    LevelManager.startLevel()
  }

  /**
   * This method is called periodically by the engine
   * @param g
   */
  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()
    dt += Gdx.graphics.getDeltaTime

    // Camera follows the hero
    g.zoom(MapManager.zoom)
    g.moveCamera(player.position.x, player.position.y, tiledLayer.getWidth * tiledLayer.getTileWidth, tiledLayer.getHeight * tiledLayer.getTileHeight)
    MapManager.render(g)

    //player projectile
//    proj_player.draw(g)
    proj_player.move_projectil(proj_move_left,proj_move_rigt,proj_move_up,proj_move_down)
    proj_move_left = move_left
    proj_move_rigt = move_rigt
    proj_move_down = move_down
    proj_move_up = move_up

    if(dt > player.attackSpeed){
      dt = 0
      proj_player.velocity.x = proj_player.nextVelocity.x
      proj_player.velocity.y = proj_player.nextVelocity.y

      proj_player.position.x = player.position.x
      proj_player.position.y = player.position.y
      proj_player.targetsHit = ArrayBuffer()
    }

    // move player
    player.move_fc(move_left,move_rigt,move_up,move_down)
    player.move_controller(leftSickVal.x,leftSickVal.y)

    for (obj <- GameObject.getGameobjects().toArray) {
      obj match {
        case player: Player =>
          if(player.hp > 0) {
            player.draw(g)
          }else{
            g.drawPicture(getWindowHeight / 2,getWindowWidth / 2,imgBitmap)
          }
        case mob: Mob =>
          if(LevelManager.getCurrentLevel.currentRoom.mobs.contains(mob)) {
            mob.draw(g)
            mob.move_fc()
          }
        case door: Door =>
          door.draw(g)

        case projectile: Projectile => {
          projectile.draw(g)
        }
        case _ =>
      }
    }

    g.drawSchoolLogo()
  }

  // key
  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)

    keycode match {
      case Input.Keys.RIGHT =>
        move_rigt = true

      case Input.Keys.LEFT =>
        move_left = true

      case Input.Keys.UP =>
        move_up = true

      case Input.Keys.DOWN =>
        move_down = true

      case _ =>

    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    super.onKeyUp(keycode)

    keycode match {
      case Input.Keys.RIGHT =>
        move_rigt = false

      case Input.Keys.LEFT =>
        move_left = false

      case Input.Keys.UP =>
        move_up = false

      case Input.Keys.DOWN =>
        move_down = false

      case _ =>

    }

  }
}