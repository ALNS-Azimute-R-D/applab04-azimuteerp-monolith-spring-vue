import { type IPerson } from '@/shared/model/person.model';
import { type IProduct } from '@/shared/model/product.model';

import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface ISupplier {
  id?: number;
  acronym?: string;
  companyName?: string;
  streetAddress?: string;
  houseNumber?: string | null;
  locationName?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  zipPostalCode?: string | null;
  countryRegion?: string | null;
  pointLocationContentType?: string | null;
  pointLocation?: string | null;
  mainEmail?: string | null;
  phoneNumber1?: string | null;
  phoneNumber2?: string | null;
  customAttributesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  representativePerson?: IPerson;
  toProducts?: IProduct[] | null;
}

export class Supplier implements ISupplier {
  constructor(
    public id?: number,
    public acronym?: string,
    public companyName?: string,
    public streetAddress?: string,
    public houseNumber?: string | null,
    public locationName?: string | null,
    public city?: string | null,
    public stateProvince?: string | null,
    public zipPostalCode?: string | null,
    public countryRegion?: string | null,
    public pointLocationContentType?: string | null,
    public pointLocation?: string | null,
    public mainEmail?: string | null,
    public phoneNumber1?: string | null,
    public phoneNumber2?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public representativePerson?: IPerson,
    public toProducts?: IProduct[] | null,
  ) {}
}
