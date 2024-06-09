import { type IEvent } from '@/shared/model/event.model';
import { type IInvoice } from '@/shared/model/invoice.model';

export interface ITicketPurchased {
  id?: number;
  buyingCode?: string | null;
  duePaymentDate?: Date | null;
  amountOfTickets?: number | null;
  taxValue?: number | null;
  ticketValue?: number | null;
  acquiredSeatsNumbers?: string | null;
  description?: string | null;
  event?: IEvent | null;
  invoice?: IInvoice | null;
}

export class TicketPurchased implements ITicketPurchased {
  constructor(
    public id?: number,
    public buyingCode?: string | null,
    public duePaymentDate?: Date | null,
    public amountOfTickets?: number | null,
    public taxValue?: number | null,
    public ticketValue?: number | null,
    public acquiredSeatsNumbers?: string | null,
    public description?: string | null,
    public event?: IEvent | null,
    public invoice?: IInvoice | null,
  ) {}
}
