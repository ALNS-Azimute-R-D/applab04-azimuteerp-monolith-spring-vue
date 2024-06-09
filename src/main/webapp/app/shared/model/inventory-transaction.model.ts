import { type ISupplier } from '@/shared/model/supplier.model';
import { type IProduct } from '@/shared/model/product.model';
import { type IWarehouse } from '@/shared/model/warehouse.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IInventoryTransaction {
  id?: number;
  invoiceId?: number;
  transactionCreatedDate?: Date | null;
  transactionModifiedDate?: Date | null;
  quantity?: number;
  transactionComments?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  supplier?: ISupplier;
  product?: IProduct;
  warehouse?: IWarehouse;
}

export class InventoryTransaction implements IInventoryTransaction {
  constructor(
    public id?: number,
    public invoiceId?: number,
    public transactionCreatedDate?: Date | null,
    public transactionModifiedDate?: Date | null,
    public quantity?: number,
    public transactionComments?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public supplier?: ISupplier,
    public product?: IProduct,
    public warehouse?: IWarehouse,
  ) {}
}
