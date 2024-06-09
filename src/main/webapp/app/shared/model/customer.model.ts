import { type IPerson } from '@/shared/model/person.model';
import { type ICustomerType } from '@/shared/model/customer-type.model';
import { type IDistrict } from '@/shared/model/district.model';

import { type CustomerStatusEnum } from '@/shared/model/enumerations/customer-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface ICustomer {
  id?: number;
  customerBusinessCode?: string;
  fullname?: string;
  customAttributesDetailsJSON?: string | null;
  customerStatus?: keyof typeof CustomerStatusEnum;
  activationStatus?: keyof typeof ActivationStatusEnum;
  buyerPerson?: IPerson;
  customerType?: ICustomerType | null;
  district?: IDistrict | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public customerBusinessCode?: string,
    public fullname?: string,
    public customAttributesDetailsJSON?: string | null,
    public customerStatus?: keyof typeof CustomerStatusEnum,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public buyerPerson?: IPerson,
    public customerType?: ICustomerType | null,
    public district?: IDistrict | null,
  ) {}
}
