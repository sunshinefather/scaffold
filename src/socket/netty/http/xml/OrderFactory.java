package socket.netty.http.xml;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;

public class OrderFactory {
    public static Order create(long orderID) {  
        Order order = new Order();  
        order.setOrderNumber(orderID);  
        order.setTotal(9999.999f);  
        order.setBillTo("中国南京市江苏省龙眠大道");  
        Customer customer = new Customer();  
        customer.setCustomerNumber(orderID);  
        customer.setFirstName("李");  
        customer.setLastName("林峰");  
        order.setCustomer(customer);  
        order.setShipping("顺丰快递");  
        order.setShipTo("中国南京市江苏省龙眠大道");  
        return order;  
    } 
    public static void main(String[] args) {
    	JAXBContext jbt;
		try {
			jbt = JAXBContext.newInstance(Order.class);
	    	StringWriter write = new StringWriter();
	    	jbt.createMarshaller().marshal(OrderFactory.create(123),write);
	    	System.out.println(write.toString());
	    	StringReader reader = new StringReader(write.toString());
	    	Order order = (Order)jbt.createUnmarshaller().unmarshal(reader);
	    	System.out.println(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}