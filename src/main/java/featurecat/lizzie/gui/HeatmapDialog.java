package featurecat.lizzie.gui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JDialog;

import org.json.JSONArray;

import featurecat.lizzie.Lizzie;
import featurecat.lizzie.util.WindowPosition;

@SuppressWarnings("serial")
public class HeatmapDialog extends JDialog {

  private HeatmapPane heatmapPane;

  public HeatmapDialog(Window owner) {
    super(owner);

    setTitle("Heatmap");
    heatmapPane = new HeatmapPane();
    add(heatmapPane, BorderLayout.CENTER);

    JSONArray pos = WindowPosition.gtpHeatmapPos();
    if (pos != null) {
      this.setBounds(pos.getInt(0), pos.getInt(1), pos.getInt(2), pos.getInt(3));
    }  else {
      pack();
    }

    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        toggle();
      }
    });
  }

  public void toggle() {
    Lizzie.config.showHeatmap = !Lizzie.config.showHeatmap;
    Lizzie.heatmap.setVisible(Lizzie.config.showHeatmap);
    Lizzie.config.uiConfig.put("show-heatmap", Lizzie.config.showHeatmap);
    try {
      Lizzie.config.save();
    } catch (IOException es) {
    }
  }

  @Override
  public void setVisible(boolean b) {
    super.setVisible(b);
    Lizzie.leelaz.isShowingHeatmap = b;
    Lizzie.leelaz.heatmap();
  }

}
