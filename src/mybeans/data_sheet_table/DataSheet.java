package mybeans.data_sheet_table;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTPListType", propOrder = {
        "data"
})
public class DataSheet {
    private List<Data> data;

    public DataSheet() { data = new ArrayList<>(); }

    public void add_data_item(Data item) { data.add(item); }

    public Data get_data_item(int pos) { return data.get(pos); }

    public void remove_data_item(int pos) { data.remove(pos); }

    public int size() { return data.size(); }
}