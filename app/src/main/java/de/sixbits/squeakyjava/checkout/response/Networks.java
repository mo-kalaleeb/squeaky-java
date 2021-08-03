package de.sixbits.squeakyjava.checkout.response;

import java.util.List;
import com.squareup.moshi.Json;

public class Networks{

	@Json(name = "applicable")
	private List<ApplicableItem> applicable;

	public List<ApplicableItem> getApplicable(){
		return applicable;
	}
}