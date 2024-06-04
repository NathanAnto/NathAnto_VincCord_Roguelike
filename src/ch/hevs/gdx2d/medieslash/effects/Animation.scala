package ch.hevs.gdx2d.medieslash.effects

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animation(val nFrames: Int, val SPRITE_WIDTH: Int = 64, val SPRITE_HEIGHT: Int = 64) {

  private val FRAME_TIME = 0.15 // Duration of each frame

  private var ss: Spritesheet = _

  private var currentFrame: Int = 0 // The currently selected sprite for animation

  private var dt: Float = 0

  def this(path: String, nFrames: Int) = {
    this(nFrames)
    ss = new Spritesheet(path, SPRITE_WIDTH, SPRITE_HEIGHT)
  }

  // Returns sprite to be played
  def playAnimation(): TextureRegion = {
    dt += Gdx.graphics.getDeltaTime

    if (dt > FRAME_TIME) {
      dt = 0
      currentFrame = (currentFrame + 1) % nFrames
    }

    return ss.sprites(0)(currentFrame)
  }





}
