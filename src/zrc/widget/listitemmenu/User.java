package zrc.widget.listitemmenu;

import android.graphics.drawable.Drawable;

public class User {
	private String name;
	private String sign;
	private Drawable pic;

	public User() {
		super();
	}

	public User(String name, String sign, Drawable pic) {
		super();
		this.name = name;
		this.sign = sign;
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) { 
		this.sign = sign;
	}

	public Drawable getPic() {
		return pic;
	}

	public void setPic(Drawable pic) {
		this.pic = pic;
	}

}
