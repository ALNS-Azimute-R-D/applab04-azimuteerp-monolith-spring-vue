import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PaymentGatewayService from './payment-gateway.service';
import { type IPaymentGateway } from '@/shared/model/payment-gateway.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentGatewayDetails',
  setup() {
    const paymentGatewayService = inject('paymentGatewayService', () => new PaymentGatewayService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const paymentGateway: Ref<IPaymentGateway> = ref({});

    const retrievePaymentGateway = async paymentGatewayId => {
      try {
        const res = await paymentGatewayService().find(paymentGatewayId);
        paymentGateway.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentGatewayId) {
      retrievePaymentGateway(route.params.paymentGatewayId);
    }

    return {
      alertService,
      paymentGateway,

      previousState,
      t$: useI18n().t,
    };
  },
});
