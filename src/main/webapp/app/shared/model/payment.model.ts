import { type IPaymentGateway } from '@/shared/model/payment-gateway.model';

import { type PaymentTypeEnum } from '@/shared/model/enumerations/payment-type-enum.model';
import { type PaymentStatusEnum } from '@/shared/model/enumerations/payment-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IPayment {
  id?: number;
  installmentNumber?: number;
  paymentDueDate?: Date;
  paymentPaidDate?: Date;
  paymentAmount?: number;
  typeOfPayment?: keyof typeof PaymentTypeEnum;
  statusPayment?: keyof typeof PaymentStatusEnum;
  customAttributesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  paymentGateway?: IPaymentGateway;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public installmentNumber?: number,
    public paymentDueDate?: Date,
    public paymentPaidDate?: Date,
    public paymentAmount?: number,
    public typeOfPayment?: keyof typeof PaymentTypeEnum,
    public statusPayment?: keyof typeof PaymentStatusEnum,
    public customAttributesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public paymentGateway?: IPaymentGateway,
  ) {}
}
