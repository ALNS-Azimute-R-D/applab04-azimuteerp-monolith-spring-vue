import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IWarehouse {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string | null;
  streetAddress?: string;
  houseNumber?: string | null;
  locationName?: string | null;
  postalCode?: string;
  pointLocationContentType?: string | null;
  pointLocation?: string | null;
  mainEmail?: string | null;
  landPhoneNumber?: string | null;
  mobilePhoneNumber?: string | null;
  faxNumber?: string | null;
  customAttributesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
}

export class Warehouse implements IWarehouse {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string | null,
    public streetAddress?: string,
    public houseNumber?: string | null,
    public locationName?: string | null,
    public postalCode?: string,
    public pointLocationContentType?: string | null,
    public pointLocation?: string | null,
    public mainEmail?: string | null,
    public landPhoneNumber?: string | null,
    public mobilePhoneNumber?: string | null,
    public faxNumber?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
  ) {}
}
