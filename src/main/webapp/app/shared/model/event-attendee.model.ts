import { type IPerson } from '@/shared/model/person.model';
import { type IEvent } from '@/shared/model/event.model';
import { type ITicketPurchased } from '@/shared/model/ticket-purchased.model';

export interface IEventAttendee {
  id?: number;
  attendedAsRole?: string;
  wasPresentInEvent?: boolean | null;
  shouldBuyTicket?: boolean | null;
  ticketNumber?: string | null;
  seatNumber?: string | null;
  attendeePerson?: IPerson | null;
  event?: IEvent | null;
  ticketPurchased?: ITicketPurchased | null;
}

export class EventAttendee implements IEventAttendee {
  constructor(
    public id?: number,
    public attendedAsRole?: string,
    public wasPresentInEvent?: boolean | null,
    public shouldBuyTicket?: boolean | null,
    public ticketNumber?: string | null,
    public seatNumber?: string | null,
    public attendeePerson?: IPerson | null,
    public event?: IEvent | null,
    public ticketPurchased?: ITicketPurchased | null,
  ) {
    this.wasPresentInEvent = this.wasPresentInEvent ?? false;
    this.shouldBuyTicket = this.shouldBuyTicket ?? false;
  }
}
