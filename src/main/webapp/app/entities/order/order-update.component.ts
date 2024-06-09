import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrderService from './order.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import InvoiceService from '@/entities/invoice/invoice.service';
import { type IInvoice } from '@/shared/model/invoice.model';
import CustomerService from '@/entities/customer/customer.service';
import { type ICustomer } from '@/shared/model/customer.model';
import { type IOrder, Order } from '@/shared/model/order.model';
import { OrderStatusEnum } from '@/shared/model/enumerations/order-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrderUpdate',
  setup() {
    const orderService = inject('orderService', () => new OrderService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const order: Ref<IOrder> = ref(new Order());

    const invoiceService = inject('invoiceService', () => new InvoiceService());

    const invoices: Ref<IInvoice[]> = ref([]);

    const customerService = inject('customerService', () => new CustomerService());

    const customers: Ref<ICustomer[]> = ref([]);
    const orderStatusEnumValues: Ref<string[]> = ref(Object.keys(OrderStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOrder = async orderId => {
      try {
        const res = await orderService().find(orderId);
        res.placedDate = new Date(res.placedDate);
        res.estimatedDeliveryDate = new Date(res.estimatedDeliveryDate);
        order.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.orderId) {
      retrieveOrder(route.params.orderId);
    }

    const initRelationships = () => {
      invoiceService()
        .retrieve()
        .then(res => {
          invoices.value = res.data;
        });
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      businessCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      placedDate: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      totalTaxValue: {},
      totalDueValue: {},
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      estimatedDeliveryDate: {},
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      invoice: {},
      customer: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, order as any);
    v$.value.$validate();

    return {
      orderService,
      alertService,
      order,
      previousState,
      orderStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      invoices,
      customers,
      v$,
      ...useDateFormat({ entityRef: order }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.order.id) {
        this.orderService()
          .update(this.order)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.order.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.orderService()
          .create(this.order)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.order.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
