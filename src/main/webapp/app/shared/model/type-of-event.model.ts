export interface ITypeOfEvent {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  handlerClazzName?: string | null;
}

export class TypeOfEvent implements ITypeOfEvent {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public handlerClazzName?: string | null,
  ) {}
}
