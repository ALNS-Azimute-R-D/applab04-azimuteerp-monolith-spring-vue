import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArticleService from './article.service';
import { type IArticle } from '@/shared/model/article.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArticleDetails',
  setup() {
    const articleService = inject('articleService', () => new ArticleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const article: Ref<IArticle> = ref({});

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

    return {
      alertService,
      article,

      previousState,
      t$: useI18n().t,
    };
  },
});
