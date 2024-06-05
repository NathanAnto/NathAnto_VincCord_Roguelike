//package ch.hevs.gdx2d.medieslash.objects
//
//import ch.hevs.gdx2d.lib.GdxGraphics
//import com.badlogic.gdx.math.Vector2
//
//class MeleeMob(initialPosition: Vector2) extends Mob(initialPosition) {
//
//  override def draw(g: GdxGraphics): Unit = {
//
//    val alignment = computeAlignment()
//    val cohesion = computeCohesion()
//    val separation = computeSeparation()
//
//    velocity.x += alignment.x + cohesion.x + separation.x
//    velocity.y += alignment.y + cohesion.y + separation.y
//    velocity = velocity.nor()
//
//    super.draw(g)
//  }
//
//  private def computeAlignment(): Vector2 = {
//    var v: Vector2 = new Vector2()
//    var neighborCount = 0
//
//    for(obj <- GameObject.getObjectsByTag("mob").toArray) {
//      obj match {
//        case entity: Entity => {
//          if (entity != this && distanceFrom(entity) < 300) {
//            v.x += velocity.x
//            v.y += velocity.y
//            neighborCount += 1
//          }
//        }
//      }
//    }
//
//    if (neighborCount == 0)
//      return v;
//    v.x /= neighborCount
//    v.y /= neighborCount
//    v.nor()
//  }
//
//  private def computeCohesion(): Vector2 = {
//    var v: Vector2 = new Vector2()
//    var neighborCount = 0
//
//    for(obj <- GameObject.getObjectsByTag("mob").toArray) {
//      obj match {
//        case entity: Entity => {
//          if (entity != this && distanceFrom(entity) < 300) {
//            v.x += velocity.x
//            v.y += velocity.y
//            neighborCount += 1
//          }
//        }
//      }
//    }
//
//    v.x += this.position.x
//    v.y += this.position.y
//
//    v.x /= neighborCount
//    v.y /= neighborCount
//    v.x -= this.position.x
//    v.y -= this.position.y
//
//    v.nor()
//  }
//
//  private def computeSeparation(): Vector2 = {
//    var v: Vector2 = new Vector2()
//    var neighborCount = 0
//
//    for(obj <- GameObject.getObjectsByTag("mob").toArray) {
//      obj match {
//        case entity: Entity => {
//          if (entity != this && distanceFrom(entity) < 300) {
//            v.x += entity.velocity.x - velocity.x
//            v.y += entity.velocity.y - velocity.x
//            neighborCount += 1
//          }
//        }
//      }
//    }
//
//    v.x *= -1
//    v.y *= -1
//    v
//  }
//}
