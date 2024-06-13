package ch.hevs.gdx2d.medieslash.objects

import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable.ArrayBuffer

abstract class GameObject {
  var tag: String = ""
  var position: Vector2

  def getCollider(radius: Int) = new Circle(position.x, position.y, radius)

  GameObject.objectCreated(this)
}

object GameObject {
  private val gameObjects: ArrayBuffer[GameObject] = ArrayBuffer()

  def getGameobjects(): ArrayBuffer[GameObject] = gameObjects

  private def objectCreated(g: GameObject): Unit = {
    gameObjects.addOne(g)
  }

  def getObjectsByTag(tag: String): ArrayBuffer[GameObject] = {
    gameObjects.filter((g: GameObject) => g.tag == tag)
  }
}
