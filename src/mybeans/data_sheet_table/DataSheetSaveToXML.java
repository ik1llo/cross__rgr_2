package mybeans.data_sheet_table;

import javax.xml.bind.*;

import java.io.File;

public class DataSheetSaveToXML {
    public static void marshal_data_to_XML(String fileName, DataSheet data_sheet) throws JAXBException {
        Marshaller marshaller = JAXBContext.newInstance(DataSheet.class).createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(data_sheet, new File(fileName));
    }

    public static DataSheet unmarshal_out_XML(String filepath) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(DataSheet.class).createUnmarshaller();

        Object element = unmarshaller.unmarshal(new File(filepath));
        if (element instanceof DataSheet) {
            return (DataSheet) element;
        } else throw new JAXBException("it's not DataSheet, check the correctness of the file path...");
    }
}