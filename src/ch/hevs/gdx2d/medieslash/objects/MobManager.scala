package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.medieslash.levels.{LevelManager, Room}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object MobManager {
  var mobs: Array[Mob] = new Array(10)

  private val MAX_MOBS_PER_ROOM = 8

  def generateMobs(): Unit = {
    for (i <- 0 until 7) {
      mobs(i) = new Mob(new Vector2(2000,2000))
    }
    for (i <- 7 until 10) {
      mobs(i) = new ArcherMob(new Vector2(2000,2000))
    }
  }

  def getMobs(): ArrayBuffer[Mob] = {
    var m: ArrayBuffer[Mob] = ArrayBuffer()
    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    val numMobs = MAX_MOBS_PER_ROOM - (LevelManager.levels.size - LevelManager.getCurrentLevel.id + 1)

    println(s"$numMobs in this room")

    for(x <- 0 until numMobs) {
      val i = Random.between(0, mobs.length)
      val newMob = mobs(i)
      newMob.position = new Vector2(Random.between(100, currentRoom.width - 100), Random.between(100, currentRoom.height - 100))
      m += newMob
    }

    m
  }

  def resetMobs(): Unit = {
    for(mob <- mobs) {
      mob.hp = mob.maxHp
    }
  }

  def upgradeMobs(): Unit = {
    for(m <- mobs) {
      m.maxHp += 1
      m.hp = m.maxHp
      m.damage += 0.5f
      m.speed += 0.1f
    }
  }
}