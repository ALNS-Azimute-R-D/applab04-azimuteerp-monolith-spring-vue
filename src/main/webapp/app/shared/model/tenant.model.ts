import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface ITenant {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string;
  customerBusinessCode?: string;
  businessHandlerClazz?: string | null;
  mainContactPersonDetailsJSON?: string | null;
  billingDetailsJSON?: string | null;
  technicalEnvironmentsDetailsJSON?: string | null;
  customAttributesDetailsJSON?: string | null;
  logoImgContentType?: string | null;
  logoImg?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
}

export class Tenant implements ITenant {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string,
    public customerBusinessCode?: string,
    public businessHandlerClazz?: string | null,
    public mainContactPersonDetailsJSON?: string | null,
    public billingDetailsJSON?: string | null,
    public technicalEnvironmentsDetailsJSON?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public logoImgContentType?: string | null,
    public logoImg?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
  ) {}
}
