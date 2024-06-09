import { type IOrganization } from '@/shared/model/organization.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IOrganizationDomain {
  id?: number;
  domainAcronym?: string;
  name?: string;
  isVerified?: boolean;
  businessHandlerClazz?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  organization?: IOrganization;
}

export class OrganizationDomain implements IOrganizationDomain {
  constructor(
    public id?: number,
    public domainAcronym?: string,
    public name?: string,
    public isVerified?: boolean,
    public businessHandlerClazz?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public organization?: IOrganization,
  ) {
    this.isVerified = this.isVerified ?? false;
  }
}
