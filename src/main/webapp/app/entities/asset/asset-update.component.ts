import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AssetService from './asset.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AssetTypeService from '@/entities/asset-type/asset-type.service';
import { type IAssetType } from '@/shared/model/asset-type.model';
import RawAssetProcTmpService from '@/entities/raw-asset-proc-tmp/raw-asset-proc-tmp.service';
import { type IRawAssetProcTmp } from '@/shared/model/raw-asset-proc-tmp.model';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { type IAsset, Asset } from '@/shared/model/asset.model';
import { StorageTypeEnum } from '@/shared/model/enumerations/storage-type-enum.model';
import { StatusAssetEnum } from '@/shared/model/enumerations/status-asset-enum.model';
import { PreferredPurposeEnum } from '@/shared/model/enumerations/preferred-purpose-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetUpdate',
  setup() {
    const assetService = inject('assetService', () => new AssetService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const asset: Ref<IAsset> = ref(new Asset());

    const assetTypeService = inject('assetTypeService', () => new AssetTypeService());

    const assetTypes: Ref<IAssetType[]> = ref([]);

    const rawAssetProcTmpService = inject('rawAssetProcTmpService', () => new RawAssetProcTmpService());

    const rawAssetProcTmps: Ref<IRawAssetProcTmp[]> = ref([]);

    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());

    const assetCollections: Ref<IAssetCollection[]> = ref([]);
    const storageTypeEnumValues: Ref<string[]> = ref(Object.keys(StorageTypeEnum));
    const statusAssetEnumValues: Ref<string[]> = ref(Object.keys(StatusAssetEnum));
    const preferredPurposeEnumValues: Ref<string[]> = ref(Object.keys(PreferredPurposeEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAsset = async assetId => {
      try {
        const res = await assetService().find(assetId);
        asset.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetId) {
      retrieveAsset(route.params.assetId);
    }

    const initRelationships = () => {
      assetTypeService()
        .retrieve()
        .then(res => {
          assetTypes.value = res.data;
        });
      rawAssetProcTmpService()
        .retrieve()
        .then(res => {
          rawAssetProcTmps.value = res.data;
        });
      assetCollectionService()
        .retrieve()
        .then(res => {
          assetCollections.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      storageTypeUsed: {},
      fullFilenamePath: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      status: {},
      preferredPurpose: {},
      assetContentAsBlob: {},
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      assetType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      rawAssetProcTmp: {},
      assetCollections: {},
    };
    const v$ = useVuelidate(validationRules, asset as any);
    v$.value.$validate();

    return {
      assetService,
      alertService,
      asset,
      previousState,
      storageTypeEnumValues,
      statusAssetEnumValues,
      preferredPurposeEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      assetTypes,
      rawAssetProcTmps,
      assetCollections,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {
    this.asset.assetCollections = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.asset.id) {
        this.assetService()
          .update(this.asset)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.asset.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.assetService()
          .create(this.asset)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.asset.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
