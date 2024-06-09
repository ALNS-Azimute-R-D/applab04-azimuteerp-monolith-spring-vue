import { type IEvent } from '@/shared/model/event.model';
import { type IProgram } from '@/shared/model/program.model';
import { type IPerson } from '@/shared/model/person.model';

export interface IEventProgram {
  id?: number;
  percentageExecution?: number | null;
  event?: IEvent | null;
  program?: IProgram | null;
  responsiblePerson?: IPerson | null;
}

export class EventProgram implements IEventProgram {
  constructor(
    public id?: number,
    public percentageExecution?: number | null,
    public event?: IEvent | null,
    public program?: IProgram | null,
    public responsiblePerson?: IPerson | null,
  ) {}
}
