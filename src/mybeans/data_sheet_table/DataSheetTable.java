package mybeans.data_sheet_table;

import javax.swing.*;

public class DataSheetTable {
    private JButton add_btn;
    private JButton del_btn;

    private JScrollPane scroll_pane;

    private JTable table;

    private JPanel root_pane;

    private DataSheetTableModel table_model;

    private void createUIComponents() {
        this.scroll_pane = new JScrollPane();
        this.scroll_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.table_model = new DataSheetTableModel();

        this.table = new JTable();
        this.table.setModel(this.table_model);

        this.add_btn = new JButton();
        this.add_btn.addActionListener(e -> {
            this.table_model.set_row_count(this.table_model.getRowCount() + 1);
            this.table_model.get_data_sheet().add_data_item(new Data());
            
            this.table.revalidate();
            this.table_model.fire_data_sheet_change();
        });

        this.del_btn = new JButton();
        this.del_btn.addActionListener(e -> {
            if (this.table_model.getRowCount() > 1) {
                this.table_model.set_row_count(this.table_model.getRowCount() - 1);
                this.table_model.get_data_sheet().remove_data_item(this.table_model.get_data_sheet().size() - 1);

                this.table.revalidate();
            }

            this.table_model.fire_data_sheet_change();
        });
    }

    public DataSheetTableModel getTableModel() { return this.table_model; }

    public void setTableModel(DataSheetTableModel table_model) {
        this.table_model = table_model;
        this.table.revalidate();
    }

    public void revalidate() { if (table != null) this.table.revalidate(); }
}