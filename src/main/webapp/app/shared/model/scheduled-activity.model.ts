import { type IProgram } from '@/shared/model/program.model';
import { type IActivity } from '@/shared/model/activity.model';
import { type IPerson } from '@/shared/model/person.model';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IScheduledActivity {
  id?: number;
  customizedName?: string | null;
  startTime?: Date;
  endTime?: Date | null;
  numberOfAttendees?: number | null;
  averageEvaluationInStars?: number | null;
  customAttributtesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  program?: IProgram | null;
  activity?: IActivity | null;
  responsiblePerson?: IPerson | null;
  assetCollections?: IAssetCollection[] | null;
}

export class ScheduledActivity implements IScheduledActivity {
  constructor(
    public id?: number,
    public customizedName?: string | null,
    public startTime?: Date,
    public endTime?: Date | null,
    public numberOfAttendees?: number | null,
    public averageEvaluationInStars?: number | null,
    public customAttributtesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public program?: IProgram | null,
    public activity?: IActivity | null,
    public responsiblePerson?: IPerson | null,
    public assetCollections?: IAssetCollection[] | null,
  ) {}
}
