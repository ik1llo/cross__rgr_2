package mybeans.data_sheet_table;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

public class DataSheetTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final int column_count = 3;
    private int row_count = 1;

    private DataSheet data_sheet = null;

    private ArrayList<DataSheetChangeListener> listener_list = new ArrayList<>();

    private DataSheetChangeEvent event = new DataSheetChangeEvent(this);

    private final String[] column_names = {"date", "X value", "Y value"};

    public DataSheetTableModel() {
        this.data_sheet = new DataSheet();
        this.row_count = data_sheet.size();
    }

    public DataSheet get_data_sheet() { return data_sheet; }

    public void set_data_sheet(DataSheet data_sheet){
        this.data_sheet = data_sheet;
        this.row_count = this.data_sheet.size();

        fire_data_sheet_change();
    }

    @Override
    public int getRowCount() { return row_count; }

    @Override
    public int getColumnCount() { return column_count; }

    @Override
    public String getColumnName(int column) { return column_names[column]; }

    @Override
    public boolean isCellEditable(int row_index, int column_index) { return column_index >= 0; }

    @Override
    public Object getValueAt(int row_index, int column_index) {
        if (data_sheet != null) {
            if (column_index == 0)
                return data_sheet.get_data_item(row_index).get_date();
            else if (column_index == 1)
                return data_sheet.get_data_item(row_index).get_X();
            else if (column_index == 2)
                return data_sheet.get_data_item(row_index).get_Y();
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int row_index, int column_index) {
        try {
            double d;

            if (data_sheet != null) {
                if (column_index == 0) { data_sheet.get_data_item(row_index).set_date((String) value); } 
                else if (column_index == 1) {
                    d = Double.parseDouble((String) value);
                    data_sheet.get_data_item(row_index).set_X(d);
                } else if (column_index == 2) {
                    d = Double.parseDouble((String) value);
                    data_sheet.get_data_item(row_index).set_Y(d);
                }
            }

            fire_data_sheet_change();
        } catch (Exception ignored) {}
    }

    public void set_row_count(int row_count) { if (row_count > 0) this.row_count = row_count; }

    public void addDataSheetChangeListener(DataSheetChangeListener l) { listener_list.add(l); }

    public void remove_data_sheet_change_listener(DataSheetChangeListener l) { listener_list.remove(l); }

    protected void fire_data_sheet_change() { for (DataSheetChangeListener dataSheetChangeListener : listener_list) dataSheetChangeListener.dataChanged(event); }
}