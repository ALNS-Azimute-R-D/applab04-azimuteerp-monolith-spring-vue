import { type IVenue } from '@/shared/model/venue.model';
import { type ITypeOfEvent } from '@/shared/model/type-of-event.model';
import { type IPerson } from '@/shared/model/person.model';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';

import { type EventStatusEnum } from '@/shared/model/enumerations/event-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IEvent {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string;
  fullDescription?: string | null;
  startTime?: Date;
  endTime?: Date | null;
  defaultTicketValue?: number;
  status?: keyof typeof EventStatusEnum;
  activationStatus?: keyof typeof ActivationStatusEnum;
  mainVenue?: IVenue | null;
  typeOfEvent?: ITypeOfEvent;
  promoteurPerson?: IPerson | null;
  assetCollections?: IAssetCollection[] | null;
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string,
    public fullDescription?: string | null,
    public startTime?: Date,
    public endTime?: Date | null,
    public defaultTicketValue?: number,
    public status?: keyof typeof EventStatusEnum,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public mainVenue?: IVenue | null,
    public typeOfEvent?: ITypeOfEvent,
    public promoteurPerson?: IPerson | null,
    public assetCollections?: IAssetCollection[] | null,
  ) {}
}
