/*GUI를 구현한 클래스로서 작업의 정보, 진행바, nicevalue값 입력부분, 버튼, 
 * 하단의 cpu의 할당된 프로세스를 구현
 */

package unix;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;

@SuppressWarnings("serial")
class MyFrame extends JFrame {
	private JLabel lblUnixSchedulingSimulator;
	private JLabel lblNicevalue;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel label_2;
	private JLabel lblNewLabel;
	private JLabel label_3;
	private JLabel lblNewLabel_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane1;
	private JTextArea textArea;
	private JTextArea textArea_1;
	public JProgressBar[] progress;
	public JTextField[] textFiled;
	ArrayList<Color> colArray = new ArrayList<Color>();
	ArrayList<String> strArray = new ArrayList<String>();
	ArrayList<Integer> niceArray = new ArrayList<Integer>();
	public boolean time = false;
	public int joblen = 0;
	Thread th = new Thread();
	
	public void addLog(String log) {										//프로세스의 정보를 출력
		textArea.append(log + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	public void jobLog(String log1) {										//작업의 정보 출력
		textArea_1.append(log1 + "\n");
		textArea_1.setCaretPosition(textArea_1.getDocument().getLength());
	}
	
	public void creatBar(int size) {										//진행바 생성 함수
		progress = new JProgressBar[size];
		joblen = size;
		for(int i = 0; i < size; i++) {
			progress[i] = new JProgressBar();
			progress[i].setBounds(0, 30*i, 130, 20);
			panel_3.add(progress[i]);
			progress[i].setStringPainted(true);
		}
	}
	
	public void creatTextField(int size) {									//TextField생성 함수
		textFiled = new JTextField[size];
		for(int i = 0; i < size; i++) {
			textFiled[i] = new JTextField();
			textFiled[i].setBounds(40, 30*i, 60, 20);
			panel.add(textFiled[i]);
			textFiled[i].setColumns(10);
		}
	}
	
	public void creatBar(MakeJob[] job, int size) {							//진행바 갱신함수
		for(int i = 0; i < size; i++) {
			progress[i].setValue((job[i].maxtime - job[i].worktime) * 100 / job[i].maxtime);
			progress[i].setStringPainted(true);
		}
		repaint();
	}
	public void scheduleColor(Color c, String jobName) {					//cpu의 할당된 프로세스의 색과 이름 저장 함수
		colArray.add(c);
		strArray.add(jobName);
	}
	public void paint(Graphics g)											//cpu를 할당 받은 프로세스의 시각적인 정보 출력
    {
		super.paintComponents(g);
        for (int i = 0; i < colArray.size(); i++)//count수만큼 반복해서 사각형 그려주기
        {
        	g.setColor(colArray.get(i));
        	g.drawString(strArray.get(i), 20 + 20 * i, 505);
            g.fillRect(20 + 20 * i, 510 , 10, 70);	//위치 조절 꼭 하자!
        }
    }

	public MyFrame() {		
		setSize(1150,600);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lblUnixSchedulingSimulator = new JLabel("UNIX Scheduling Simulator");
		lblUnixSchedulingSimulator.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblUnixSchedulingSimulator.setBounds(415, -19, 337, 67);
		getContentPane().add(lblUnixSchedulingSimulator);
		
		lblNicevalue = new JLabel("niceValue");
		lblNicevalue.setBounds(1025, 55, 57, 15);
		getContentPane().add(lblNicevalue);
		final JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {		//niceValue값 입력에 따른 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnStart) {
					for(int i = 0; i<joblen; i++){ 
						String value = textFiled[i].getText();
						niceArray.add(Integer.parseInt(value));
					}
					time = true;
				}
			}
		});
		
		btnStart.setBounds(1003, 6, 97, 23);
		getContentPane().add(btnStart);
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 117, 324, 345);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		textArea_1 = new JTextArea();
		scrollPane1 = new JScrollPane(textArea_1);
		scrollPane1.setBounds(0, 0, 324, 345);
		panel_1.add(scrollPane1);
			
		panel_2 = new JPanel();
		panel_2.setBounds(348, 117, 481, 345);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
				
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, 481, 345);
		panel_2.add(scrollPane);
		
		panel_3 = new JPanel();
		panel_3.setBounds(841, 117, 131, 345);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblD = new JLabel("\uC791\uC5C5\uC9C4\uD589\uB3C4");
		lblD.setBounds(869, 55, 71, 15);
		getContentPane().add(lblD);
		
		JLabel label = new JLabel("\uC791\uC5C5\uC815\uBCF4");
		label.setBounds(538, 55, 57, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\uC791\uC5C5\uBAA9\uB85D");
		label_1.setBounds(159, 55, 57, 15);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("\uC791\uC5C5\uC774\uB984");
		label_2.setBounds(12, 92, 57, 15);
		getContentPane().add(label_2);
		
		lblNewLabel = new JLabel("\uAE30\uBCF8\uC6B0\uC120\uC21C\uC704");
		lblNewLabel.setBounds(81, 92, 78, 15);
		getContentPane().add(lblNewLabel);
		
		label_3 = new JLabel("\uC791\uC5C5\uC2DC\uAC04");
		label_3.setBounds(181, 92, 57, 15);
		getContentPane().add(label_3);
		
		lblNewLabel_1 = new JLabel("\uCEE4\uB110\uBAA8\uB4DC");
		lblNewLabel_1.setBounds(259, 92, 77, 15);
		getContentPane().add(lblNewLabel_1);
		
		panel = new JPanel();
		panel.setBounds(984, 117, 138, 345);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		setVisible(true);
	}
}