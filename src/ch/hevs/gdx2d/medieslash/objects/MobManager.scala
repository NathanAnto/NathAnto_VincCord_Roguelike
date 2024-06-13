package ch.hevs.gdx2d.medieslash.objects

import ch.hevs.gdx2d.medieslash.levels.{LevelManager, Room}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object MobManager {
  var mobs: Array[Mob] = new Array(10)

  private val MAX_MOBS_PER_ROOM = 8

  // Create and add all mobs in array
  def generateMobs(): Unit = {
    val l = mobs.length

    val meleeMobs: Int = 70 * l / 100 // 70 %
    val archerMobs: Int = 30 * l / 100 // 30 %

    for (i <- 0 until meleeMobs) {
      mobs(i) = new Mob(new Vector2(2000,2000))
    }
    for (i <- (l-archerMobs) until l) {
      mobs(i) = new WarlockMob(new Vector2(2000,2000))
    }
  }

  // Get mobs for current room
  def getMobs(): ArrayBuffer[Mob] = {
    val m: ArrayBuffer[Mob] = ArrayBuffer()
    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    val numMobs = MAX_MOBS_PER_ROOM - (LevelManager.levels.size - LevelManager.getCurrentLevel.id + 1)

    var usedIndices = Set[Int]()
    while (usedIndices.size < numMobs) {
      val i = Random.between(0, mobs.length)
      if (!usedIndices.contains(i)) {
        usedIndices += i
        val newMob = mobs(i)
        newMob.position = new Vector2(Random.between(100, currentRoom.width - 100), Random.between(100, currentRoom.height - 100))
        m += newMob
      }
    }

    m
  }

  // Reset mob health after new room entered
  def resetMobs(): Unit = {
    for(mob <- mobs) {
      mob.hp = mob.maxHp
    }
  }

  // Upgrade mob stats for after each level
  def upgradeMobs(): Unit = {
    for(m <- mobs) {
      m.maxHp += 1
      m.hp = m.maxHp
      m.damage += 0.5f
      m.speed += 0.1f
    }
  }
}