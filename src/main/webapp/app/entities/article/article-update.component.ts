import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArticleService from './article.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import CategoryService from '@/entities/category/category.service';
import { type ICategory } from '@/shared/model/category.model';
import { type IArticle, Article } from '@/shared/model/article.model';
import { SizeOptionEnum } from '@/shared/model/enumerations/size-option-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArticleUpdate',
  setup() {
    const articleService = inject('articleService', () => new ArticleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const article: Ref<IArticle> = ref(new Article());

    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());

    const assetCollections: Ref<IAssetCollection[]> = ref([]);

    const categoryService = inject('categoryService', () => new CategoryService());

    const categories: Ref<ICategory[]> = ref([]);
    const sizeOptionEnumValues: Ref<string[]> = ref(Object.keys(SizeOptionEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArticle = async articleId => {
      try {
        const res = await articleService().find(articleId);
        article.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.articleId) {
      retrieveArticle(route.params.articleId);
    }

    const initRelationships = () => {
      assetCollectionService()
        .retrieve()
        .then(res => {
          assetCollections.value = res.data;
        });
      categoryService()
        .retrieve()
        .then(res => {
          categories.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      inventoryProductId: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      skuCode: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 60 }).toString(), 60),
      },
      customName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 150 }).toString(), 150),
      },
      customDescription: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 8192 }).toString(), 8192),
      },
      priceValue: {},
      itemSize: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      assetCollections: {},
      mainCategory: {},
    };
    const v$ = useVuelidate(validationRules, article as any);
    v$.value.$validate();

    return {
      articleService,
      alertService,
      article,
      previousState,
      sizeOptionEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      assetCollections,
      categories,
      v$,
      t$,
    };
  },
  created(): void {
    this.article.assetCollections = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.article.id) {
        this.articleService()
          .update(this.article)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.article.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.articleService()
          .create(this.article)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.article.created', { param: param.id }).toString());
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
