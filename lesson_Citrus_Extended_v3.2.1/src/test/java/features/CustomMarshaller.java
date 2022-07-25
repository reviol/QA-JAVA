package features;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.OutputStream;

public class CustomMarshaller<T> {
    public T generic;

    public String convert(T requestClass, Object requestData, String namespaseURI, String localPart) { //namespaseURI=http://oms.rt.ru/ , localPart=submitOrderRequest
        generic = requestClass;
        String s = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((Class) requestClass);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // To format XML
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            JAXBElement<T> jaxbElement =
                    new JAXBElement<T>(new QName(namespaseURI, localPart),
                            (Class) requestClass,
                            (T) requestData);

            OutputStream output = new OutputStream() {
                private StringBuilder string = new StringBuilder();
                @Override
                public void write(int b) throws IOException {
                    this.string.append((char) b );
                }

                public String toString(){
                    return this.string.toString();
                }
            };
            jaxbMarshaller.marshal(jaxbElement, output);
            s = String.valueOf(output);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return s;
    }
}