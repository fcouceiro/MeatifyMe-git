package com.ruthlessgames.meatifymebuilder;

public interface SignInListener {
	public void signSuccedd(String username);
	public void signFailed(int code);
}
