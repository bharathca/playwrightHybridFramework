package com.OpenCart.constants;


public enum ApplicationConstantsHelper {
	HOME_PAGE_TITLE("Your Store"),
	LOGIN_PAGE_TITLE("Account Login");
	
	private final String label;

	ApplicationConstantsHelper(String label) {
			this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
