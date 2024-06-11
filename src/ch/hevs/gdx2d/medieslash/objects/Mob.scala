package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.effects.Animation
import ch.hevs.gdx2d.medieslash.levels.RoomManager
import ch.hevs.gdx2d.medieslash.upgrades.XPManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable

class Mob(p: Vector2) extends Entity {
  override var position: Vector2 = p
  override var sprites: Spritesheet = _
  override var animations: mutable.HashMap[String, Animation] = mutable.HashMap()
  override var currentAnimation: Animation = _
  override var speed: Float = 1f
  override var diagoSpeed: Int = (speed * 2 * math.cos(math.Pi / 4)).toInt
  override var maxHp = 2
  override var hp = maxHp
  override var hitTimer: Float = 0f
  var velocity: Vector2 = new Vector2(1,0)
  var attackSpeed: Float = 2f

  var player: Player = GameObject.getObjectsByTag("player")(0).asInstanceOf[Player]

  tag = "mob"

  override def toString: String = s"Mob [hp: $hp]"


  val walkRightAnim: Animation = new Animation("data/images/skeleton/skeleton_walk_right.png", 13, 22, 33)
  val walkLeftAnim: Animation = new Animation("data/images/skeleton/skeleton_walk_left.png", 13, 22, 33)

  val attackRightAnim: Animation = new Animation("data/images/skeleton/skeleton_attack_right.png", 15, 43, 37)
  attackRightAnim.FRAME_TIME = 0.1f
  val attackLeftAnim: Animation = new Animation("data/images/skeleton/skeleton_attack_left.png", 15, 43, 37)
  attackLeftAnim.FRAME_TIME = 0.1f

  attackSpeed = (attackRightAnim.FRAME_TIME*6).toFloat

  animations.addOne("right", walkRightAnim)
  animations.addOne("left", walkLeftAnim)

  animations.addOne("attack_right", attackRightAnim)
  animations.addOne("attack_left", attackLeftAnim)

  currentAnimation = walkLeftAnim

  def move_fc(): Unit = {
    var startOffset: Int = (position.y - speed).toInt
    var endOffset: Int =  (position.y + speed).toInt

    if(player.position.x <= position.x && Array.range(startOffset, endOffset).contains(player.position.y.toInt)) {
      velocity.x = -speed
      velocity.y = 0
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }

    if(player.position.x >= position.x && Array.range(startOffset, endOffset).contains(player.position.y.toInt)){
      velocity.x = speed
      velocity.y = 0
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }

    startOffset = (position.x - speed).toInt
    endOffset = (position.x + speed).toInt
    if(Array.range(startOffset, endOffset).contains(player.position.x.toInt) && player.position.y >= position.y){
      velocity.x = 0
      velocity.y = speed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }
    if(Array.range(startOffset, endOffset).contains(player.position.x.toInt) && player.position.y <= position.y){
      velocity.x = 0
      velocity.y = -speed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }

    if(player.position.x <= position.x-speed && player.position.y >= position.y+speed){
      velocity.x = -diagoSpeed
      velocity.y = diagoSpeed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
      }
    }

    if(player.position.x >= position.x+speed && player.position.y >= position.y+speed){
      velocity.x = diagoSpeed
      velocity.y = diagoSpeed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }

    if(player.position.x <= position.x-speed && player.position.y <= position.y-speed){
      velocity.x = -diagoSpeed
      velocity.y = -diagoSpeed
      if (currentAnimation != animations("left")) {
        currentAnimation = animations("left")
        }
    }

    if(player.position.x >= position.x+speed && player.position.y <= position.y-speed){
      velocity.x = diagoSpeed
      velocity.y = -diagoSpeed
      if (currentAnimation != animations("right")) {
        currentAnimation = animations("right")
      }
    }
  }

  override def draw(g: GdxGraphics): Unit = {
    if(hp <= 0) return

    if(getCollider(colliderRadius).overlaps(player.getCollider(player.colliderRadius))) {
      velocity.x = 0
      velocity.y = 0

      dealDamage()
    } else {
      attackLeftAnim.resetAnimation()
      attackRightAnim.resetAnimation()

      position.x += velocity.x * speed
      position.y += velocity.y * speed
    }

    g.draw(
      currentAnimation.playAnimation(),
      position.x - currentAnimation.SPRITE_WIDTH / 6,
      position.y - currentAnimation.SPRITE_HEIGHT / 6
    )
  }

  override def takeDamage(dmg: Float): Unit = {
    // Do Effect
    hp -= dmg
    if (hp <= 0) {
      RoomManager.mobDied(this)
      XPManager.gainedByMob(player, this)
    }
  }

  def dealDamage(): Unit = {
    if(currentAnimation != animations("attack_left") && currentAnimation != animations("attack_right")) {
      if(player.position.x < position.x)
        currentAnimation = animations("attack_left")
      else
        currentAnimation = animations("attack_right")
    }

    hitTimer += Gdx.graphics.getDeltaTime

    if(hitTimer > attackSpeed) {
      // TODO: Attack animation
      println("Player hit")
      hitTimer = 0
      player.takeDamage(damage)
    }
  }
}
