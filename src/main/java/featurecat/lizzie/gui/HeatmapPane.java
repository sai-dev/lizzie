package featurecat.lizzie.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import featurecat.lizzie.rules.Board;

@SuppressWarnings("serial")
class HeatmapPane extends JPanel {
  final private int DEFAULT_SIZE = 600;

  private BoardRenderer heatmapRenderer;

  public HeatmapPane() {
    heatmapRenderer = new BoardRenderer(false, true);
  }

  protected void paintComponent(Graphics g0) {
    super.paintComponent(g0);

    int width = getWidth();
    int height = getHeight();

    // initialize
    BufferedImage cachedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = (Graphics2D) cachedImage.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    int[] boardParams = BoardRenderer.availableLength(
        Math.max(width, Board.boardWidth + 5),
        Math.max(height, Board.boardHeight + 5),
        false,
        false);
    heatmapRenderer.setLocation((width - boardParams[0])/2, (height - boardParams[3])/2);
    heatmapRenderer.setBoardParam(boardParams);
    heatmapRenderer.draw(g);
    g.dispose();

    Graphics2D bsGraphics = (Graphics2D) g0; // bs.getDrawGraphics();
    bsGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    bsGraphics.drawImage(cachedImage, 0, 0, null);
    bsGraphics.dispose();
  }

  public Dimension getPreferredSize() {
    int[] params = BoardRenderer.availableLength(
      Math.max(DEFAULT_SIZE, Board.boardWidth + 5),
      Math.max(DEFAULT_SIZE, Board.boardHeight + 5),
      false,
      false);
    return new Dimension(params[0], params[3]);
  }
}
