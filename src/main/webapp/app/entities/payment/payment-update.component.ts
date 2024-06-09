import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PaymentService from './payment.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PaymentGatewayService from '@/entities/payment-gateway/payment-gateway.service';
import { type IPaymentGateway } from '@/shared/model/payment-gateway.model';
import { type IPayment, Payment } from '@/shared/model/payment.model';
import { PaymentTypeEnum } from '@/shared/model/enumerations/payment-type-enum.model';
import { PaymentStatusEnum } from '@/shared/model/enumerations/payment-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentUpdate',
  setup() {
    const paymentService = inject('paymentService', () => new PaymentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const payment: Ref<IPayment> = ref(new Payment());

    const paymentGatewayService = inject('paymentGatewayService', () => new PaymentGatewayService());

    const paymentGateways: Ref<IPaymentGateway[]> = ref([]);
    const paymentTypeEnumValues: Ref<string[]> = ref(Object.keys(PaymentTypeEnum));
    const paymentStatusEnumValues: Ref<string[]> = ref(Object.keys(PaymentStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePayment = async paymentId => {
      try {
        const res = await paymentService().find(paymentId);
        res.paymentDueDate = new Date(res.paymentDueDate);
        res.paymentPaidDate = new Date(res.paymentPaidDate);
        payment.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentId) {
      retrievePayment(route.params.paymentId);
    }

    const initRelationships = () => {
      paymentGatewayService()
        .retrieve()
        .then(res => {
          paymentGateways.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      installmentNumber: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      paymentDueDate: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      paymentPaidDate: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      paymentAmount: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      typeOfPayment: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      statusPayment: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      paymentGateway: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, payment as any);
    v$.value.$validate();

    return {
      paymentService,
      alertService,
      payment,
      previousState,
      paymentTypeEnumValues,
      paymentStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      paymentGateways,
      v$,
      ...useDateFormat({ entityRef: payment }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.payment.id) {
        this.paymentService()
          .update(this.payment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.payment.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.paymentService()
          .create(this.payment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.payment.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
