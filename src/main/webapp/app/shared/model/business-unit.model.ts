import { type IOrganization } from '@/shared/model/organization.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IBusinessUnit {
  id?: number;
  acronym?: string;
  hierarchicalLevel?: string | null;
  name?: string;
  activationStatus?: keyof typeof ActivationStatusEnum;
  organization?: IOrganization;
  businessUnitParent?: IBusinessUnit | null;
}

export class BusinessUnit implements IBusinessUnit {
  constructor(
    public id?: number,
    public acronym?: string,
    public hierarchicalLevel?: string | null,
    public name?: string,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public organization?: IOrganization,
    public businessUnitParent?: IBusinessUnit | null,
  ) {}
}
