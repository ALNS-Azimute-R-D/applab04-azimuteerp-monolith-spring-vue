export interface ITypeOfVenue {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  handlerClazzName?: string | null;
}

export class TypeOfVenue implements ITypeOfVenue {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public handlerClazzName?: string | null,
  ) {}
}
