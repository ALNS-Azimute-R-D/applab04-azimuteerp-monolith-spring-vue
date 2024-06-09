import { type IOrganizationMembership } from '@/shared/model/organization-membership.model';
import { type IOrganizationRole } from '@/shared/model/organization-role.model';

export interface IOrganizationMemberRole {
  id?: number;
  joinedAt?: Date;
  organizationMembership?: IOrganizationMembership;
  organizationRole?: IOrganizationRole;
}

export class OrganizationMemberRole implements IOrganizationMemberRole {
  constructor(
    public id?: number,
    public joinedAt?: Date,
    public organizationMembership?: IOrganizationMembership,
    public organizationRole?: IOrganizationRole,
  ) {}
}
