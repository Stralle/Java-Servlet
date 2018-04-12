package paket;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import paket.Contact;

public class ServerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<Contact> contacts = new ArrayList<>();
	private String ime;
	private String prezime;
	private ArrayList<HttpSession> sessions = new ArrayList<>();
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ServerServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//pokupimo session objekat
		HttpSession session = req.getSession(true);
		
		//probamo da pokupimo brojac pristupa ovoj sesiji
		SessionCounter sc = (SessionCounter)session.getAttribute("brojac");
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<HTML>");
		
		//ispisi id sesije
		//out.println("<B>Sesija ID:"+session.getId()+"</B>");
		
		/*
		 * Ako je getAttribute vratio null, onda je ovo prvi pristup stranici,
		 * inace je u pitanju neki sledeci.
		 */

		out.println("<HEAD><title>Domaci4</title></HEAD>");

		String submit = req.getParameter("submit");
		
		if(sc == null && !sessions.contains(session)){
			//sc.inc();
			out.println("<BODY>");
			
			out.println("<div> <h1>Jednostavna forma</h1>");
					
			out.println("<div> <form method=\"get\" action=\"http://localhost:8080/Domaci4/ServerServlet\">"
						+ "<label for=\"Ime\">Ime:</label>"
						+ "<input type=\"text\" name=\"ime\" id=\"ime\" />"
						+ "<br>"
						+ "<label for=\"Prezime\">Prezime:</label>"
						+ "<input type=\"text\" name=\"prezime\" id=\"prezime\" />"
						+ "<input type=\"submit\" name=\"submit\" value=\"Potvrda\" />"
						+ "</form>"
						+ "<div style=\"clear: both;\"></div>"
						+ "</div>"
						+ "</div>");
			out.println("</BODY>");
			
			sc = new SessionCounter();
			sc.inc();
			session.setAttribute("brojac", sc);
			sessions.add(session);
			
			//out.println(", ukupno pristupa:"+sc.getCount()+". <BR />");
		}
		else{
			
			ime = req.getParameter("ime");
			prezime = req.getParameter("prezime");
			String kontakt = req.getParameter("kontakt");
			String broj = req.getParameter("broj");
			submit = req.getParameter("submit");
			
			if(submit != null && submit.equals("Potvrda")) {
				session.setAttribute("ime", ime);
				session.setAttribute("prezime", prezime);
			}
			
			ime = (String) session.getAttribute("ime");
			prezime = (String) session.getAttribute("prezime");

			out.println("<body>");
			
			if(submit != null && submit.equals("Dodaj")) {
				contacts.add(new Contact(kontakt, broj));
				out.println("Uneti kontakt je: " + kontakt + " " + broj);
			}
			
			
			out.println("<h2>Dobro nam dosli " + ime + " " + prezime + "</h2>");
			
			out.println("<h4>Ubacite novi kontakt</h4>");
			
			out.println("<div> <form method=\"get\" action=\"http://localhost:8080/Domaci4/ServerServlet\">"
					+ "<label for=\"Ime\">Ime i prezime:</label>"
					+ "<input type=\"text\" name=\"kontakt\" id=\"kontakt\" />"
					+ "<label for=\"Kontakt\">Kontakt telefon:</label>"
					+ "<input type=\"text\" name=\"broj\" id=\"broj\" />"
					+ "<input type=\"submit\" name=\"submit\" value=\"Dodaj\" />"
					+ "</form>"
					+ "</div>"
					+ "</div>");
			
			if(contacts.size() > 0) 
				out.println(listAllContactsHTML());
			else {
				out.println("<p style=\"color:red\">Ubacite nove kontakte i oni ce se prikazati ovde!</p>");
			}
			out.println("</body>");
			out.close();
			
			
		}
		
		out.println("</HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	public synchronized static String listAllContactsHTML() {
		String s = "";
		s += "<table>";
		for(Contact c: contacts) {
			s += "<tr>\n";
			s += "<td>" + c + "</td>\n";
			s += "</tr>\n";
			
		}
		s += "</table>";
		
		return s;
	}
	
}
