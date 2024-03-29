package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 随机扑克牌
 * 
 * @author:sunshine
 * @version:v1.0.0
 * @date:2018年7月12日
 */
public class Card {
	/** 扑克牌花纹图案 */
	private String pattern;
	/** 扑克牌数字 */
	private String number;

	public Card(Pattern pattern, Number number) {
		this.pattern = pattern.name();
		this.number = number.name().length()>1?number.name().substring(1):number.name();
	}
	
	private  Card(String pattern, String number) {
		this.pattern = pattern;
		this.number = number;
	}
	
	private static Card getDaWang() {
		return new Card("大","王");
	}
	private static Card getXiaoWang() {
		return new Card("小","王");
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return pattern + " " + number;
	}

	private static enum Pattern {
		红桃, 黑桃, 方块, 樱花
	};

	private static enum Number {
		A, N2, N3, N4, N5, N6, N7, N8, N9, N10, J, Q, K
	};

	public static List<Card> getPoker() {
		List<Card> list = new ArrayList<>(54);
		for (Card.Pattern pattern : Card.Pattern.values()) {
			for (Card.Number number : Card.Number.values()) {
				list.add(new Card(pattern, number));
			}
		}
		list.add(getDaWang());
		list.add(getXiaoWang());
		Collections.shuffle(list);
		return list;
	}

	public static void main(String[] args) {
		Card.getPoker().forEach(a->{
			System.out.println(a.getNumber());
		});
	}

}
