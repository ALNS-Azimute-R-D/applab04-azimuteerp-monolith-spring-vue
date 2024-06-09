import { type IAsset } from '@/shared/model/asset.model';
import { type IArticle } from '@/shared/model/article.model';
import { type IEvent } from '@/shared/model/event.model';
import { type IActivity } from '@/shared/model/activity.model';
import { type IScheduledActivity } from '@/shared/model/scheduled-activity.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IAssetCollection {
  id?: number;
  name?: string;
  fullFilenamePath?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  assets?: IAsset[] | null;
  articles?: IArticle[] | null;
  events?: IEvent[] | null;
  activities?: IActivity[] | null;
  scheduledActivities?: IScheduledActivity[] | null;
}

export class AssetCollection implements IAssetCollection {
  constructor(
    public id?: number,
    public name?: string,
    public fullFilenamePath?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public assets?: IAsset[] | null,
    public articles?: IArticle[] | null,
    public events?: IEvent[] | null,
    public activities?: IActivity[] | null,
    public scheduledActivities?: IScheduledActivity[] | null,
  ) {}
}
