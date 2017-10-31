package P13458;

import java.util.Scanner;

/**
 * Created by K on 2017-04-09.
 */
public class Main {
	public static void main(String agrs[]) {

		Scanner scanner = new Scanner(System.in);
		int classNum = 0;
		int[] studentNum;
		int profOverseeNum;
		int secondProfOverseeNum;
		if (scanner.hasNextInt())
			classNum = scanner.nextInt();
		else
			return;
		if (1 > classNum || classNum > 1000000)
			return;
		studentNum = new int[classNum];
		for (int i = 0; i < classNum; i++) {
			if (scanner.hasNextInt())
				studentNum[i] = scanner.nextInt();
			else
				return;
			if (1 > studentNum[i] || studentNum[i] > 1000000)
				return;
		}
		if (scanner.hasNextInt())
			profOverseeNum = scanner.nextInt();
		else
			return;
		if (1 > profOverseeNum || profOverseeNum > 1000000)
			return;
		if (scanner.hasNextInt())
			secondProfOverseeNum = scanner.nextInt();
		else
			return;
		if (1 > secondProfOverseeNum || secondProfOverseeNum > 1000000)
			return;
		InputHandler inputHandler = new InputHandler(classNum, studentNum,
				profOverseeNum, secondProfOverseeNum);
		if (inputHandler.checkInputs() == true) {
			inputHandler.execProcess();
			System.out.print(inputHandler.getResultNum());
		}
		return;
	}
}

class InputHandler {
	public int classNum;
	public int[] studentNum;
	public int profOverseeNum;
	public int secondProfOverseeNum;
	public long resultNum;

	public long getResultNum() {
		return resultNum;
	}

	public InputHandler(int classNum, int[] studentNum, int profOverseeNum,
			int secondProfOverseeNum) {
		this.classNum = classNum;
		this.studentNum = studentNum;
		this.profOverseeNum = profOverseeNum;
		this.secondProfOverseeNum = secondProfOverseeNum;
	}

	public boolean checkInputs() {
		if (1 > classNum || classNum > 1000000)
			return false;
		for (int temp : studentNum) {
			if (1 > temp || temp > 1000000)
				return false;
		}
		if (1 > profOverseeNum || profOverseeNum > 1000000)
			return false;
		if (1 > secondProfOverseeNum || secondProfOverseeNum > 1000000)
			return false;
		return true;
	}

	public void execProcess() {
		this.resultNum = 0;
		for (int i = 0; i < classNum; i++) {
			resultNum++;
			int remainStNum = studentNum[i] - profOverseeNum;
			if (remainStNum <= 0)
				continue;
			else {
				resultNum++;
				int secondProfNum = remainStNum / secondProfOverseeNum;
				if (remainStNum % secondProfOverseeNum == 0)
					resultNum = resultNum + secondProfNum - 1;
				else {
					resultNum = resultNum + secondProfNum;
				}
			}
		}
	}
}
