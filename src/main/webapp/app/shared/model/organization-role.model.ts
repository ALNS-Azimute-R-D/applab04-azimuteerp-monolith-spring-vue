import { type IOrganization } from '@/shared/model/organization.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IOrganizationRole {
  id?: number;
  roleName?: string;
  activationStatus?: keyof typeof ActivationStatusEnum;
  organization?: IOrganization;
}

export class OrganizationRole implements IOrganizationRole {
  constructor(
    public id?: number,
    public roleName?: string,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public organization?: IOrganization,
  ) {}
}
