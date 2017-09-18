/*작업을 생성하는 클래스로서 커널모드 유무, 이름, 기본우선순위, cputime, 작업 시간, 색 등의 정보를
 * 가진다.
 */

package unix;

import java.awt.Color;

class MakeJob {
	public boolean mode;									//커널모드 유루
	public int pri, basepri, worktime, cputime, maxtime, niceValue;	//기본우선순위, 현재우선순위, 남은시간, cputime, 총 시간
	public String jobName;									//작업 이름
	public Color color;										//작업의 색
	public MakeJob(){}
	
	public MakeJob(boolean mode, int pri, int worktime, String jobName) {		//생성자
		this.mode = mode;
		this.pri = pri;
		this.worktime = worktime;
		this.jobName = jobName;
		maxtime = worktime;
		basepri = pri;
		cputime = 0;
		niceValue = 0;
		color = new Color((int) (Math.random() * 255.0),						//작업의 색은 랜덤하게 배정
				(int) (Math.random() * 255.0), (int) (Math.random() * 255.0));
	}
	public void setMakejob(boolean mode, int pri, int worktime) {				//설정자
		this.mode = mode;
		this.pri = pri;
		this.worktime = worktime;
	}
	public void duplicate(MakeJob a){											//작업 복사 함수
		this.mode = a.mode;
		this.pri = a.pri;
		this.worktime = a.worktime;
		this.jobName = a.jobName;
		this.cputime = a.cputime;
		maxtime = worktime;
	}
}