package socket.netty.http.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {  
    private long orderNumber;  
  
    private Customer customer;  
  
    private String billTo;  
  
    private String shipping;  
  
    private String shipTo;  

    private Float total;

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", customer=" + customer + ", billTo=" + billTo + ", shipping=" + shipping + ", shipTo=" + shipTo + ", total=" + total + "]";
	}

}