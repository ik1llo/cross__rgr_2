package mybeans.data_sheet_graph;

import java.beans.*;

public class DataSheetGraphBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] property_descriptors;

    public DataSheetGraphBeanInfo() {
        try {
            property_descriptors = new PropertyDescriptor[]
                    {
                            new PropertyDescriptor("color", DataSheetGraph.class),
                            new PropertyDescriptor("filled", DataSheetGraph.class),
                            new PropertyDescriptor("delta_X", DataSheetGraph.class),
                            new PropertyDescriptor("delta_Y", DataSheetGraph.class)
                    };
        } catch (IntrospectionException e) { e.printStackTrace(); }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() { return property_descriptors; }
}