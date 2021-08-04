package de.sixbits.squeakyjava.checkout;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.sixbits.platform.core.BaseViewModel;
import de.sixbits.platform.core.Failure;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;

@HiltViewModel
public class PaymentMethodViewModel extends BaseViewModel {
    private static final String TAG = "CheckoutViewModel";
    private final GetAvailablePaymentMethods mGetAvailablePaymentMethods;

    private final MutableLiveData<List<PaymentMethodDataModel>> _data = new MutableLiveData<>();
    public LiveData<List<PaymentMethodDataModel>> getDataLiveData() {
        return _data;
    }

    private Boolean isConnected = false;
    private Boolean isRequestOnQue = false;

    @Inject
    PaymentMethodViewModel(GetAvailablePaymentMethods getAvailablePaymentMethods) {
        mGetAvailablePaymentMethods = getAvailablePaymentMethods;
    }

    void getAvailablePaymentMethods() {
        if (isConnected) {
            setLoading(true);
            mGetAvailablePaymentMethods.execute(new PaymentMethodsObserver());
        } else {
            isRequestOnQue = true;
            handleFailure(new Failure.ConnectivityError());
        }
    }

    void setIsNetworkAvailable(Boolean connected) {
        isConnected = connected;

        if (isRequestOnQue) {
            isRequestOnQue = false;
            getAvailablePaymentMethods();
        }
    }

    private class PaymentMethodsObserver extends DisposableSingleObserver<List<PaymentMethodDataModel>> {

        @Override
        public void onSuccess(@NonNull List<PaymentMethodDataModel> paymentMethodDataModels) {
            Log.d(TAG, "onSuccess: ");
            _data.postValue(paymentMethodDataModels);
            setLoading(false);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(TAG, "onError: " + e.getMessage() + "\n\n" + Arrays.toString(e.getStackTrace()));
            if (e instanceof Failure) {
                handleFailure((Failure) e);
            } else {
                handleFailure(new Failure.UnknownFailure());
            }
            setLoading(false);
        }
    }
}
