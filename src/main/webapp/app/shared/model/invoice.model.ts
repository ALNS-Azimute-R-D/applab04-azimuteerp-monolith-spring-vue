import { type IPaymentGateway } from '@/shared/model/payment-gateway.model';

import { type InvoiceStatusEnum } from '@/shared/model/enumerations/invoice-status-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IInvoice {
  id?: number;
  businessCode?: string;
  invoiceDate?: Date | null;
  dueDate?: Date | null;
  description?: string;
  taxValue?: number | null;
  shippingValue?: number | null;
  amountDueValue?: number | null;
  numberOfInstallmentsOriginal?: number;
  numberOfInstallmentsPaid?: number | null;
  amountPaidValue?: number | null;
  status?: keyof typeof InvoiceStatusEnum;
  customAttributesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  preferrablePaymentGateway?: IPaymentGateway | null;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public businessCode?: string,
    public invoiceDate?: Date | null,
    public dueDate?: Date | null,
    public description?: string,
    public taxValue?: number | null,
    public shippingValue?: number | null,
    public amountDueValue?: number | null,
    public numberOfInstallmentsOriginal?: number,
    public numberOfInstallmentsPaid?: number | null,
    public amountPaidValue?: number | null,
    public status?: keyof typeof InvoiceStatusEnum,
    public customAttributesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public preferrablePaymentGateway?: IPaymentGateway | null,
  ) {}
}
