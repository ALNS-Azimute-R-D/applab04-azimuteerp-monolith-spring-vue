import { type IOrganization } from '@/shared/model/organization.model';
import { type IPerson } from '@/shared/model/person.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IOrganizationMembership {
  id?: number;
  joinedAt?: Date;
  activationStatus?: keyof typeof ActivationStatusEnum;
  organization?: IOrganization;
  person?: IPerson;
}

export class OrganizationMembership implements IOrganizationMembership {
  constructor(
    public id?: number,
    public joinedAt?: Date,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public organization?: IOrganization,
    public person?: IPerson,
  ) {}
}
