import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SupplierService from './supplier.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type ISupplier } from '@/shared/model/supplier.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SupplierDetails',
  setup() {
    const supplierService = inject('supplierService', () => new SupplierService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const supplier: Ref<ISupplier> = ref({});

    const retrieveSupplier = async supplierId => {
      try {
        const res = await supplierService().find(supplierId);
        supplier.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.supplierId) {
      retrieveSupplier(route.params.supplierId);
    }

    return {
      alertService,
      supplier,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
