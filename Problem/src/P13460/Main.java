package P13460;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// 세로 가로 입력
		Scanner scanner = new Scanner(System.in);
		
		int yLen = 0;
		int xLen = 0; 
		
		if(scanner.hasNextInt())
			yLen = scanner.nextInt();
		else {
			scanner.close();
			return;
		}
		
		if(scanner.hasNextInt())
			xLen = scanner.nextInt();
		else {
			scanner.close();
			return;
		}
		
		// 지도 생성
		Map map = new Map(yLen, xLen);
		
		scanner.nextLine(); // 개행문자
		
		// 공 생성
		Ball redBall = new Ball('R', 0, 0);
		Ball blueBall = new Ball('B', 0, 0);
		
		// 맵 세팅
		for(int y = 0; y < yLen; y++) {
			String row = scanner.nextLine();

			for(int x = 0; x < xLen; x++) {
				char value = row.charAt(x);
				
				Node node = map.getNode(y, x);
				
				if(row.charAt(x) == 'R')
					map.redBall = node;
				else if(row.charAt(x) == 'B')
					map.blueBall = node;
				
				node.setNode(x, y, value, y * yLen + x);
			}
		}
		
		scanner.close();
		
		// 로직 시작
		map.printMap();
		System.out.println(map.makePath());
		//map.printQueue();
	}        

}

class Map {
	int yLen = 0; // 세로
	int xLen = 0; // 가로
	
	Node[][] arrNode;
	
	Node redBall;
	Node blueBall;
	
	Queue<Node> pathQueue; // Queue로 세팅
	
	public Map(int yLen, int xLen) {
		this.yLen = yLen;
		this.xLen = xLen;
		
		arrNode = new Node[yLen][xLen];
		
		pathQueue = new LinkedList<Node>();
		
		for(int y = 0; y < yLen; y++) {
			for(int x = 0; x < xLen; x++) {
				arrNode[y][x] = new Node();
			}
		}
		
		for(int y = 0; y < yLen; y++) {
			for(int x = 0; x < xLen; x++) {
				if(y - 1 >= 0) { // 상
					Node tempNode = arrNode[y - 1][x];
					arrNode[y][x].setUpNode(tempNode);
				}
					
				if(y + 1 < yLen) { // 하
					Node tempNode = arrNode[y + 1][x];
					arrNode[y][x].setDownNode(tempNode);
				}
				
				if(x - 1 >= 0) { // 좌
					Node tempNode = arrNode[y][x - 1];
					arrNode[y][x].setLeftNode(tempNode);					
				}
				 
				if(x + 1 < xLen) { // 우
					Node tempNode = arrNode[y][x + 1];
					arrNode[y][x].setRightNode(tempNode);
				}
			}
		}
	}
	
	/** 
	 * 세로, 가로, 값
	 * @param y : y좌표
	 * @param x : x좌표
	 * @param value : 값
	 */
	public void setMapCoor(int y, int x, Node node) {
		arrNode[y][x] = node;
	}
	
	public Node getNode(int y, int x) {
		return arrNode[y][x];
	}

	/**
	 * 
	 * @param y : y좌표
	 * @param x : x좌표
	 * @return value : 
	 */
	public int makePath() { // y(-1): 상, y(+1): 하, x(-1): 좌, x(+1): 우
		int count = 0;
		
		Node tempNode = redBall;
		//Node pathNode = null;

		while(count <= 10) {
			if(count > 0 && pathQueue.size() != 0) {
				tempNode = pathQueue.poll();
				System.out.println(tempNode.id);
				if(tempNode.getValue() == 'O') return count;
			}
			else if(count == 0) {
				
			}
			else {
				return -1;
			}
			
			if(tempNode.isVisit == false) {
				tempNode.isVisit = true;
				
				if(tempNode.getUpNode() != null) {
					if( tempNode.getUpNode().getValue() == '.' || tempNode.getUpNode().getValue() == 'O' ) {
						Node pathNode = tempNode.moveNode(tempNode, "up");
						
						if(pathNode != null && !pathQueue.contains(pathNode) && !pathNode.isBlocked(pathNode, "up") && !pathNode.isVisit) {
							pathQueue.add(pathNode);
						}
					}
				}
				
				if(tempNode.getRightNode() != null) {
					if( tempNode.getRightNode().getValue() == '.' || tempNode.getRightNode().getValue() == 'O' ) {
						Node pathNode = tempNode.moveNode(tempNode, "right");
						
						if(pathNode != null && !pathQueue.contains(pathNode) && !pathNode.isBlocked(pathNode, "right") && !pathNode.isVisit) {
							pathQueue.add(pathNode);
						}
					}
				}
				
				if(tempNode.getDownNode() != null) {
					if( tempNode.getDownNode().getValue() == '.' || tempNode.getDownNode().getValue() == 'O' ) {
						Node pathNode = tempNode.moveNode(tempNode, "down");
						
						if(pathNode != null && !pathQueue.contains(pathNode) && !pathNode.isBlocked(pathNode, "down") && !pathNode.isVisit) {
							pathQueue.add(pathNode);
						}
					}
				}
				
				if(tempNode.getLeftNode() != null) {
					if( tempNode.getLeftNode().getValue() == '.' || tempNode.getLeftNode().getValue() == 'O' ) {
						Node pathNode = tempNode.moveNode(tempNode, "left");
						
						if(pathNode != null && !pathQueue.contains(pathNode) && ( !pathNode.isBlocked(pathNode, "left") || pathNode.getValue() == 'O') && !pathNode.isVisit) {
							pathQueue.add(pathNode);
						}
					}
				}
			}
			
			count++;
		}
		
		return -1;
	}
	
