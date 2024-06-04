package ch.hevs.gdx2d.medieslash.levels

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.medieslash.levels.LevelManager.getCurrentLevel
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapRenderer, TiledMapTile, TiledMapTileLayer}
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

object MapManager {
  var tiledMapRenderer: TiledMapRenderer = null
  var tiledLayer: TiledMapTileLayer = null
  var currentMap: TiledMap = null
  var zoom: Float = .5f

  var doorLayers: mutable.HashMap[String, TiledMapTileLayer] = new mutable.HashMap[String, TiledMapTileLayer]

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

  def getTile(x: Int, y: Int) = try {
    tiledLayer.getCell(x, y).getTile
  } catch {
    case e: Exception =>
      null
  }

  def tileToPosition(tileX: Int, tileY: Int): Vector2 = try {
    new Vector2(
      (tileX * tiledLayer.getTileWidth)  + tiledLayer.getTileWidth/2,
      (tileY * tiledLayer.getTileHeight) + tiledLayer.getTileHeight/2
    )
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
  def isDoor(tile: TiledMapTile): Boolean = {
    if (tile == null) return false
    val test = tile.getProperties.get("door")
    test.toString.toBoolean
  }

  def setNewMap(map: TiledMap): Unit = {
    currentMap = map

    tiledMapRenderer = new OrthogonalTiledMapRenderer(map)
    tiledLayer = map.getLayers.get(0).asInstanceOf[TiledMapTileLayer]

    doorLayers = mutable.HashMap()

    doorLayers.addOne("N", map.getLayers.get(1).asInstanceOf[TiledMapTileLayer])
    doorLayers.addOne("S", map.getLayers.get(2).asInstanceOf[TiledMapTileLayer])
    doorLayers.addOne("E", map.getLayers.get(3).asInstanceOf[TiledMapTileLayer])
    doorLayers.addOne("W", map.getLayers.get(4).asInstanceOf[TiledMapTileLayer])
//    doorLayers.addOne("Boss", map.getLayers.get(5).asInstanceOf[TiledMapTileLayer])

    doorLayers.foreach { case (key, layer) =>
      layer.setVisible(false)
    }

    val currentRoom = LevelManager.getCurrentLevel.currentRoom
    currentRoom.width = tiledLayer.getTileWidth * tiledLayer.getWidth
    currentRoom.height = tiledLayer.getTileHeight * tiledLayer.getHeight

    LevelManager.getCurrentLevel.currentRoom.showTraversableDoors(getCurrentLevel)
    RoomManager.getDoors()
  }

  def render(g:GdxGraphics) = {
    // Render the tilemap
    tiledMapRenderer.setView(g.getCamera)
    tiledMapRenderer.render()
  }
}
