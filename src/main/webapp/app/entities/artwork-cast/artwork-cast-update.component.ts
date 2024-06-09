import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArtworkCastService from './artwork-cast.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ArtworkService from '@/entities/artwork/artwork.service';
import { type IArtwork } from '@/shared/model/artwork.model';
import ArtistService from '@/entities/artist/artist.service';
import { type IArtist } from '@/shared/model/artist.model';
import { type IArtworkCast, ArtworkCast } from '@/shared/model/artwork-cast.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtworkCastUpdate',
  setup() {
    const artworkCastService = inject('artworkCastService', () => new ArtworkCastService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const artworkCast: Ref<IArtworkCast> = ref(new ArtworkCast());

    const artworkService = inject('artworkService', () => new ArtworkService());

    const artworks: Ref<IArtwork[]> = ref([]);

    const artistService = inject('artistService', () => new ArtistService());

    const artists: Ref<IArtist[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArtworkCast = async artworkCastId => {
      try {
        const res = await artworkCastService().find(artworkCastId);
        artworkCast.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artworkCastId) {
      retrieveArtworkCast(route.params.artworkCastId);
    }

    const initRelationships = () => {
      artworkService()
        .retrieve()
        .then(res => {
          artworks.value = res.data;
        });
      artistService()
        .retrieve()
        .then(res => {
          artists.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      orderOfAppearance: {},
      characterName: {
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      mainAssetUUID: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      characterDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      artwork: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      artist: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, artworkCast as any);
    v$.value.$validate();

    return {
      artworkCastService,
      alertService,
      artworkCast,
      previousState,
      isSaving,
      currentLanguage,
      artworks,
      artists,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.artworkCast.id) {
        this.artworkCastService()
          .update(this.artworkCast)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.artworkCast.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.artworkCastService()
          .create(this.artworkCast)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.artworkCast.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
