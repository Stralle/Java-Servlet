package paket;

public class Contact {
	private String name;
	private String contact;
	
	public Contact(String n, String c) {
		this.setName(n);
		this.setContact(c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.contact;
	}
	
	
}
