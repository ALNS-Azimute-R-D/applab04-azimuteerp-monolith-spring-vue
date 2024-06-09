import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AssetMetadataService from './asset-metadata.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AssetService from '@/entities/asset/asset.service';
import { type IAsset } from '@/shared/model/asset.model';
import { type IAssetMetadata, AssetMetadata } from '@/shared/model/asset-metadata.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetMetadataUpdate',
  setup() {
    const assetMetadataService = inject('assetMetadataService', () => new AssetMetadataService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const assetMetadata: Ref<IAssetMetadata> = ref(new AssetMetadata());

    const assetService = inject('assetService', () => new AssetService());

    const assets: Ref<IAsset[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAssetMetadata = async assetMetadataId => {
      try {
        const res = await assetMetadataService().find(assetMetadataId);
        assetMetadata.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetMetadataId) {
      retrieveAssetMetadata(route.params.assetMetadataId);
    }

    const initRelationships = () => {
      assetService()
        .retrieve()
        .then(res => {
          assets.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      metadataDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 8192 }).toString(), 8192),
      },
      asset: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, assetMetadata as any);
    v$.value.$validate();

    return {
      assetMetadataService,
      alertService,
      assetMetadata,
      previousState,
      isSaving,
      currentLanguage,
      assets,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.assetMetadata.id) {
        this.assetMetadataService()
          .update(this.assetMetadata)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.assetMetadata.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.assetMetadataService()
          .create(this.assetMetadata)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.assetMetadata.created', { param: param.id }).toString(),
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
