/*�� ���α׷��� UNIX �����ٸ� ����� �����ϱ����� �ùķ����� �Դϴ�.
 * �Է��� ���� �Է��� ���� Ŀ�θ�� ����, �۾��̸�, �۾��ؾ��� �ð�, �⺻�켱������ �Է¹�����
 * niceValue���� �Է� ������ �۾��� �����ٸ��� �ù����̼��մϴ�.
 * Job.java = main class
 * MyFrame.java = GUI ����
 * MakeJob = �۾� ���� Ŭ����
 * sample.txt = �Է��� ���� 
 */

package unix;

import java.io.*;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Job extends MyFrame {								//���� Ŭ����
	static MyFrame a= new MyFrame();
	
	public static void operate(MakeJob a) {						//���μ��� �� ���� �Լ�
		a.cputime = a.cputime/2;
		a.pri = a.basepri + a.niceValue + a.cputime/2;
	}
	
	public static void schedule(MakeJob array[]){				//���μ��� ������ �Լ�
		int len = 1;;
		int j = 0;
		int i = 0;
		boolean cunal = false;									//���� �۾��� Ŀ�θ�� ���� �Ǵ�
		MakeJob temp = new MakeJob(true, 9999, 99999, "temp");	//�ְ� �켱���� �񱳸� ���� temp
		
		while(len != 0) {
			try {
				Thread.sleep(1000);								//1�� �������� ���� ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			len = array.length;
			for(i = 0; i < array.length; i++) {	
				if(array[i].worktime != 0) {
					if(array[i].mode == true)					//Ŀ�θ���� ��� �߽߰� 
						if(array[i].pri < temp.pri) {			//�ְ� �켱������ �۾��� �ε��� ����
							temp.duplicate(array[i]);
							j = i;
							cunal = true;						//Ŀ�θ���� �۾� �߰�
						}
				}
			}
			
			if(cunal != true) {									//Ŀ�θ�� �۾� �̹߽߰�
				for(i = 0; i < array.length; i++) {
					if(array[i].worktime != 0) {
						if(array[i].pri < temp.pri) {			//�ֿ켱���� �۾��� �ε��� ����
							temp.duplicate(array[i]);;
							j = i;
						}	
					}
				}
			}
			
			array[j].cputime = array[j].cputime + 60;			//�ش� �ε����� cpu�Ҵ�
			array[j].worktime = array[j].worktime - 1;			//���� �۾��ð� ����
			a.scheduleColor(array[j].color, array[j].jobName);	//GUI ������ ���� �� ����
			cunal = false;										//cpu�� �Ҵ��� ���� �۾� ������ ���� �ʱ�ȭ
			temp.setMakejob(true, 9999, 99999);
	
			for(i = 0; i < array.length; i++) {
				if(array[i].worktime == 0) {					//�Ϸ�� �۾� ���
					array[i].pri = 999999;
					a.addLog(array[i].jobName + "�۾� �Ϸ�!");
					len--;
				}
				else{											//�Ϸ���� ���� �۾� decay���� �� ���
					operate(array[i]);
					a.addLog(array[i].jobName + "�� ���� �ð� = " + array[i].worktime + " �켱���� = " + array[i].pri + " cputime = " + array[i].cputime);
				}
			}
			a.creatBar(array, array.length);					//����� ����
			j = 0;												//�Ҵ� ���� �۾� �ε��� �ʱ�ȭ
			a.addLog("\n");
		}	
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<MakeJob> arrays = new ArrayList<MakeJob>();	//���� �Է°� ������ temp
		boolean tempmode= false;
		int temppri = 0, tempwork = 0;
		String tempname = null;
		int i = 0;
		int j = 0;
		
		try {													//sample.txt�� �ҷ��� �۾��� ������ �迭�� �߰�
			FileReader textFileReader = new FileReader("sample.txt");
			BufferedReader reader = new BufferedReader(textFileReader);
			String line = null;
			while((line = reader.readLine()) != null ) {
				if(i % 5 == 0)
					tempmode = new Boolean(line).booleanValue();
				if(i % 5 == 1)
					temppri = Integer.parseInt(line);
				if(i % 5 == 2)
					tempwork = Integer.parseInt(line);
				if(i % 5 == 3){
					tempname = line;
					arrays.add(new MakeJob(tempmode, temppri, tempwork, tempname));
					j++;
				}
				i++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		MakeJob[] array = new MakeJob[j];
		
		for(int k = 0; k <j; k++) {
			array[k] = arrays.get(k);
		}
		
		for( int i1 = 0 ; i1 < j; i1 ++){				//�۾� ���� ���
			a.jobLog("      " + array[i1].jobName + "                       " + array[i1].basepri + "                        " + array[i1].maxtime + "                     " + array[i1].mode);
		}
		a.creatBar(array.length);						//�۾� ���� ���� ����� ����
		a.creatTextField(array.length);					//�۾� ���� ���� textField����
		
		while(a.time == false) {						//niceValue���� textfield�� �Է� ��
			try {										//start��ư�� ���� �� ���� ���
				Thread.sleep(200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i1 = 0; i1 < array.length; i1++) {		//�Է¹��� niceValue�� ����
			array[i1].niceValue = a.niceArray.get(i1);
		}
		schedule(array);								//�����ٸ� ����
		
	}
}

