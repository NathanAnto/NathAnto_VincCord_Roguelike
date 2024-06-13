package ch.hevs.gdx2d.medieslash.upgrades

class PlayerLevel(val nextLevelXp: Float, val uType: String) {
  val upgrade: Upgrade = XPManager.upgrades(uType)
}
