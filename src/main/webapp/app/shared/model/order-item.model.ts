import { type IArticle } from '@/shared/model/article.model';
import { type IOrder } from '@/shared/model/order.model';

import { type OrderItemStatusEnum } from '@/shared/model/enumerations/order-item-status-enum.model';
export interface IOrderItem {
  id?: number;
  quantity?: number;
  totalPrice?: number;
  status?: keyof typeof OrderItemStatusEnum;
  article?: IArticle;
  order?: IOrder;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public quantity?: number,
    public totalPrice?: number,
    public status?: keyof typeof OrderItemStatusEnum,
    public article?: IArticle,
    public order?: IOrder,
  ) {}
}
