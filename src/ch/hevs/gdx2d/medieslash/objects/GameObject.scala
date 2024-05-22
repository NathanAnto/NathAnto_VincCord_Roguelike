package ch.hevs.gdx2d.medieslash.objects

import com.badlogic.gdx.math.{Circle, Vector2}

import scala.collection.mutable.ArrayBuffer

abstract class GameObject {
  var tag: String = ""
  var position: Vector2
  var collider: Circle

  GameObject.objectCreated(this)
}

object GameObject {
  private var gameObjects: ArrayBuffer[GameObject] = ArrayBuffer()

  def getGameobjects(): ArrayBuffer[GameObject] = gameObjects

  private def objectCreated(g: GameObject): Unit = {
    println(s"GameObject $g created")
    gameObjects.addOne(g)
  }

  def getObjectsByTag(tag: String): ArrayBuffer[GameObject] = {
    gameObjects.filter((g: GameObject) => g.tag == tag)
  }

  def destroyInstance(g: GameObject): Unit = {
    // TODO: Find better way to destroy object
    g.position.x = 2000
    g.position.y = 2000
    gameObjects -= g
  }
}
