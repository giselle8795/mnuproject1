package project1;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FileDialog;
import javax.swing.filechooser.*;
import javax.swing.JOptionPane;
import java.io.*;

public class Project1 extends JFrame {

	private JFrame frmJava;
	private JPanel contentPane;
	private JTextArea textArea_1;
	String copyText;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project1 frame = new Project1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Cmd cmd = new Cmd();
	private JTextArea textArea_2;
	public Project1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 421, 476);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTextArea_1().setText("");
				getTextArea_2().setText("");
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(frmJava,"저장",FileDialog.SAVE );
		        dialog.setDirectory(".");
		        dialog.setVisible(true);
		        if(dialog.getFile() == null) return;
		        String dfName = dialog.getDirectory() + dialog.getFile()+".java";
		        try {
		        	BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
		        	writer.write(textArea_1.getText());
		        	writer.close();
		        	
		        }catch(Exception e2) {
		        	JOptionPane.showMessageDialog(frmJava,"저장 오류");
		        }    
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Open");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("Open a File");
				fs.setFileFilter(new FileTypeFilter(".txt", "Text File"));
				fs.setFileFilter(new FileTypeFilter(".dat", "Data File"));
				fs.setFileFilter(new FileTypeFilter(".hwp", "한글 파일"));
				fs.setFileFilter(new FileTypeFilter(".java", "Java File"));
				int result = fs.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = fs.getSelectedFile();
					try {
						BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
						String line = "";
						String s = "";
						while((line = br.readLine()) != null) {
							s += line + "\n";
						}
						textArea_1.setText(s);
						if (br != null)
							br.close();
					}
					catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("EXIT");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Editor");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Copy");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				copyText = textArea_1.getSelectedText();

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Paste");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int position = textArea_1.getCaretPosition();
                textArea_1.insert(copyText, position);

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Cut");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int start = textArea_1.getSelectionStart();
                 int end = textArea_1.getSelectionEnd();
                 textArea_1.replaceRange("",start,end);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_8);
		
		JMenu mnNewMenu_2 = new JMenu("Compiler");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Compile");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] findname = textArea_1.getText().split(" ");
				String name = null;
				for (int i = 0; i<findname.length; i++) {
					if(findname[i].equals("class") == true) {
						name = findname[i+1];
						if(name.indexOf("{") != -1) {
							name.replace("{", "");
						}
						cmd.FILENAME = name+".java";
					}
				}
				String[] code = textArea_1.getText().split("\n");
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i<code.length; i++) {
					buffer.append(code[i]);
				}
				String command = cmd.inputSource(buffer.toString());
				String result = cmd.execCommand(command);
				
				textArea_2.append(result);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Run");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = cmd.Run();
				textArea_2.append(result);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		textArea_1 = new JTextArea();
		textArea_1.setRows(13);
		scrollPane.setViewportView(textArea_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		textArea_2 = new JTextArea();
		scrollPane_1.setViewportView(textArea_2);
	}

	public JTextArea getTextArea_1() {
		return textArea_1;
	}
	public JTextArea getTextArea_2() {
		return textArea_2;
	}
}
