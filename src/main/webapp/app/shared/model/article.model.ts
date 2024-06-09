import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { type ICategory } from '@/shared/model/category.model';

import { type SizeOptionEnum } from '@/shared/model/enumerations/size-option-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IArticle {
  id?: number;
  inventoryProductId?: number;
  skuCode?: string | null;
  customName?: string | null;
  customDescription?: string | null;
  priceValue?: number | null;
  itemSize?: keyof typeof SizeOptionEnum;
  activationStatus?: keyof typeof ActivationStatusEnum;
  assetCollections?: IAssetCollection[] | null;
  mainCategory?: ICategory | null;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public inventoryProductId?: number,
    public skuCode?: string | null,
    public customName?: string | null,
    public customDescription?: string | null,
    public priceValue?: number | null,
    public itemSize?: keyof typeof SizeOptionEnum,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public assetCollections?: IAssetCollection[] | null,
    public mainCategory?: ICategory | null,
  ) {}
}
