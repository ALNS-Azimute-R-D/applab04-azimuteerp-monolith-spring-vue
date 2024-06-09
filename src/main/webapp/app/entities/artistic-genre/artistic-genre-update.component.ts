import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ArtisticGenreService from './artistic-genre.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ArtistService from '@/entities/artist/artist.service';
import { type IArtist } from '@/shared/model/artist.model';
import { type IArtisticGenre, ArtisticGenre } from '@/shared/model/artistic-genre.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtisticGenreUpdate',
  setup() {
    const artisticGenreService = inject('artisticGenreService', () => new ArtisticGenreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const artisticGenre: Ref<IArtisticGenre> = ref(new ArtisticGenre());

    const artistService = inject('artistService', () => new ArtistService());

    const artists: Ref<IArtist[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveArtisticGenre = async artisticGenreId => {
      try {
        const res = await artisticGenreService().find(artisticGenreId);
        artisticGenre.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artisticGenreId) {
      retrieveArtisticGenre(route.params.artisticGenreId);
    }

    const initRelationships = () => {
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
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      artisticGenres: {},
    };
    const v$ = useVuelidate(validationRules, artisticGenre as any);
    v$.value.$validate();

    return {
      artisticGenreService,
      alertService,
      artisticGenre,
      previousState,
      isSaving,
      currentLanguage,
      artists,
      v$,
      t$,
    };
  },
  created(): void {
    this.artisticGenre.artisticGenres = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.artisticGenre.id) {
        this.artisticGenreService()
          .update(this.artisticGenre)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.artisticGenre.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.artisticGenreService()
          .create(this.artisticGenre)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.artisticGenre.created', { param: param.id }).toString(),
            );
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
