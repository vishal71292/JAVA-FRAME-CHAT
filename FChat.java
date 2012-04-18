import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/*<applet code = Chat height = 700 width = 7000>
</applet>*/

public class FChat extends Frame implements KeyListener , ActionListener ,Runnable
{

  Panel p,p1,p2;
	CardLayout cl;
	Thread t;
	MenuBar mb;
	Menu file,help;
	MenuItem n,sa,c,h;
	Socket s,x;
	InputStream in,sin;
	OutputStream out,sout;
	int port1 = 2233,port2 = 2244,port;
	String ip,z;
	Label l,l1,l2;
	Button connect;
	TextField tf,tf_ip,tf_p;
	TextArea ta;
	ServerSocket ss ;
	FChat()
	{
	InetAddress Address;
		cl = new CardLayout();
		p = new Panel();
		p1 = new Panel();
		p2 = new Panel();
		p.setLayout(cl);
		add(p);
		p.add(p1,"init");
		p.add(p2,"chat");
		mb = new MenuBar();
		setMenuBar(mb);
		file = new Menu("FILE");
		help = new Menu("HELP");
		mb.add(file);
		mb.add(help);
		n = new MenuItem("NEW");
		sa = new MenuItem("SAVE");
		c = new MenuItem("CLOSE");
		h = new MenuItem("HELP");
		file.add(n);
		file.add(sa);
		file.add(c);
		help.add(h);
		try{Address = InetAddress.getLocalHost();
		System.out.println(Address);
		l = new Label("ENTER THE IP");
		l1 = new Label("ENTER THE PORT");
		l2 = new Label("SYSTEMS IP is  "+String.valueOf(Address)+"\n  PORT IS  2233");
		}catch(Exception e){}
		connect = new Button("CONNECT");
		t = new Thread(this);
		tf = new TextField(20);
		tf_ip = new TextField(20);tf_p = new TextField(20);
		ta = new TextArea(50,100);
		tf.addKeyListener(this);
		addWindowListener(new X());
		setSize(1500,500);
		setVisible(true);
		setLayout(new FlowLayout());
		p1.add(l2);
		p1.add(l);
		p1.add(tf_ip);
		p1.add(l1);
		p1.add(tf_p);
		
		p1.add(connect);
		
		p2.add(tf);
		p2.add(ta);
		connect.addActionListener(this);
		
		
	}
	
	public void run()
	{
		System.out.println("IN RUn");
		int c;
		try
	{
	ss = new ServerSocket(port1);
	}catch(Exception v){System.out.println("ERROR IN CREATING A SERVER SS\n"+v);}	
		try{
		x = ss.accept();
		z = String.valueOf(x.getInetAddress());
		System.out.println("CREATED A SERVER SOCKET 1");
		sin = x.getInputStream();
		sout = x.getOutputStream();
		}catch(Exception m){System.out.println("ERROR IN CREATING A SERVER SOCKET 2\n"+m);}
		while(true){
		try{ta.append(z+"SAYS   :");
		while ((c = sin.read()) != -1) 
		{
		if((char) c == '\n')
		{
			ta.append("SAID BY:"+z);
		}
		ta.append((char) c+"");
		
		}}
		
		catch(Exception e){}
	}}
	
	public void keyReleased(KeyEvent ke)
	{
	}
	public void keyTyped(KeyEvent ke)
	{
	}
	public void keyPressed(KeyEvent ke)
	{

		try{
		if(ke.getKeyCode() == ke.VK_ENTER)
		{
			ta.append("\n"+"YOU  :"+tf.getText()+"\n");
			
			int x = 0,i=0;
			byte buf[] = tf.getText().getBytes();
			x = tf.getText().length();
			while(i < x)
			{
				out.write(buf[i]);
				i++;
			}
			out.write('\n');
			tf.setText("");
		}
		}catch(Exception e){}
		
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		ip = tf_ip.getText();
		port = Integer.parseInt(tf_p.getText());
			if(ae.getActionCommand() == "CONNECT")
			{
				
				System.out.println("RUN CREATED");
				t.start();
				int x = 1;
			while(x != 0)
			{
			try{
				s = new Socket(ip,port);
				in = s.getInputStream();
				out = s.getOutputStream();
				System.out.println("CREATED A CLIENT SOCKET\n");
				cl.show(p,"chat");
				x = 0;
				}
				catch(Exception e)
				{
				System.out.println("ERROR IN CREATING A CLIENT SOCKET\n"+e);
				}
			}	
				
			}
	}	
	
	public static void main(String ar[])
	{
		FChat f = new FChat();
	}
		
		
	}
	
	class X extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			System.exit(0);
		}
	}