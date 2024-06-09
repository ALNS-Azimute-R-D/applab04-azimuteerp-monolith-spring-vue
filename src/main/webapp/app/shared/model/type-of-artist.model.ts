export interface ITypeOfArtist {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string;
}

export class TypeOfArtist implements ITypeOfArtist {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string,
  ) {}
}
