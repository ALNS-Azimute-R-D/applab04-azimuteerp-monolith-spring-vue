export interface ITypeOfArtmedia {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string;
}

export class TypeOfArtmedia implements ITypeOfArtmedia {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string,
  ) {}
}
