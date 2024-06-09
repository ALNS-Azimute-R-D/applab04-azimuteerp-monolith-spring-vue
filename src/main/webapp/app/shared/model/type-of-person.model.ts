export interface ITypeOfPerson {
  id?: number;
  code?: string;
  description?: string;
}

export class TypeOfPerson implements ITypeOfPerson {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
  ) {}
}
