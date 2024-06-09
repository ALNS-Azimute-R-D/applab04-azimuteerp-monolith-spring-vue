import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IPaymentGateway {
  id?: number;
  aliasCode?: string;
  description?: string;
  businessHandlerClazz?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
}

export class PaymentGateway implements IPaymentGateway {
  constructor(
    public id?: number,
    public aliasCode?: string,
    public description?: string,
    public businessHandlerClazz?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
  ) {}
}
