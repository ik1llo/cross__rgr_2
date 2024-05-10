package mybeans.data_sheet_graph;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import mybeans.data_sheet_table.DataSheet;

public class DataSheetGraph extends JPanel {
    private static final long serialVersionUID = 1L;

    private DataSheet data_sheet = null;

    private Color color;

    private boolean is_connected;
    
    private int delta_X;
    private int delta_Y;

    public DataSheetGraph() {
        super();
        initialize();
    }

    private void createUIComponents() { initialize(); }

    private void initialize() {
        is_connected = false;

        delta_X = 3;
        delta_Y = 3;

        color = Color.red;

        this.setSize(600, 600);
    }

    public DataSheet get_data_sheet() { return data_sheet; }

    public void set_data_sheet(DataSheet data_sheet) { this.data_sheet = data_sheet; }

    public boolean is_connected() { return is_connected; }

    public void set_connected(boolean is_connected) {
        this.is_connected = is_connected;
        repaint();
    }

    private double min_X() {
        double result = 0;
        if (data_sheet == null) return result;

        if (data_sheet.size() > 0) result = data_sheet.get_data_item(0).get_X();
        for (int k = 1; k < data_sheet.size(); k++)
            if (data_sheet.get_data_item(k).get_X() < result)
                result = data_sheet.get_data_item(k).get_X();

        return result;
    }

    private double max_X() {
        double result = 0;
        if (data_sheet == null) return result;

        if (data_sheet.size() > 0) result = data_sheet.get_data_item(0).get_X();
        for (int k = 1; k < data_sheet.size(); k++)
            if (data_sheet.get_data_item(k).get_X() > result)
                result = data_sheet.get_data_item(k).get_X();
    
        return result;
    }

    private double min_Y() {
        double result = 0;
        if (data_sheet == null) return result;

        if (data_sheet.size() > 0) result = data_sheet.get_data_item(0).get_Y();
        for (int k = 1; k < data_sheet.size(); k++)
            if (data_sheet.get_data_item(k).get_Y() < result)
                result = data_sheet.get_data_item(k).get_Y();
            
        return result;
    }

    private double max_Y() {
        double result = 0;
        if (data_sheet == null) return result;

        if (data_sheet.size() > 0) result = data_sheet.get_data_item(0).get_Y();
        for (int k = 1; k < data_sheet.size(); k++)
            if (data_sheet.get_data_item(k).get_Y() > result) 
                result = data_sheet.get_data_item(k).get_Y();
        
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        show_graph((Graphics2D) g);
    }

    public void show_graph(Graphics2D gr) {
        double x_min, x_max, y_min, y_max;

        double width = getWidth();
        double height = getHeight();

        x_min = min_X() - delta_X;
        x_max = max_X() + delta_X;
        y_min = min_Y() - delta_Y;
        y_max = max_Y() + delta_Y;

        double x_scale = width / (x_max - x_min);
        double y_scale = height / (y_max - y_min);
        double x0 = -x_min * x_scale;
        double y0 = y_max * y_scale;

        Paint old_color = gr.getPaint();
        gr.setPaint(Color.WHITE);

        gr.fill(new Rectangle2D.Double(0.0, 0.0, width, height));

        Stroke old_stroke = gr.getStroke();
        gr.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10, 10}, 0));
        
        Font old_font = gr.getFont();
        gr.setFont(new Font("Serif", Font.BOLD, 10));

        double x_step = 1;
        double y_step = 1;

        for (double dx = x_step; dx < x_max; dx += x_step) { calculate_step_X(gr, height, x_scale, x0, x_step, dx); }
        for (double dx = -x_step; dx >= x_min; dx -= x_step) { calculate_step_X(gr, height, x_scale, x0, x_step, dx); }
        for (double dy = y_step; dy < y_max; dy += y_step) { calculate_step_Y(gr, width, y_scale, y0, y_step, dy); }
        for (double dy = -y_step; dy >= y_min; dy -= y_step) { calculate_step_Y(gr, width, y_scale, y0, y_step, dy); }

        gr.setPaint(Color.BLACK);
        gr.setStroke(new BasicStroke(3.0f));

        gr.draw(new Line2D.Double(x0, 0, x0, height));
        gr.draw(new Line2D.Double(0, y0, width, y0));
        gr.drawString("X", (int) width - 10, (int) y0 - 2);
        gr.drawString("Y", (int) x0 + 2, 10);

        if (data_sheet != null) {
            if (!is_connected) {
                for (int k = 0; k < data_sheet.size(); k++) {
                    double x = x0 + (data_sheet.get_data_item(k).get_X() * x_scale);
                    double y = y0 - (data_sheet.get_data_item(k).get_Y() * y_scale);

                    gr.setColor(Color.white);
                    gr.fillOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);

                    gr.setColor(color);
                    gr.drawOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                }
            } else {
                gr.setPaint(color);
                gr.setStroke(new BasicStroke(2.0f));

                double x_old = x0 + data_sheet.get_data_item(0).get_X() * x_scale;
                double y_old = y0 - data_sheet.get_data_item(0).get_Y() * y_scale;

                for (int k = 1; k < data_sheet.size(); k++) {
                    double x = x0 + data_sheet.get_data_item(k).get_X() * x_scale;
                    double y = y0 - data_sheet.get_data_item(k).get_Y() * y_scale;

                    gr.draw(new Line2D.Double(x_old, y_old, (double) x, y));

                    x_old = x;
                    y_old = y;
                }
            }

            gr.setPaint(old_color);
            gr.setStroke(old_stroke);
            gr.setFont(old_font);
        }
    }

    private void calculate_step_Y(Graphics2D gr, double width, double y_scale, double y0, double y_step, double dy) {
        double y = y0 - dy * y_scale;

        gr.setPaint(Color.LIGHT_GRAY);
        gr.draw(new Line2D.Double(0, y, width, y));
        
        gr.setPaint(Color.BLACK);
        gr.drawString(Math.round(dy / y_step) * y_step + "", 2, (int) y - 2);
    }

    private void calculate_step_X(Graphics2D gr, double height, double x_scale, double x0, double x_step, double dx) {
        double x = x0 + dx * x_scale;

        gr.setPaint(Color.LIGHT_GRAY);
        gr.draw(new Line2D.Double(x, 0, x, height));

        gr.setPaint(Color.BLACK);
        gr.drawString(Math.round(dx / x_step) * x_step + "", (int) x + 2, 10);
    }
}