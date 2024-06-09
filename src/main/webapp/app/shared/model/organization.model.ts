import { type ITenant } from '@/shared/model/tenant.model';
import { type ITypeOfOrganization } from '@/shared/model/type-of-organization.model';

import { type OrganizationStatusEnum } from '@/shared/model/enumerations/organization-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IOrganization {
  id?: number;
  acronym?: string;
  businessCode?: string;
  hierarchicalLevel?: string | null;
  name?: string;
  description?: string;
  businessHandlerClazz?: string | null;
  mainContactPersonDetailsJSON?: string | null;
  technicalEnvironmentsDetailsJSON?: string | null;
  customAttributesDetailsJSON?: string | null;
  organizationStatus?: keyof typeof OrganizationStatusEnum;
  activationStatus?: keyof typeof ActivationStatusEnum;
  logoImgContentType?: string | null;
  logoImg?: string | null;
  tenant?: ITenant;
  typeOfOrganization?: ITypeOfOrganization;
  organizationParent?: IOrganization | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public acronym?: string,
    public businessCode?: string,
    public hierarchicalLevel?: string | null,
    public name?: string,
    public description?: string,
    public businessHandlerClazz?: string | null,
    public mainContactPersonDetailsJSON?: string | null,
    public technicalEnvironmentsDetailsJSON?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public organizationStatus?: keyof typeof OrganizationStatusEnum,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public logoImgContentType?: string | null,
    public logoImg?: string | null,
    public tenant?: ITenant,
    public typeOfOrganization?: ITypeOfOrganization,
    public organizationParent?: IOrganization | null,
  ) {}
}