	public void printMap() {
		for(int y = 0; y < yLen; y++) {
			for(int x = 0; x < xLen; x++) {
				System.out.print(arrNode[y][x].value); 
			}
			System.out.println("");
		}
	}
	
	public void printQueue() {
		while(true) {
			Node node = pathQueue.poll(); 
			
			if(node != null) {
				System.out.println(node.value);
			}
			else {
				return;
			}
		}
	}
}

class Node {
	int x, y; // x, y 좌표
	Node leftNode, rightNode, upNode, downNode;
	char value;
	int id;
	boolean isVisit;
	
	/**
	 * x, y, 왼쪽, 오른쪽, 위, 아래 
	 * @param x
	 * @param y
	 * @param leftNode
	 * @param rightNode
	 * @param upNode
	 * @param downNode
	 */
	public void setNode(int x, int y, char value, int id) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.id = id;
	}

	public Node getDownNode() {
		return downNode;
	}

	public void setDownNode(Node downNode) {
		this.downNode = downNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public void setUpNode(Node upNode) {
		this.upNode = upNode;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public Node getUpNode() {
		return upNode;
	}

	public char getValue() {
		return value;
	}
	
	public Node moveNode(Node node, String direction) {	
		if(direction.equals("up")){
			Node tempNode = node.getUpNode();
			
			if(tempNode != null) {
				if( tempNode.getValue() == '.' ) {
					return moveNode(tempNode, "up");
				}
				else if( tempNode.getValue() == 'O' ) {
					return tempNode;
				}
			}
		}
		else if(direction.equals("right")){
			Node tempNode = node.getRightNode();
			
			if(tempNode != null) {
				if( tempNode.getValue() == '.' ) {
					return moveNode(tempNode, "right");
				}
				else if( tempNode.getValue() == 'O' ) {
					return tempNode;
				}
			}
		}
		else if(direction.equals("down")){
			Node tempNode = node.getDownNode();
			
			if(tempNode != null) {
				if( tempNode.getValue() == '.' ) {
					return moveNode(tempNode, "down");
				}
				else if( tempNode.getValue() == 'O' ) {
					return tempNode;
				}
			}
		}
		else if(direction.equals("left")){
			Node tempNode = node.getLeftNode();
			
			if(tempNode != null) {
				if( tempNode.getValue() == '.' ) {
					return moveNode(tempNode, "left");
				}
				else if( tempNode.getValue() == 'O' ) {
					return tempNode;
				}
			}
		}
		
		return node;
	}
	
	public boolean isBlocked(Node node, String direction) {
		if(direction.equals("up")){
			if(node.getLeftNode().getValue() != '.' && node.getRightNode().getValue() != '.') {
				return true;
			}
			else {
				return false;
			}
		}
		else if(direction.equals("right")){
			if(node.getUpNode().getValue() != '.' && node.getDownNode().getValue() != '.') {
				return true;
			}
			else {
				return false;
			}
		}
		else if(direction.equals("down")){
			if(node.getLeftNode().getValue() != '.' && node.getRightNode().getValue() != '.') {
				return true;
			}
			else {
				return false;
			}
		}
		else { // "left"
			if(node.getUpNode().getValue() != '.' && node.getDownNode().getValue() != '.') {
				return true;
			}
			else {
				return false;
			}
		}
	}
}

class Ball {
	char color; // r: red ball, b: blue ball
	int coorY, coorX;
	
	/**
	 * 색, 세로 좌표, 가로 좌표 
	 * @param color : 색
	 * @param coorY : Y좌표
	 * @param coorX : X좌표 
	 */
	public Ball(char color, int coorY, int coorX) {
		this.color = color;
		this.coorY = coorY;
		this.coorX = coorX;
	}
	
	/**
	 * 세로, 가로
	 * @param coorY : Y좌표
	 * @param coorX : X좌표
	 */
	public void setBallCoor(int coorY, int coorX) {
		this.coorY = coorY;
		this.coorX = coorY;
	}
}

