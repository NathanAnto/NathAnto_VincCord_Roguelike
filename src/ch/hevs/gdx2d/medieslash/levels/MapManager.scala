package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapRenderer, TiledMapTile, TiledMapTileLayer}
import com.badlogic.gdx.math.Vector2

object MapManager {
  var tiledMapRenderer: TiledMapRenderer = null
  var tiledLayer: TiledMapTileLayer = null
  var currentMap: TiledMap = null
  var zoom: Float = .3f

  /**
   * exemple : getTile(myPosition,0,1) get the tile over myPosition
   *
   * @param position
   * The position on map (not on screen)
   * @param offsetX
   * The number of cells at right of the given position.
   * @param offsetY
   * The number of cells over the given position.
   * @return The tile around the given position | null
   */
  def getTile(position: Vector2, offsetX: Int, offsetY: Int) = try {
    val x = (position.x / tiledLayer.getTileWidth).toInt + offsetX
    val y = (position.y / tiledLayer.getTileHeight).toInt + offsetY
    tiledLayer.getCell(x, y).getTile
  } catch {
    case e: Exception =>
      null
  }

  /**
   * Get the "walkable" property of the given tile.
   *
   * @param tile
   * The tile to know the property
   * @return true if the property is set to "true", false otherwise
   */
  def isWalkable(tile: TiledMapTile): Boolean = {
    if (tile == null) return false
    val test = tile.getProperties.get("walkable")
    test.toString.toBoolean
  }

  /**
   * Get the "speed" property of the given tile.
   *
   * @param tile
   * The tile to know the property
   * @return The value of the property
   */
  def getSpeed(tile: TiledMapTile) = {
    val test = tile.getProperties.get("speed")
    test.toString.toFloat
  }

  def setNewMap(map: TiledMap): Unit = {
    currentMap = map

    tiledMapRenderer = new OrthogonalTiledMapRenderer(map)
    tiledLayer = map.getLayers.get(0).asInstanceOf[TiledMapTileLayer]
  }

  def render(g:GdxGraphics) = {
    // Render the tilemap
    tiledMapRenderer.setView(g.getCamera)
    tiledMapRenderer.render()
  }
}
