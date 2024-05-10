package mybeans.data_sheet_table;

import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener {
    public void dataChanged(DataSheetChangeEvent e);
}
