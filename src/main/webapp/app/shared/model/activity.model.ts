import { type ITypeOfActivity } from '@/shared/model/type-of-activity.model';
import { type IPerson } from '@/shared/model/person.model';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IActivity {
  id?: number;
  name?: string;
  shortDescription?: string | null;
  fullDescription?: string | null;
  mainGoals?: string | null;
  estimatedDurationTime?: string | null;
  lastPerformedDate?: Date | null;
  createdAt?: Date | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  typeOfActivity?: ITypeOfActivity;
  createdByUser?: IPerson | null;
  assetCollections?: IAssetCollection[] | null;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public name?: string,
    public shortDescription?: string | null,
    public fullDescription?: string | null,
    public mainGoals?: string | null,
    public estimatedDurationTime?: string | null,
    public lastPerformedDate?: Date | null,
    public createdAt?: Date | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public typeOfActivity?: ITypeOfActivity,
    public createdByUser?: IPerson | null,
    public assetCollections?: IAssetCollection[] | null,
  ) {}
}
