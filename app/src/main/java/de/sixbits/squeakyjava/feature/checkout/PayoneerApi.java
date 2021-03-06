package de.sixbits.squeakyjava.feature.checkout;

import de.sixbits.squeakyjava.feature.checkout.response.PaymentMethodsResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface PayoneerApi {
    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Single<PaymentMethodsResponse> getPaymentMethods();
}
