import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RawAssetProcTmpService from './raw-asset-proc-tmp.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AssetTypeService from '@/entities/asset-type/asset-type.service';
import { type IAssetType } from '@/shared/model/asset-type.model';
import { type IRawAssetProcTmp, RawAssetProcTmp } from '@/shared/model/raw-asset-proc-tmp.model';
import { StatusRawProcessingEnum } from '@/shared/model/enumerations/status-raw-processing-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RawAssetProcTmpUpdate',
  setup() {
    const rawAssetProcTmpService = inject('rawAssetProcTmpService', () => new RawAssetProcTmpService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rawAssetProcTmp: Ref<IRawAssetProcTmp> = ref(new RawAssetProcTmp());

    const assetTypeService = inject('assetTypeService', () => new AssetTypeService());

    const assetTypes: Ref<IAssetType[]> = ref([]);
    const statusRawProcessingEnumValues: Ref<string[]> = ref(Object.keys(StatusRawProcessingEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRawAssetProcTmp = async rawAssetProcTmpId => {
      try {
        const res = await rawAssetProcTmpService().find(rawAssetProcTmpId);
        rawAssetProcTmp.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rawAssetProcTmpId) {
      retrieveRawAssetProcTmp(route.params.rawAssetProcTmpId);
    }

    const initRelationships = () => {
      assetTypeService()
        .retrieve()
        .then(res => {
          assetTypes.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      statusRawProcessing: {},
      fullFilenamePath: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      assetRawContentAsBlob: {},
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      assetType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, rawAssetProcTmp as any);
    v$.value.$validate();

    return {
      rawAssetProcTmpService,
      alertService,
      rawAssetProcTmp,
      previousState,
      statusRawProcessingEnumValues,
      isSaving,
      currentLanguage,
      assetTypes,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rawAssetProcTmp.id) {
        this.rawAssetProcTmpService()
          .update(this.rawAssetProcTmp)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rawAssetProcTmpService()
          .create(this.rawAssetProcTmp)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
