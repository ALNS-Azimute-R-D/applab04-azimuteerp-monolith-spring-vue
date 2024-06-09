import { type IWarehouse } from '@/shared/model/warehouse.model';
import { type IProduct } from '@/shared/model/product.model';

export interface IStockLevel {
  id?: number;
  lastModifiedDate?: Date;
  remainingQuantity?: number;
  commonAttributesDetailsJSON?: string | null;
  warehouse?: IWarehouse;
  product?: IProduct;
}

export class StockLevel implements IStockLevel {
  constructor(
    public id?: number,
    public lastModifiedDate?: Date,
    public remainingQuantity?: number,
    public commonAttributesDetailsJSON?: string | null,
    public warehouse?: IWarehouse,
    public product?: IProduct,
  ) {}
}
