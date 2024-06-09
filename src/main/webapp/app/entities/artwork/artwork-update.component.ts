import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArtworkService from './artwork.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TypeOfArtmediaService from '@/entities/type-of-artmedia/type-of-artmedia.service';
import { type ITypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';
import { type IArtwork, Artwork } from '@/shared/model/artwork.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtworkUpdate',
  setup() {
    const artworkService = inject('artworkService', () => new ArtworkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const artwork: Ref<IArtwork> = ref(new Artwork());

    const typeOfArtmediaService = inject('typeOfArtmediaService', () => new TypeOfArtmediaService());

    const typeOfArtmedias: Ref<ITypeOfArtmedia[]> = ref([]);

    const artworks: Ref<IArtwork[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArtwork = async artworkId => {
      try {
        const res = await artworkService().find(artworkId);
        artwork.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artworkId) {
      retrieveArtwork(route.params.artworkId);
    }

    const initRelationships = () => {
      typeOfArtmediaService()
        .retrieve()
        .then(res => {
          typeOfArtmedias.value = res.data;
        });
      artworkService()
        .retrieve()
        .then(res => {
          artworks.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      artworkTitle: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      productionYear: {},
      seasonNumber: {},
      episodeOrSequenceNumber: {},
      registerIdInIMDB: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      assetsCollectionUUID: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      contentDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      typeOfArtmedia: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      artworkAggregator: {},
    };
    const v$ = useVuelidate(validationRules, artwork as any);
    v$.value.$validate();

    return {
      artworkService,
      alertService,
      artwork,
      previousState,
      isSaving,
      currentLanguage,
      typeOfArtmedias,
      artworks,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.artwork.id) {
        this.artworkService()
          .update(this.artwork)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.artwork.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.artworkService()
          .create(this.artwork)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.artwork.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
