/*본 프로그램은 UNIX 스케줄링 기법을 시험하기위한 시뮬레이터 입니다.
 * 입력은 파일 입력을 통해 커널모드 유무, 작업이름, 작업해야할 시간, 기본우선순위를 입력받으며
 * niceValue값을 입력 받으면 작업을 스케줄링을 시물레이션합니다.
 * Job.java = main class
 * MyFrame.java = GUI 구현
 * MakeJob = 작업 생성 클래스
 * sample.txt = 입력할 파일 
 */

package unix;

import java.io.*;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Job extends MyFrame {								//메인 클래스
	static MyFrame a= new MyFrame();
	
	public static void operate(MakeJob a) {						//프로세스 후 연산 함수
		a.cputime = a.cputime/2;
		a.pri = a.basepri + a.niceValue + a.cputime/2;
	}
	
	public static void schedule(MakeJob array[]){				//프로세스 스케줄 함수
		int len = 1;;
		int j = 0;
		int i = 0;
		boolean cunal = false;									//현재 작업중 커널모드 유무 판단
		MakeJob temp = new MakeJob(true, 9999, 99999, "temp");	//최고 우선순위 비교를 위한 temp
		
		while(len != 0) {
			try {
				Thread.sleep(1000);								//1초 간격으로 정보 갱신
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			len = array.length;
			for(i = 0; i < array.length; i++) {	
				if(array[i].worktime != 0) {
					if(array[i].mode == true)					//커널모드의 잡업 발견시 
						if(array[i].pri < temp.pri) {			//최고 우선순위의 작업의 인덱스 저장
							temp.duplicate(array[i]);
							j = i;
							cunal = true;						//커널모드인 작업 발견
						}
				}
			}
			
			if(cunal != true) {									//커널모드 작업 미발견시
				for(i = 0; i < array.length; i++) {
					if(array[i].worktime != 0) {
						if(array[i].pri < temp.pri) {			//최우선순위 작업의 인덱스 저장
							temp.duplicate(array[i]);;
							j = i;
						}	
					}
				}
			}
			
			array[j].cputime = array[j].cputime + 60;			//해당 인덱스의 cpu할당
			array[j].worktime = array[j].worktime - 1;			//남은 작업시간 감소
			a.scheduleColor(array[j].color, array[j].jobName);	//GUI 구현을 위한 색 전달
			cunal = false;										//cpu를 할당할 다음 작업 선별을 위한 초기화
			temp.setMakejob(true, 9999, 99999);
	
			for(i = 0; i < array.length; i++) {
				if(array[i].worktime == 0) {					//완료된 작업 출력
					array[i].pri = 999999;
					a.addLog(array[i].jobName + "작업 완료!");
					len--;
				}
				else{											//완료되지 않은 작업 decay연산 후 출력
					operate(array[i]);
					a.addLog(array[i].jobName + "의 남은 시간 = " + array[i].worktime + " 우선순위 = " + array[i].pri + " cputime = " + array[i].cputime);
				}
			}
			a.creatBar(array, array.length);					//진행바 갱신
			j = 0;												//할당 받은 작업 인덱스 초기화
			a.addLog("\n");
		}	
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<MakeJob> arrays = new ArrayList<MakeJob>();	//파일 입력과 정보의 temp
		boolean tempmode= false;
		int temppri = 0, tempwork = 0;
		String tempname = null;
		int i = 0;
		int j = 0;
		
		try {													//sample.txt를 불러와 작업의 정보를 배열에 추가
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
		
		for( int i1 = 0 ; i1 < j; i1 ++){				//작업 정보 출력
			a.jobLog("      " + array[i1].jobName + "                       " + array[i1].basepri + "                        " + array[i1].maxtime + "                     " + array[i1].mode);
		}
		a.creatBar(array.length);						//작업 수에 따른 진행바 생성
		a.creatTextField(array.length);					//작업 수에 따른 textField생성
		
		while(a.time == false) {						//niceValue값이 textfield에 입력 후
			try {										//start버튼을 누를 때 까지 대기
				Thread.sleep(200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i1 = 0; i1 < array.length; i1++) {		//입력받은 niceValue값 적용
			array[i1].niceValue = a.niceArray.get(i1);
		}
		schedule(array);								//스케줄링 시작
		
	}
}

