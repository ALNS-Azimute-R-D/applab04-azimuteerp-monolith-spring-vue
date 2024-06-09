import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IProgram {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  fullDescription?: string | null;
  targetPublic?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
}

export class Program implements IProgram {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public fullDescription?: string | null,
    public targetPublic?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
  ) {}
}
