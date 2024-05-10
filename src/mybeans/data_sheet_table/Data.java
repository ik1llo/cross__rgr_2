package mybeans.data_sheet_table;

import java.time.LocalDate;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data", propOrder = {
        "date",
        "x",
        "y"
})
public class Data {
    private String date;

    private double x;
    private double y;

    public Data(String date, double x, double y) {
        this.date = date;

        this.x = x;
        this.y = y;
    }

    public Data() {
        x = 0;
        y = 0;

        date = LocalDate.now().toString();
    }

    public String get_date() { return date; }

    public void set_date(String data) { this.date = data; }

    public double get_X() { return x; }

    public void set_X(double x) { this.x = x; }

    public double get_Y() { return y; }

    public void set_Y(double y) { this.y = y; }
}