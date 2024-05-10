import mybeans.data_sheet_graph.DataSheetGraph;
import mybeans.data_sheet_table.Data;
import mybeans.data_sheet_table.DataSheet;
import mybeans.data_sheet_table.DataSheetSaveToXML;
import mybeans.data_sheet_table.DataSheetTable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.IOException;
import javax.xml.bind.JAXBException;

public class Application extends JFrame {
    private final JFileChooser file_chooser = new JFileChooser();
    private JPanel content_pane;

    private DataSheet data_sheet;
    private DataSheetGraph data_sheet_graph;
    private DataSheetTable data_sheet_table;

    private JButton read_btn;
    private JButton save_btn;
    private JButton clear_btn;
    private JButton exit_btn;

    public Application() {
        setContentPane(this.content_pane);

        this.data_sheet = new DataSheet();
        this.data_sheet.add_data_item(new Data());
        this.data_sheet_graph.set_data_sheet(this.data_sheet);
        this.data_sheet_table.getTableModel().set_data_sheet(this.data_sheet);

        this.file_chooser.setCurrentDirectory(new java.io.File("."));

        exit_btn.addActionListener(e -> dispose() );

        clear_btn.addActionListener(e -> {
            this.data_sheet = new DataSheet();
            this.data_sheet.add_data_item(new Data());

            this.data_sheet_table.getTableModel().set_data_sheet(data_sheet);
            this.data_sheet_table.revalidate();

            this.data_sheet_graph.set_data_sheet(data_sheet);
        });

        save_btn.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == file_chooser.showSaveDialog(null)) {
                String filename = "";
                
                try {
                    filename = file_chooser.getSelectedFile().getCanonicalPath();

                    DataSheetSaveToXML.marshal_data_to_XML(filename, this.data_sheet);
                    JOptionPane.showMessageDialog(null, filename.trim() + " saved!", "saved", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (JAXBException err) { JOptionPane.showMessageDialog(null, filename.trim() + " not saved...", "not saved", JOptionPane.INFORMATION_MESSAGE); }
                catch (IOException err) { JOptionPane.showMessageDialog(null, filename.trim() + " not saved...", "not saved", JOptionPane.INFORMATION_MESSAGE); }
            }
        });

        read_btn.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == file_chooser.showOpenDialog(null)) {
                String filename = "";

                try {
                    filename = file_chooser.getSelectedFile().getCanonicalPath();
                    this.data_sheet = DataSheetSaveToXML.unmarshal_out_XML(filename);
                }
                catch (JAXBException err) { JOptionPane.showMessageDialog(null, filename.trim() + " not saved...", "not saved", JOptionPane.INFORMATION_MESSAGE); }
                catch (IOException err) { JOptionPane.showMessageDialog(null, filename.trim() + " not saved...", "not saved", JOptionPane.INFORMATION_MESSAGE); }

                this.data_sheet_table.getTableModel().set_data_sheet(this.data_sheet);
                this.data_sheet_table.revalidate();
                this.data_sheet_table.revalidate();

                this.data_sheet_graph.set_data_sheet(this.data_sheet);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); }
        });

        data_sheet_table.getTableModel().addDataSheetChangeListener(e -> {
            data_sheet_graph.revalidate();
            data_sheet_graph.repaint();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Application frame = new Application();

                frame.pack();
                frame.setVisible(true);
            } catch (Exception e) { e.printStackTrace(); }
        });
    }
}