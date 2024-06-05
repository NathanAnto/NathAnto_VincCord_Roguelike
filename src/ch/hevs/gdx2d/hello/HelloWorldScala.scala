package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.{Interpolation, Vector2}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.{PortableApplication, Xbox}
import ch.hevs.gdx2d.lib.utils.Logger
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.objects.{Mob, MobProjectile, Player, PlayerProjectile}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapRenderer, TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection

import scala.collection.immutable.{HashMap, TreeMap}


/**
 * Hello World demo in Scala
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
object HelloWorldScala {

  def main(args: Array[String]): Unit = {
    new HelloWorldScala
  }
}
// intersect / contains // overlaps circle

class HelloWorldScala extends PortableApplication(1000,1000)  {


  var player: Player = _
  var mob: Mob = _
  var mob1: Mob = _
  var mob2: Mob = _
  var mob3: Mob = _
  var mob4: Mob = _
  var mob5: Mob = _
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


  // Tiles management
  var tiledMap: TiledMap = null

  var tiledMapRenderer: TiledMapRenderer = null


  var zoom: Float = 1f


  private val position = new Vector2(0, 0)


  override def onInit(): Unit = {
    setTitle("Test Map")

    // create player
    player = new Player(new Vector2(200, 200))
    val walkRightAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_right.png", 4)
    val walkLeftAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_left.png", 4)
    val walkupAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_up.png", 4)
    val walkdownAnim: Animation = new Animation("data/images/player/lumberjack_sheet_walk_down.png", 4)

    player.animations.addOne("right", walkRightAnim)
    player.animations.addOne("left", walkLeftAnim)
    player.animations.addOne("up", walkupAnim)
    player.animations.addOne("down", walkdownAnim)

    player.currentAnimation = player.animations("right")

    player.tag = "player"

    // player projectile
    proj_player = new PlayerProjectile(new Vector2(player.position.x,player.position.y))




    // create mob
    mob = new Mob(new Vector2(300,300))
    mob1 = new Mob(new Vector2(500,400))
    mob1.xSpeed = 2
    mob1.ySpeed = 2
    mob2 = new Mob(new Vector2(600,400))
    mob3 = new Mob(new Vector2(400,200))
    mob3.xSpeed = 3
    mob3.ySpeed = 3
    mob4 = new Mob(new Vector2(400,300))

    mob5 = new Mob(new Vector2(200,200))


    proj_mob4 = new MobProjectile(mob4,new Vector2(mob4.position.x,mob4.position.y))






    // create map
    tiledMap = new TmxMapLoader().load("data/maps/desert.tmx")
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)

    // Controller
    if (Controllers.getControllers.size > 0){
      ctrl = Controllers.getControllers.first
      println(s"Controllleer: $ctrl")
    }

    // img lose
    imgBitmap = new BitmapImage("data/images/loser.jpg")

  }

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




  // mob moove (mettre les 4 mouvements didi dans la classe pour switch entre eux)

  /**
   * This method is called periodically by the engine
   *
   * @param g
   */
  override def onGraphicRender(g: GdxGraphics): Unit = {
    // temp test compte
    compte += 0.1
    // Clears the screen
    g.clear()
    g.zoom(zoom)
    g.moveCamera(position.x, position.y)


    tiledMapRenderer.setView(g.getCamera)
    tiledMapRenderer.render()
    // Draw everything
    //g.drawFPS()



    //player projectile
    proj_player.draw(g)
    proj_player.move_projectil(proj_move_left,proj_move_rigt,proj_move_up,proj_move_down)
    if(compte.toInt == compteProjectil){
      compteProjectil += 10
      proj_player.position.x = player.position.x
      proj_player.position.y = player.position.y
      proj_move_left = move_left
      proj_move_rigt = move_rigt
      proj_move_down = move_down
      proj_move_up = move_up
    }


    // mob hp
    if(mob.hp > 0){
      mob.draw(g)
    }
    if(mob1.hp > 0){
      mob1.draw(g)
    }
    if(mob2.hp > 10){
      mob2.draw(g)
    }
    if(mob3.hp > 10){
      mob3.draw(g)
    }
    if(mob4.hp > 0){
      mob4.draw(g)
    }
    if(mob5.hp > 10){
      mob5.draw(g)
    }




    // mob Projectile
    proj_mob4.draw(g)
    proj_mob4.move_proj_towards_player()
    if(compte.toInt == compteProjectilMob){
      compteProjectilMob += 5
      proj_mob4.position.x = mob4.position.x
      proj_mob4.position.y = mob4.position.y
      proj_mob4.changeAngleAndSpeed(player.position.x,player.position.y)
    }


    // move player

    player.move_fc(move_left,move_rigt,move_up,move_down)

    player.move_controller(leftSickVal.x,leftSickVal.y)

    // move mob

    mob.move_fc(player.position.x,player.position.y,mob.position.x,mob.position.y)
    mob1.move_fc(player.position.x,player.position.y,mob1.position.x,mob1.position.y)
    mob2.move_fc(player.position.x,player.position.y,mob2.position.x,mob2.position.y)
    mob3.move_fc(player.position.x,player.position.y,mob3.position.x,mob3.position.y)
    mob5.move_fc(player.position.x,player.position.y,mob5.position.x,mob5.position.y)


    //player
    if(player.hp > 0){
      player.draw(g)
    }else{
      g.drawPicture(getWindowHeight / 2,getWindowWidth / 2,imgBitmap)
    }
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

  // C:\Users\cordo\IdeaProjects\NathAnto_VincCord_Roguelike\libs\gdx2d-desktop-1.2.2.jar!\ch\hevs\gdx2d\lib\interfaces\KeyboardInterface.class
  // Controller
  override def onControllerConnected(controller: Controller): Unit = {
    super.onControllerConnected(controller)
    ctrl = controller
    println(s"c'est connecté: $ctrl")
  }

  override def onControllerDisconnected(controller: Controller): Unit = {
    ctrl = null
  }

  override def onControllerKeyDown(controller: Controller ,buttonCode: Int): Unit = {
    super.onControllerKeyDown(controller,buttonCode)

    if(buttonCode == Xbox.A){
      println("ouiiiiiiiiiiiiiiiii")
    }

  }

  override def onControllerAxisMoved(controller: Controller, axisCode: Int, value: Float): Unit = {
    super.onControllerAxisMoved(controller, axisCode, value)
    if (axisCode == Xbox.L_STICK_HORIZONTAL_AXIS){
      leftSickVal.x = value
    }
    if (axisCode == Xbox.L_STICK_VERTICAL_AXIS){
      leftSickVal.y = value
    }

  }



}
