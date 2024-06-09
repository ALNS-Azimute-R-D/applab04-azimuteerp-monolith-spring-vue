import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CustomerTypeService from './customer-type.service';
import { type ICustomerType } from '@/shared/model/customer-type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CustomerTypeDetails',
  setup() {
    const customerTypeService = inject('customerTypeService', () => new CustomerTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const customerType: Ref<ICustomerType> = ref({});

    const retrieveCustomerType = async customerTypeId => {
      try {
        const res = await customerTypeService().find(customerTypeId);
        customerType.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.customerTypeId) {
      retrieveCustomerType(route.params.customerTypeId);
    }

    return {
      alertService,
      customerType,

      previousState,
      t$: useI18n().t,
    };
  },
});
