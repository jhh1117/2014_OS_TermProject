/*�۾��� �����ϴ� Ŭ�����μ� Ŀ�θ�� ����, �̸�, �⺻�켱����, cputime, �۾� �ð�, �� ���� ������
 * ������.
 */

package unix;

import java.awt.Color;

class MakeJob {
	public boolean mode;									//Ŀ�θ�� ����
	public int pri, basepri, worktime, cputime, maxtime, niceValue;	//�⺻�켱����, ����켱����, �����ð�, cputime, �� �ð�
	public String jobName;									//�۾� �̸�
	public Color color;										//�۾��� ��
	public MakeJob(){}
	
	public MakeJob(boolean mode, int pri, int worktime, String jobName) {		//������
		this.mode = mode;
		this.pri = pri;
		this.worktime = worktime;
		this.jobName = jobName;
		maxtime = worktime;
		basepri = pri;
		cputime = 0;
		niceValue = 0;
		color = new Color((int) (Math.random() * 255.0),						//�۾��� ���� �����ϰ� ����
				(int) (Math.random() * 255.0), (int) (Math.random() * 255.0));
	}
	public void setMakejob(boolean mode, int pri, int worktime) {				//������
		this.mode = mode;
		this.pri = pri;
		this.worktime = worktime;
	}
	public void duplicate(MakeJob a){											//�۾� ���� �Լ�
		this.mode = a.mode;
		this.pri = a.pri;
		this.worktime = a.worktime;
		this.jobName = a.jobName;
		this.cputime = a.cputime;
		maxtime = worktime;
	}
}