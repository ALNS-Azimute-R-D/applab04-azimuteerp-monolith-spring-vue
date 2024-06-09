import { type IOrganization } from '@/shared/model/organization.model';

export interface IOrganizationAttribute {
  id?: number;
  attributeName?: string | null;
  attributeValue?: string | null;
  organization?: IOrganization;
}

export class OrganizationAttribute implements IOrganizationAttribute {
  constructor(
    public id?: number,
    public attributeName?: string | null,
    public attributeValue?: string | null,
    public organization?: IOrganization,
  ) {}
}
