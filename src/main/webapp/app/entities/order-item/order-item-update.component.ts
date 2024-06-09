import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrderItemService from './order-item.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ArticleService from '@/entities/article/article.service';
import { type IArticle } from '@/shared/model/article.model';
import OrderService from '@/entities/order/order.service';
import { type IOrder } from '@/shared/model/order.model';
import { type IOrderItem, OrderItem } from '@/shared/model/order-item.model';
import { OrderItemStatusEnum } from '@/shared/model/enumerations/order-item-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrderItemUpdate',
  setup() {
    const orderItemService = inject('orderItemService', () => new OrderItemService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const orderItem: Ref<IOrderItem> = ref(new OrderItem());

    const articleService = inject('articleService', () => new ArticleService());

    const articles: Ref<IArticle[]> = ref([]);

    const orderService = inject('orderService', () => new OrderService());

    const orders: Ref<IOrder[]> = ref([]);
    const orderItemStatusEnumValues: Ref<string[]> = ref(Object.keys(OrderItemStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOrderItem = async orderItemId => {
      try {
        const res = await orderItemService().find(orderItemId);
        orderItem.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.orderItemId) {
      retrieveOrderItem(route.params.orderItemId);
    }

    const initRelationships = () => {
      articleService()
        .retrieve()
        .then(res => {
          articles.value = res.data;
        });
      orderService()
        .retrieve()
        .then(res => {
          orders.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      quantity: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      totalPrice: {
        required: validations.required(t$('entity.validation.required').toString()),
        min: validations.minValue(t$('entity.validation.min', { min: 0 }).toString(), 0),
      },
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      article: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      order: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, orderItem as any);
    v$.value.$validate();

    return {
      orderItemService,
      alertService,
      orderItem,
      previousState,
      orderItemStatusEnumValues,
      isSaving,
      currentLanguage,
      articles,
      orders,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.orderItem.id) {
        this.orderItemService()
          .update(this.orderItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.orderItem.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.orderItemService()
          .create(this.orderItem)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.orderItem.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
