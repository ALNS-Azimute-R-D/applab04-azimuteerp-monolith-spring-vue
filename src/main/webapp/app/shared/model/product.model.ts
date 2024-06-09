import { type IBrand } from '@/shared/model/brand.model';
import { type ISupplier } from '@/shared/model/supplier.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IProduct {
  id?: number;
  productSKU?: string | null;
  productName?: string | null;
  description?: string | null;
  standardCost?: number | null;
  listPrice?: number;
  reorderLevel?: number | null;
  targetLevel?: number | null;
  quantityPerUnit?: string | null;
  discontinued?: boolean;
  minimumReorderQuantity?: number | null;
  suggestedCategory?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  brand?: IBrand;
  toSuppliers?: ISupplier[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productSKU?: string | null,
    public productName?: string | null,
    public description?: string | null,
    public standardCost?: number | null,
    public listPrice?: number,
    public reorderLevel?: number | null,
    public targetLevel?: number | null,
    public quantityPerUnit?: string | null,
    public discontinued?: boolean,
    public minimumReorderQuantity?: number | null,
    public suggestedCategory?: string | null,
    public attachmentsContentType?: string | null,
    public attachments?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public brand?: IBrand,
    public toSuppliers?: ISupplier[] | null,
  ) {
    this.discontinued = this.discontinued ?? false;
  }
}
