import { type IInvoice } from '@/shared/model/invoice.model';
import { type ICustomer } from '@/shared/model/customer.model';

import { type OrderStatusEnum } from '@/shared/model/enumerations/order-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IOrder {
  id?: number;
  businessCode?: string;
  placedDate?: Date;
  totalTaxValue?: number | null;
  totalDueValue?: number | null;
  status?: keyof typeof OrderStatusEnum;
  estimatedDeliveryDate?: Date | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  invoice?: IInvoice | null;
  customer?: ICustomer;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public businessCode?: string,
    public placedDate?: Date,
    public totalTaxValue?: number | null,
    public totalDueValue?: number | null,
    public status?: keyof typeof OrderStatusEnum,
    public estimatedDeliveryDate?: Date | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public invoice?: IInvoice | null,
    public customer?: ICustomer,
  ) {}
}
