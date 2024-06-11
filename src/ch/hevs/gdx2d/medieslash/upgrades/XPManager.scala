package ch.hevs.gdx2d.medieslash.upgrades

import ch.hevs.gdx2d.medieslash.levels.LevelManager
import ch.hevs.gdx2d.medieslash.objects.{Mob, Player}
import ch.hevs.gdx2d.medieslash.ui.UIManager

import scala.collection.mutable
import scala.util.Random

object XPManager {
  var upgrades: mutable.HashMap[String, Upgrade] = mutable.HashMap(
    (UpgradeType.HEALTH, new Upgrade(UpgradeType.HEALTH, 1f)),
    (UpgradeType.DAMAGE, new Upgrade(UpgradeType.DAMAGE, 0.2f)),
    (UpgradeType.ATTACK_SPEED, new Upgrade(UpgradeType.ATTACK_SPEED, 0.05f)),
    (UpgradeType.HEAL, new Upgrade(UpgradeType.HEAL, 5f))
  )

  def gainedByMob(player: Player, mob: Mob) = {
    var xp = mob.maxHp + mob.damage
    player.xp += xp
    player.gold += LevelManager.getCurrentLevel.id

    println(s"XP: ${player.xp}")

    if (player.xp > player.level.nextLevelXp) {
      val nextLevelXp = (player.level.nextLevelXp * 1.5).toInt
      println(s"LEVEL UP")

      UIManager.dt = 0

      player.level = new PlayerLevel(nextLevelXp, getRandomUpgrade())
      upgradePlayer(player, player.level.upgrade)
    }
  }

  def getRandomUpgrade(): String = {
    val values = upgrades.values.toArray
    return values(Random.nextInt(values.length)).uType
  }

  def upgradePlayer(player: Player, upgrade: Upgrade): Unit = {
    upgrade.uType match {
      case UpgradeType.HEALTH => {
        player.maxHp += upgrade.amount
        upgrade.amount *= 1.1f
      }

      case UpgradeType.DAMAGE => {
        player.damage += upgrade.amount
        upgrade.amount *= 1.1f
      }

      case UpgradeType.ATTACK_SPEED => {
        player.attackSpeed -= upgrade.amount
        upgrade.amount *= 1.1f
      }

      case UpgradeType.HEAL => {
        player.hp += upgrade.amount
        if(player.hp > player.maxHp) {
          player.hp = player.maxHp
        }
      }
    }
//    println("STATS:")
//    println(player.maxHp)
//    println(player.hp)
//    println(player.damage)
//    println(player.attackSpeed)
  }
}
