package de.sixbits.squeakyjava.feature.checkout;

import java.util.List;

import javax.inject.Inject;

import de.sixbits.reactive.executor.PostExecutionThread;
import de.sixbits.reactive.executor.ThreadExecutor;
import de.sixbits.reactive.interactor.SingleUseCase;
import io.reactivex.rxjava3.core.Single;

public class GetAvailablePaymentMethods extends SingleUseCase<List<PaymentMethodDataModel>, Object> {
    private final PayoneerRepository mPayoneerRepository;

    @Inject
    GetAvailablePaymentMethods(
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread,
            PayoneerRepository payoneerRepository
    ) {
        super(threadExecutor, postExecutionThread);
        mPayoneerRepository = payoneerRepository;
    }

    @Override
    public Single<List<PaymentMethodDataModel>> buildUseCaseSingle(Object... params) {
        return mPayoneerRepository.getAvailablePaymentMethods();
    }
}
