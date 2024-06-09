export interface ITypeOfOrganization {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string;
  businessHandlerClazz?: string | null;
}

export class TypeOfOrganization implements ITypeOfOrganization {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string,
    public businessHandlerClazz?: string | null,
  ) {}
}
