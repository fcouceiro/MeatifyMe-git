package com.me.meatifyme;

public interface ActionResolver {
	 public void showShortToast(CharSequence toastMessage);
     public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
     public void showMainMenu();
     public void backPressed();
     public void updateProgressLoading(float prog);
     public void showLoading();
}
