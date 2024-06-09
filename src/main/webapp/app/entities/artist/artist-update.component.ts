import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArtistService from './artist.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TypeOfArtmediaService from '@/entities/type-of-artmedia/type-of-artmedia.service';
import { type ITypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';
import TypeOfArtistService from '@/entities/type-of-artist/type-of-artist.service';
import { type ITypeOfArtist } from '@/shared/model/type-of-artist.model';
import ArtisticGenreService from '@/entities/artistic-genre/artistic-genre.service';
import { type IArtisticGenre } from '@/shared/model/artistic-genre.model';
import { type IArtist, Artist } from '@/shared/model/artist.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtistUpdate',
  setup() {
    const artistService = inject('artistService', () => new ArtistService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const artist: Ref<IArtist> = ref(new Artist());

    const typeOfArtmediaService = inject('typeOfArtmediaService', () => new TypeOfArtmediaService());

    const typeOfArtmedias: Ref<ITypeOfArtmedia[]> = ref([]);

    const typeOfArtistService = inject('typeOfArtistService', () => new TypeOfArtistService());

    const typeOfArtists: Ref<ITypeOfArtist[]> = ref([]);

    const artists: Ref<IArtist[]> = ref([]);

    const artisticGenreService = inject('artisticGenreService', () => new ArtisticGenreService());

    const artisticGenres: Ref<IArtisticGenre[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArtist = async artistId => {
      try {
        const res = await artistService().find(artistId);
        artist.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artistId) {
      retrieveArtist(route.params.artistId);
    }

    const initRelationships = () => {
      typeOfArtmediaService()
        .retrieve()
        .then(res => {
          typeOfArtmedias.value = res.data;
        });
      typeOfArtistService()
        .retrieve()
        .then(res => {
          typeOfArtists.value = res.data;
        });
      artistService()
        .retrieve()
        .then(res => {
          artists.value = res.data;
        });
      artisticGenreService()
        .retrieve()
        .then(res => {
          artisticGenres.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      publicName: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      realName: {
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      biographyDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      typeOfArtmedia: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      typeOfArtist: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      artistAggregator: {},
      artists: {},
    };
    const v$ = useVuelidate(validationRules, artist as any);
    v$.value.$validate();

    return {
      artistService,
      alertService,
      artist,
      previousState,
      isSaving,
      currentLanguage,
      typeOfArtmedias,
      typeOfArtists,
      artists,
      artisticGenres,
      v$,
      t$,
    };
  },
  created(): void {
    this.artist.artists = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.artist.id) {
        this.artistService()
          .update(this.artist)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.artist.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.artistService()
          .create(this.artist)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.artist.created', { param: param.id }).toString());
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
