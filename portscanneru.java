import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Portscanneru extends Frame
{
	static boolean InSufficientDataForScan = false;
	DatagramSocket DSOCK;
	int PORT_START;
	int PORT_END;
	int PortStart;
	int PortEnd;
	String TARGET;

	Frame MainWindow = new Frame("port scanner");
	

	JLabel L_Title = new JLabel("Port Scanner",JLabel.CENTER);
	JLabel L_TARGET = new JLabel("Target");
	JLabel L_port_Start = new JLabel("Starting port:");
	JLabel L_port_End = new JLabel("Ending port:");
	JLabel L_Status = new JLabel("SCAN TO PROCEED", JLabel.CENTER);

	JTextField TF_TARGET = new JTextField(15);
	JTextField TF_PORT_START = new JTextField(15);
	JTextField TF_PORT_END = new JTextField(15);

	JTextArea TA_Message = new JTextArea();

	JScrollPane SP_Message = new JScrollPane ();

	JButton B_Scan = new JButton("SCAN");
	JButton b_Reset =new JButton("RESET");


	public static void main(String args[]){
 		new Portscanneru();
	}


	public Portscanneru(){
		BuildGUI();
	}


public void BuildGUI()
{
	GridLayout g1=new GridLayout(5,5);
	MainWindow.setLayout(g1);
	MainWindow.setSize(600,600);
	MainWindow.setBackground(new Color(255,229,204));
	MainWindow.setResizable(true);


	GridLayout ug=new GridLayout(5,1);
	Panel UpperPanel =new Panel (ug);
	ug.setVgap(5);	
	L_Title.setForeground(Color.black);
	L_Title.setFont(new Font("TimesRoman",Font.BOLD,30));
	MainWindow.add(L_Title);


	L_TARGET.setForeground(Color.black);
	
	L_TARGET.setFont(new Font("TimesRoman",Font.BOLD,22));
	UpperPanel.add(L_TARGET);
	
	TF_TARGET.setForeground(Color.black);
	TF_TARGET.setFocusable(true);
	TF_TARGET.requestFocus();
	UpperPanel.add(TF_TARGET);


	L_port_Start.setForeground(Color.black);
	L_port_Start.setFont(new Font("TimesRoman",Font.BOLD,22));
	UpperPanel.add(L_port_Start);


	TF_PORT_START.setForeground(Color.black);
	TF_PORT_START.setFocusable(true);
	UpperPanel.add(TF_PORT_START);

	L_port_End.setForeground(Color.black);
	L_port_End.setFont(new Font("TimesRoman",Font.BOLD,22));
	UpperPanel.add(L_port_End);


	TF_PORT_END.setForeground(Color.black);
	TF_PORT_END.setFocusable(true);
	UpperPanel.add(TF_PORT_END);


	MainWindow.add(UpperPanel);

	Panel LowerPanel = new Panel();
	JLabel SPACER = new JLabel();
	LowerPanel.add(B_Scan);
	LowerPanel.add(SPACER);
	LowerPanel.add(b_Reset);
	MainWindow.add(LowerPanel);


	SP_Message.setViewportView(TA_Message);
	SP_Message.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	SP_Message.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	MainWindow.add(SP_Message);


	L_Status.setForeground(Color.black);
	L_Status.setFont(new Font("TimesRoman",Font.BOLD,22));
	MainWindow.add(L_Status);
	AddEventHandlers();
	MainWindow.setVisible(true);
	MainWindow.repaint();
	
	}

	public void AddEventHandlers(){
		MainWindow.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});

		B_Scan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent r){
				B_SCAN_ACTION();
			}
		});

		b_Reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent t){
				B_RESET_ACTION();
			}
		});
	}
	
	public void B_SCAN_ACTION(){
		if(TF_TARGET.getText().equals("")){
			TA_Message.setText("ENTER TARGET.");
				return;
		}
		else if(TF_PORT_START.getText().equals("")){
			TA_Message.setText("ENTER START PORT.");
				return;
		}
		else if(TF_PORT_END.getText().equals("")){
			TA_Message.setText("ENTER END PORT.");
				return;
		}
 		InSufficientDataForScan = false;
		TA_Message.setText("");
		Thread X1 = new Thread(){
			public void run(){
				B_Scan.setEnabled(false);
				b_Reset.setText("stop");
				TARGET =TF_TARGET.getText();
				PortStart = Integer.parseInt(TF_PORT_START.getText());
				PortEnd = Integer.parseInt(TF_PORT_END.getText());
			     for(int x=PortStart;x<=PortEnd;x++){
					L_Status.setText("Port "+x+" is being tested");
						if( InSufficientDataForScan)
							break;
						try{
							
							DSOCK =new DatagramSocket(x,InetAddress.getByName(TARGET));
							TA_Message.append("port"+x+"is opened.\n");
							DSOCK.close();
						}
						catch(Exception X){
							TA_Message.append("port "+x+" is closed.\n");
							continue;
						}
				}
				B_Scan.setEnabled(true);	
				b_Reset.setText("RESET");
				L_Status.setText("press scan to start.");
			}
		};
		X1.start();
	}


	public void B_RESET_ACTION(){
		InSufficientDataForScan = true;
		TF_TARGET.setText("");
		TF_PORT_START.setText("");
		TF_PORT_END.setText("");
		TA_Message.setText("");
		b_Reset.setText("STOP");
		}
	}
