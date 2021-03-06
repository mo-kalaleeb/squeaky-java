package de.sixbits.squeakyjava.core.navigation;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.sixbits.squeakyjava.feature.checkout.PaymentFormActivity;
import de.sixbits.squeakyjava.feature.checkout.PaymentMethodActivity;
import de.sixbits.squeakyjava.feature.checkout.PaymentMethodDataModel;
import de.sixbits.squeakyjava.utils.RobolectricAssertions;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = {Build.VERSION_CODES.O_MR1}
)
public class TestNavigator {

    @Test
    public void shouldForwardUserToPaymentMethodScreen() {
        Navigator navigator = new Navigator();
        navigator.showPaymentMethod(getApplicationContext());
        RobolectricAssertions.shouldNavigateTo(RouterActivity.class, PaymentMethodActivity.class);
    }

    @Test
    public void shouldForwardUserToPaymentFormScreen() {
        Navigator navigator = new Navigator();
        navigator.showPaymentForm(
                getApplicationContext(),
                new PaymentMethodDataModel("1", "name", "url")
        );
        RobolectricAssertions.shouldNavigateTo(RouterActivity.class, PaymentFormActivity.class);
    }
}
