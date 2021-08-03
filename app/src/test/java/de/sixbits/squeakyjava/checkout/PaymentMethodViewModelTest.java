package de.sixbits.squeakyjava.checkout;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import de.sixbits.platform.core.Failure;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMethodViewModelTest {
    @Mock
    GetAvailablePaymentMethods getAvailablePaymentMethods;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        RxJavaPlugins.setIoSchedulerHandler((v) -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler((v) -> Schedulers.trampoline());
    }

    @Test
    public void testInitialViewModelState() {
        // Given I just opened the app
        PaymentMethodViewModel viewModel = new PaymentMethodViewModel(
                getAvailablePaymentMethods
        );

        // When I open the app
        // Then I should not be loading
        assert viewModel.loading.getValue() != null && !viewModel.loading.getValue();

        // There should be no data
        assert viewModel.data.getValue() == null;

        // And there should be no failures
        assert viewModel.failure.getValue() == null;
    }

    @Test
    public void testRequestMethods() {
        // Given I open the app
        PaymentMethodViewModel viewModel = new PaymentMethodViewModel(
                getAvailablePaymentMethods
        );

        // And I have internet connection
        viewModel.setIsNetworkAvailable(true);

        // When I request payment methods
        viewModel.getAvailablePaymentMethods();

        // Then I should request the payment method list
        Mockito.verify(getAvailablePaymentMethods, Mockito.times(1)).execute(
                Mockito.any()
        );

        // And I should get a loading indication
        assert viewModel.loading.getValue() != null && viewModel.loading.getValue();

        // And failures should be null
        assert viewModel.failure.getValue() == null;
    }

    @Test
    public void testNoInternet_shouldProduceNetworkFailure() {
        // Given I open the app
        PaymentMethodViewModel viewModel = new PaymentMethodViewModel(
                getAvailablePaymentMethods
        );

        // And I don't have internet connection
        viewModel.setIsNetworkAvailable(false);

        // When I request payment methods
        viewModel.getAvailablePaymentMethods();

        // Then I should not request the payment method list
        Mockito.verify(getAvailablePaymentMethods, Mockito.times(0)).execute(
                Mockito.any()
        );

        // And I should not get a loading indication
        assert viewModel.loading.getValue() != null && !viewModel.loading.getValue();

        // And failures should be NetworkFailure
        assert viewModel.failure.getValue() instanceof Failure.NetworkConnection;
    }

    @Test
    public void testQueRequests() {
        // Given I open the app
        PaymentMethodViewModel viewModel = new PaymentMethodViewModel(
                getAvailablePaymentMethods
        );

        // And I don't have internet connection
        viewModel.setIsNetworkAvailable(false);

        // When I request payment methods
        viewModel.getAvailablePaymentMethods();

        // Then I should not request the payment method list
        Mockito.verify(getAvailablePaymentMethods, Mockito.times(0)).execute(
                Mockito.any()
        );

        // And failures should be NetworkFailure
        assert viewModel.failure.getValue() instanceof Failure.NetworkConnection;

        // When I get internet connectivity
        viewModel.setIsNetworkAvailable(true);

        // Then I should request payment methods automatically
        Mockito.verify(getAvailablePaymentMethods, Mockito.times(1)).execute(
                Mockito.any()
        );
    }
}