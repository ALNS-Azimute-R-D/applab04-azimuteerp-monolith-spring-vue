import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import InvoiceService from './invoice.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PaymentGatewayService from '@/entities/payment-gateway/payment-gateway.service';
import { type IPaymentGateway } from '@/shared/model/payment-gateway.model';
import { type IInvoice, Invoice } from '@/shared/model/invoice.model';
import { InvoiceStatusEnum } from '@/shared/model/enumerations/invoice-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InvoiceUpdate',
  setup() {
    const invoiceService = inject('invoiceService', () => new InvoiceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const invoice: Ref<IInvoice> = ref(new Invoice());

    const paymentGatewayService = inject('paymentGatewayService', () => new PaymentGatewayService());

    const paymentGateways: Ref<IPaymentGateway[]> = ref([]);
    const invoiceStatusEnumValues: Ref<string[]> = ref(Object.keys(InvoiceStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveInvoice = async invoiceId => {
      try {
        const res = await invoiceService().find(invoiceId);
        res.invoiceDate = new Date(res.invoiceDate);
        res.dueDate = new Date(res.dueDate);
        invoice.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.invoiceId) {
      retrieveInvoice(route.params.invoiceId);
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
      businessCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      invoiceDate: {},
      dueDate: {},
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 80 }).toString(), 80),
      },
      taxValue: {},
      shippingValue: {},
      amountDueValue: {},
      numberOfInstallmentsOriginal: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      numberOfInstallmentsPaid: {},
      amountPaidValue: {},
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      preferrablePaymentGateway: {},
    };
    const v$ = useVuelidate(validationRules, invoice as any);
    v$.value.$validate();

    return {
      invoiceService,
      alertService,
      invoice,
      previousState,
      invoiceStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      paymentGateways,
      v$,
      ...useDateFormat({ entityRef: invoice }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.invoice.id) {
        this.invoiceService()
          .update(this.invoice)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.invoice.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.invoiceService()
          .create(this.invoice)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.invoice.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
